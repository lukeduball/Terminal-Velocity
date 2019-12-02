/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.jbox2d.common.Vec2;
import racingplatformer.gameobject.vehicles.Vehicle;
import racingplatformer.race.Chunk;
import racingplatformer.race.Race;
import racingplatformer.race.Track;

/**
 *
 * @author Luke
 */
public class Screen 
{
    private Track track;
    private Vehicle focusVehicle;
    private int position;
    private int x, y;
    private int width, height;
    
    private float scaleFactor;
    
    private Race race;
    
    private Chunk[] loadedChunks;
    
    private static float EXPECTED_RATIO = 1920 / 986;
    
    public Screen(int pos, Race r)
    {
        this.position = pos;
        this.race = r;
        this.loadedChunks = new Chunk[3];
    }
    
    public Screen(int pos, Race r, Vehicle fv)
    {
        this(pos, r);
        this.focusVehicle = fv;
    }
    
    public void renderScreen(Graphics2D g)
    {   
        //Clear the clipping rectangle
        g.setClip(null);
        Rectangle clippingRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(new Color(228, 224, 255));
        g.fill(clippingRect);
        
        //TODO:: Remove this is a drawing line for debug purposes
        g.setColor(Color.red);
        g.draw(clippingRect);
        //Sets the clipping rectangle
        g.clip(clippingRect);
        
        for(Chunk chunk : this.loadedChunks)
        {
            //Safeguard against attempting to draw a null chunk
            if(chunk != null)
            {
                chunk.renderTrack(g, this);
                race.getFinishLine().render(g, this);
            }
        }
        
        for(Chunk chunk : this.loadedChunks)
        {
            if(chunk != null)
            {
                chunk.renderGameObjects(g, this);
            }
        }
        
        Vec2 placePosition = new Vec2(this.focusVehicle.getPosition());
        placePosition.x += Chunk.CHUNK_WIDTH - 2;
        placePosition.y -= ((float)Chunk.CHUNK_HEIGHT * .5) - 2;
        String racePosition = this.focusVehicle.getCurrentPosition();
        StringRenderer.drawCenteredString(g, racePosition,
                placePosition, Color.black, this);
    }
    
    public void updateScreen(int numScreens)
    {
        this.updateLoadedChunks();
        this.updateScreenPositionAndDimensions(numScreens);
    }
    
    public void updateLoadedChunks()
    {
        if(this.focusVehicle != null)
        {
            Chunk currentChunk = race.getChunkFromLocation(this.focusVehicle.getPosition().x);
            if(this.loadedChunks[1] != currentChunk && currentChunk != null)
            {
                race.unloadChunk(getChunkToUnload(currentChunk, this.loadedChunks[1]));
                this.loadedChunks[0] = race.getChunk(currentChunk.getChunkID() - 1);
                this.loadedChunks[1] = currentChunk;
                this.loadedChunks[2] = race.getChunk(currentChunk.getChunkID() + 1);
                for(Chunk c : this.loadedChunks)
                {
                    race.loadChunk(c);
                }
            }
            else if(currentChunk == null)
            {
                for(int i = 0; i < 3; i++)
                {
                    this.loadedChunks[i] = null;
                    race.unloadChunk(this.loadedChunks[i]);
                }
            }
        }
    }
    
    //Determines which chunk to unload based on if the new middle chunk is greater or less than the old middle chunk
    public Chunk getChunkToUnload(Chunk currentChunk, Chunk previousMiddleChunk)
    {
        if(currentChunk == null || previousMiddleChunk == null)
            return null;
        
        if(currentChunk.getChunkID() > previousMiddleChunk.getChunkID())
        {
            return this.loadedChunks[0];
        }
        else
        {
            return this.loadedChunks[2];
        }
    }
    
    public void updateScreenPositionAndDimensions(int numScreens)
    {
        float parentHeight = race.getGameInstance().getParent().getHeight();
        float parentWidth = race.getGameInstance().getParent().getWidth();
        
        if(numScreens > 1)
        {
            height = race.getGameInstance().getParent().getHeight() / 2;
        }
        else
        {
            height = race.getGameInstance().getParent().getHeight();
        }
        
        if(numScreens > 2)
        {
            width = race.getGameInstance().getParent().getWidth() / 2;
        }
        else
        {
            width = race.getGameInstance().getParent().getWidth();
        }
        
        if(this.position % 2 == 0)
        {
            this.y = height;
        }
        else
        {
            this.y = 0;
        }
        
        if(this.position > 2)
        {
            this.x = width;
        }
        else
        {
            this.x = 0;
        }
        
        float actualRatio = width / height;
        if(actualRatio > EXPECTED_RATIO)
        {
            int oldWidth = width;
            width = (height * 1920) / 986;
            this.x += (int)(oldWidth - width) / 2;
        }
        
        //TODO Decide if the width or height change is greater to decide which one to use to set the scaleFactor
        this.scaleFactor = width / (float)(Chunk.CHUNK_WIDTH*2);
    }
    
    public float getScaleFactor()
    {
        return this.scaleFactor;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public float getWorldRenderX()
    {
        return this.focusVehicle.getPosition().x - (float)Chunk.CHUNK_WIDTH;
    }
    
    public float getWorldRenderY()
    {
        return this.focusVehicle.getPosition().y - (float)Chunk.CHUNK_HEIGHT / 1.50f;
    }
    
    public Vec2 worldToScreenCoordinate(Vec2 worldSpace)
    {
        Vec2 vec = new Vec2(this.getX() + (worldSpace.x - getWorldRenderX()) * this.getScaleFactor(), 
                this.getY() + (worldSpace.y - getWorldRenderY())*this.getScaleFactor() );
        return vec;
    }
    
    public boolean isActiveChunk(Chunk chunk)
    {
        for(int i = 0; i < this.loadedChunks.length; i++)
        {
            if(this.loadedChunks[i] == chunk)
            {
                return true;
            }
        }
        return false;
    }
    
    public Chunk[] getLoadedChunks()
    {
        return this.loadedChunks;
    }
    
}
