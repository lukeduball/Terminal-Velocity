/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Chunk 
{
    private final int chunkID;
    private final List<TrackSegment> boundaryList;
    private final List<GameObject> gameObjectList;
    
    public static int CHUNK_WIDTH = 20;
    public static int CHUNK_HEIGHT = 16;
    
    private Race race;
    
    public Chunk(Race r, int cnkID)
    {
        this.race = r;
        this.chunkID = cnkID;
        this.boundaryList = new ArrayList();
        this.gameObjectList = new ArrayList<>();
    }
    
    public void onUpdate(long delta)
    {
        List<GameObject> removeList = new ArrayList<>();
        for(GameObject o : gameObjectList)
        {
            int chunkPos = (int)(o.getPosition().x / (float)CHUNK_WIDTH);
            if(chunkPos != this.chunkID)
            {
                //Remove game object from list
                removeList.add(o);
                //Add Game Object to the appropriate chunk
                Chunk chunk = race.getChunk(chunkPos);
                if(chunk != null)
                {
                    chunk.addGameObject(o);
                }
            }
            o.onUpdate(delta);
        }
        
        for(GameObject o : removeList)
        {
            this.gameObjectList.remove(o);
        }
    }
    
    public int getChunkID()
    {
        return this.chunkID;
    }
    
    public void renderGameObjects(Graphics2D g, Screen screen)
    {
        for(GameObject o : gameObjectList)
        {
            o.render(g, screen);
        }
    }
    
    public void renderTrack(Graphics2D g, Screen screen)
    {
        //Draw the track segments first
        for(TrackSegment segment : this.boundaryList)
        {
            segment.render(g, screen); 
        }
    }
    
    public void addGameObject(GameObject o)
    {
        if(!this.gameObjectList.contains(o))
        {
            this.gameObjectList.add(o);
        }
    }
    
    public void removeGameObject(GameObject o)
    {
        if(this.gameObjectList.contains(o))
        {
            this.gameObjectList.remove(o);
        }
    }
    
    public void addBoundary(TrackSegment trackSegment)
    {
        if(!this.boundaryList.contains(trackSegment))
        {
            this.boundaryList.add(trackSegment);
        }
    }
}
