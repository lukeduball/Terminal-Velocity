/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
import racingplatformer.gameobject.vehicles.Porche;
import racingplatformer.renderengine.DebugDrawTV;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Race 
{
    private Game gameInstance;
    
    //Stores all of the chunks which contain track data and game objects located in the chunk
    private final List<Chunk> chunkList;
    
    //Stores all the currently loaded chunks to do logical updates on
    private final List<Chunk> loadedChunksList;
    
    //Stores all the information about the track
    private Track track;

    private Screen screen;
    private World world;
    
    private static final float DT = 1.0f / 60.0f;
    private static final int VEL_IT = 3;
    private static final int POS_IT = 8;
    
    public Race(Game gameInst)
    {
        world = new World(new Vec2(0.0f, 9.81f));
        
        this.gameInstance = gameInst;
        this.chunkList = new ArrayList<>();
        this.loadedChunksList = new ArrayList<>();
        Porche porche = new Porche(world, 100.f, 50.f);
        this.screen = new Screen(1, gameInst, porche);
        Track.generateTrack(world, 10340340L, this.chunkList);
        this.chunkList.get(0).addGameObject(porche);
        
        for(int i = 0; i < 10; i++)
        {
            //this.createCircleShape(0.0f + 25*i, 50.0f, world);
        }
        
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.position.set(100.f, 100.f);
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(1000, 10);
        FixtureDef fd1 = new FixtureDef();
        fd1.shape = pShape;
        fd1.density = 0.5f;
        fd1.friction = 1.0f;
        fd1.restitution = 0.5f;
        //world.createBody(def).createFixture(fd1);
        
        
        DebugDrawTV debugDraw = new DebugDrawTV(this.screen, gameInstance.getGraphics());
        debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit);
        world.setDebugDraw(debugDraw);
    }
    
    private void createCircleShape(float x, float y, World world)
    {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(x, y);
        CircleShape cs = new CircleShape();
        cs.m_radius = 10f;
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        world.createBody(bd).createFixture(fd);
    }
    
    /***
     * Called to update the logic of the race each frame
     */
    public void onUpdate()
    {
        world.step(DT, VEL_IT, POS_IT);
        this.screen.updateScreen(1, this);
        
        for(Chunk chunk : this.loadedChunksList)
        {
            if(chunk != null)
            {
                chunk.onUpdate(this);   
            }
        }
        
    }
    
    /***
     * Renders all current screens
     * @param g current graphics instance
     */
    public void render(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.gameInstance.getWidth(), this.gameInstance.getHeight());
        this.screen.renderScreen(g);
        
        //Draw the debug data last so that it is drawn over all images
        world.drawDebugData();
    }
    
    public Chunk getChunk(int chunkID)
    {
        if(chunkID < 0 || chunkID >= this.chunkList.size())
        {
            return null;
        }
        
        return this.chunkList.get(chunkID);
    }
    
    public Chunk getChunkFromLocation(float worldPositionX)
    {
        int chunkID = (int)(worldPositionX / 250);
        return getChunk(chunkID);
    }
    
    public void loadChunk(Chunk chunk)
    {
        if(!this.isChunkLoaded(chunk))
        {
            this.loadedChunksList.add(chunk);
        }
    }
    
    public void unloadChunk(Chunk chunk)
    {
        this.loadedChunksList.remove(chunk);
    }
    
    private boolean isChunkLoaded(Chunk chunk)
    {
        return this.loadedChunksList.contains(chunk);
    }

    public boolean isMappedKeyDown(int playerID, int keyIdentifier)
    {
        return this.gameInstance.isKeyDown(this.gameInstance.getPlayerControlKeys(playerID-1)[keyIdentifier]);
    }
}
