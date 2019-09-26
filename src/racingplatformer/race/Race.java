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
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
import racingplatformer.gameobject.vehicles.Porche;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Race 
{
    private Game gameInstance;
    
    //Stores all of the chunks which contain track data and gameobjects located in the chunk
    private final List<Chunk> chunkList;
    
    //Stores all the currently loaded chunks to do logical updates on
    private final List<Chunk> loadedChunksList;
    
    //Stores all the information about the track
    private Track track;
    
    private Chunk chunk;
    private Screen screen;
    
    public Race(Game gameInst)
    {
        this.gameInstance = gameInst;
        this.chunkList = new ArrayList<>();
        this.loadedChunksList = new ArrayList<>();
        Porche porche = new Porche(100.f, 100.f);
        this.screen = new Screen(1, gameInst, porche);
        this.chunk = new Chunk(0);
        this.chunk.addGameObject(porche);
        this.chunkList.add(chunk);
        this.chunkList.add(new Chunk(1));
    }
    
    /***
     * Called to update the logic of the race each frame
     */
    public void onUpdate()
    {
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
}
