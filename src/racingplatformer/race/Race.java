/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import racingplatformer.gameobject.GameObject;

/**
 *
 * @author Luke
 */
public class Race 
{
    //Stores all of the game objects including the vehicles
    private ArrayList<GameObject> gameObjects;
    //Stores all the information about the track
    private Track track;
    
    public Race()
    {
        this.gameObjects = new ArrayList<>();
    }
    
    /***
     * Called to update the logic of the race each frame
     */
    public void onUpdate()
    {
        //Updates the game objects -- includes the vehicles
        for(GameObject o : gameObjects)
        {
            o.onUpdate();
        }
    }
    
    /***
     * Renders all things in the race to the screen
     * @param g current graphics instance
     */
    public void render(Graphics2D g)
    {
        //Render the track -- must be before to objects or it will render over the game objects
        //track.render(g);
        
        //Renders all game objects -- includes the vehicles
        for(GameObject o : gameObjects)
        {
            o.render(g);
        }
    }
}
