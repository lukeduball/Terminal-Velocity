/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
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
    private GameObject focusVehicle;
    private int position;
    private int x, y;
    private int width, height;
    
    private float scaleFactor;
    
    private Game gameInstance;
    
    private Chunk[] loadedChunks;
    
    public Screen(int pos, Game g)
    {
        this.position = pos;
        this.gameInstance = g;
        this.loadedChunks = new Chunk[3];
    }
    
    public Screen(int pos, Game g, GameObject fv)
    {
        this(pos, g);
        this.focusVehicle = fv;
    }
    
    public void renderScreen(Graphics2D g)
    {
        g.setColor(Color.red);
        
        //Clear the clipping rectangle
        g.setClip(null);
        
        Rectangle clippingRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        //TODO:: Remove this is a drawing line for debug purposes
        g.draw(clippingRect);
        //Sets the clipping rectangle
        g.clip(clippingRect);
        
        for(Chunk chunk : this.loadedChunks)
        {
            //Safeguard against attempting to draw a null chunk
            if(chunk != null)
            {
                chunk.renderTrack(g, this, gameInstance);
            }
        }
        
        for(Chunk chunk : this.loadedChunks)
        {
            if(chunk != null)
            {
                chunk.renderGameObjects(g, this, gameInstance);
            }
        }
    }
    
    public void updateScreen(int numScreens, Race race)
    {
        this.updateLoadedChunks(race);
        this.updateScreenPositionAndDimensions(numScreens);
    }
    
    public void updateLoadedChunks(Race race)
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
    
    //Determines which chunk to unload based on if the new middle chunk is greator or less than the old middle chunk
    private Chunk getChunkToUnload(Chunk currentChunk, Chunk previousMiddleChunk)
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
        if(numScreens > 1)
        {
            height = gameInstance.getParent().getHeight() / 2;
        }
        else
        {
            height = gameInstance.getParent().getHeight();
        }
        
        if(numScreens > 2)
        {
            width = gameInstance.getParent().getWidth() / 2;
        }
        else
        {
            width = gameInstance.getParent().getWidth();
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
        
        //TODO Replace 500.f with a constant and update width by using the screen size
        //TODO Decide if the width or height change is greater to decide which one to use to set the scaleFactor
        this.scaleFactor = width / 500.f;
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
        return this.focusVehicle.getPosition().x - 250.f;
    }
    
    public float getWorldRenderY()
    {
        return this.focusVehicle.getPosition().y - 186.f;
    }
    
}
