/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import racingplatformer.renderengine.gui.Gui;

/**
 *
 * @author Luke
 */
public class MouseHandler implements MouseListener
{
    private Game gameInstance;
    
    public MouseHandler(Game game)
    {
        this.gameInstance = game;
    }
    
    /**
     * Called when the mouse is clicked
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(this.gameInstance.getActiveGui() != null)
        {
            this.gameInstance.getActiveGui().onMouseClicked(e);
        }
    }

    /**
     * Called when the mouse is pressed down
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) 
    {

    }

    /**
     * Called when the mouse is released
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) 
    {
        //Fixes non-response issues, introduces ability to click then release into
        if(this.gameInstance.getActiveGui() != null)
        {
            //this.gameInstance.getActiveGui().onMouseClicked(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {

    }

    @Override
    public void mouseExited(MouseEvent e) 
    {

    }
    
}
