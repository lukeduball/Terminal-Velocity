/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.CharField;
import racingplatformer.renderengine.gui.components.Component;

/**
 *
 * @author Luke
 */
public class Gui 
{
    //These variables are protected so that the classes that inherit from GUI can use them
    protected Game gameInstance;
    protected Image backgroundImg = ResourceManager.loadImage("src/resources/images/gui/blue_gradient_background.png");
    
    //holds the list of buttons to draw to the screen
    protected ArrayList<Component> componentList;
    
    protected PopupGui currentPopup;
    
    public Gui(Game game)
    {
        gameInstance = game;
        componentList = new ArrayList<>();
        currentPopup = null;
    }
    
    /**
     * Called from the main game loop if this is the active Gui, updates the logic of the Gui
     * @param mouseX mouse x coordinate that is changed to psuedo width space
     * @param mouseY mouse y coordinate that is changed to psuedo heigth space
     */
    public void onUpdate(double mouseX, double mouseY)
    {
        if(this.currentPopup != null)
        {
            this.currentPopup.onUpdate(mouseX, mouseY);
        }
        else
        {
            for(Component c : componentList)
            {
                c.checkHoveredOver(this, mouseX, mouseY);
            }
        }
    }
    
    /**
     * Called from the MouseHandler if this is the active Gui when a mouse is clicked
     * @param e information about the mouse event
     */
    public void onMouseClicked(MouseEvent e)
    {
        if(this.currentPopup != null)
        {
            this.currentPopup.onMouseClicked(e);
        }
        else
        {
            for(Component c : componentList)
            {
                if(c.getHoveredOver())
                {
                    c.click(this);
                }
                else
                {
                    c.setFocus(false);
                }
            }
        }
    }
    
    public void onKeyPressed(KeyEvent e)
    {
        if(this.currentPopup != null)
        {
            this.currentPopup.onKeyPressed(e);
        }
        else
        {
            for(Component c : componentList)
            {
                c.onKeyPressed(e, this);
            }
        }
    }
    
    /**
     * Called from the main game loop to draw the screen if this is the active Gui
     * @param g graphics instance to draw to the screen
     */
    public void draw(Graphics2D g)
    {
        if(backgroundImg != null)
        {
            RenderHelper.drawScaledBackgroundImage(g, backgroundImg, gameInstance);
        }
        
        //Buttons need to be drawn second so they are drawn on top of the background image
        for(Component c : componentList)
        {
            c.draw(g, this.gameInstance);
        }
    }
    
    /**
     * Closes this gui by setting the active gui to null
     */
    public void close()
    {
        gameInstance.setActiveGui(null);
    }
    
    public void setCurrentPopup(PopupGui popup)
    {
        this.currentPopup = popup;
    }
}
