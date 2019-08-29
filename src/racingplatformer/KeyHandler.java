/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Luke
 */
public class KeyHandler extends KeyAdapter
{
    private Game gameInstance;
    
    public KeyHandler(Game game)
    {
        this.gameInstance = game;
    }
    
    /**
     * Notification that a key was pressed i.e. pushed down and not released
     * @param e The details of the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(gameInstance.getActiveGui() != null)
        {
            gameInstance.getActiveGui().onKeyPressed(e);
        }
    }
    
    /**
     * Notification that a key was released
     * @param e  The details of the key released
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    /**
     * Notification that a key was typed i.e. pressed and released
     * @param e The details of the key typed
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
}
