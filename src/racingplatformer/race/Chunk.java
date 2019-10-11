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
import racingplatformer.gameobject.vehicles.Porche;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Chunk 
{
    private final int chunkID;
    private final List boundaryList;
    private final List<GameObject> gameObjectList;
    
    public Chunk(int cnkID)
    {
        this.chunkID = cnkID;
        this.boundaryList = new ArrayList();
        this.gameObjectList = new ArrayList<>();
    }
    
    public void onUpdate(Race race)
    {
        List<GameObject> removeList = new ArrayList<>();
        for(GameObject o : gameObjectList)
        {
            int chunkPos = (int)(o.getPosition().getX() / 250.f);
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
            o.onUpdate();
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
    
    public void renderGameObjects(Graphics2D g, Screen screen, Game gameInstance)
    {
        for(GameObject o : gameObjectList)
        {
            o.render(g, screen, gameInstance);
        }
    }
    
    public void renderTrack(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Draw the track segments first
        ((TrackSegment)this.boundaryList.get(0)).render(g, screen, gameInstance);
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
