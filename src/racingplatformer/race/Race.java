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
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import racingplatformer.Game;
import racingplatformer.gameobject.vehicles.*;
import racingplatformer.renderengine.DebugDrawTV;
import racingplatformer.renderengine.Screen;
import racingplatformer.renderengine.gui.WinnerGui;

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
    
    private List<Vehicle> vehicleList;
    
    //Stores all the information about the track
    private Track track;

    private List<Screen> screens;
    private World world;
    
    private FinishLine finishLine;
    private int finishedVehicles = 0;
    
    private List<Integer> finishOrderList;
    
    private static final float DT = 1.0f / 60.0f;
    private static final int VEL_IT = 3;
    private static final int POS_IT = 8;
    
    public Race(Game gameInst)
    {
        world = new World(new Vec2(0.0f, 9.81f));
        this.screens = new ArrayList<>();
        
        this.finishOrderList = new ArrayList<>();

        this.gameInstance = gameInst;
        this.chunkList = new ArrayList<>();
        this.loadedChunksList = new ArrayList<>();
        
        this.vehicleList = new ArrayList<>();

        MonsterTruck mt = new MonsterTruck(world,5, 97, 4);
        mt.setMovementController(new PlayerController(mt, 4));
        Screen screen4 = new Screen(4, gameInst, mt);

        Porche porche3 = new Porche(world, 5.f, 97.f, 3);
        porche3.setMovementController(new PlayerController(porche3, 3));
        Screen screen3 = new Screen(3, gameInst, porche3);

        RallyRacer porche2 = new RallyRacer(world, 5.f, 97.f, 2);
        porche2.setMovementController(new PlayerController(porche2, 2));
        Screen screen2 = new Screen(2, gameInst, porche2);

        MuscleCar porche = new MuscleCar(world, 5.f, 97.f, 1);
        porche.setMovementController(new PlayerController(porche, 1));
        Screen screen = new Screen(1, gameInst, porche);
        this.screens.add(screen);
        this.screens.add(screen2);
        this.screens.add(screen3);
        this.screens.add(screen4);
        //Track.generateTrack(this, world, 10340340L, this.chunkList);
        Track.generateFlatTrack(this, world, chunkList);
        this.chunkList.get(0).addGameObject(porche);
        this.chunkList.get(0).addGameObject(porche2);
        this.chunkList.get(0).addGameObject(porche3);
        this.chunkList.get(0).addGameObject(mt);
        
        this.vehicleList.add(porche);
        this.vehicleList.add(porche2);
        this.vehicleList.add(porche3);
        this.vehicleList.add(mt);
        
        DebugDrawTV debugDraw = new DebugDrawTV(this.screens.get(0), gameInstance.getGraphics());
        debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit);
        world.setDebugDraw(debugDraw);
    }
    
    /***
     * Called to update the logic of the race each frame
     */
    public void onUpdate()
    {
        world.step(DT, VEL_IT, POS_IT);
        for(Screen screen : screens)
        {
            screen.updateScreen(screens.size(), this);
        }
        
        for(Chunk chunk : this.loadedChunksList)
        {
            if(chunk != null)
            {
                chunk.onUpdate(this);   
            }
        }
        this.checkForFinishedVehicles();
    }
    
    private void checkForFinishedVehicles()
    {
        List<Vehicle> removeList = new ArrayList<>();
        for(Vehicle vehicle : this.vehicleList)
        {
            if(this.finishLine.hasVehicleFinishedRace(vehicle))
            {
                vehicle.setFinishPosition(this.getCurrentPosition(vehicle));
                vehicle.setRacing(false);
                this.finishedVehicles++;
                removeList.add(vehicle);
                this.finishOrderList.add(vehicle.getRacerID());
            }
        }
        
        for(Vehicle vehicle : removeList)
        {
            this.vehicleList.remove(vehicle);
        }
        
        if(this.vehicleList.isEmpty())
        {
            this.gameInstance.setActiveRace(null);
            this.gameInstance.setActiveGui(new WinnerGui(this.gameInstance, this.finishOrderList));
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
        for(Screen screen : screens)
        {
            screen.renderScreen(g, this);
        }
        
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
        int chunkID = (int)(worldPositionX / Chunk.CHUNK_WIDTH);
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
    
    public int getCurrentPosition(Vehicle vehicle)
    {
        int position = finishedVehicles+1;
        for(Vehicle v : this.vehicleList)
        {
            if(v == vehicle)
                continue;
            
            float comparePosition = vehicle.getFrontOfVehiclePosition().x - v.getFrontOfVehiclePosition().x;
            if(comparePosition < 0)
            {
                position++;
            }
        }
        return position;
    }
    
    public FinishLine getFinishLine()
    {
        return this.finishLine;
    }
    
    public void setFinishLine(FinishLine fl)
    {
        this.finishLine = fl;
    }
}
