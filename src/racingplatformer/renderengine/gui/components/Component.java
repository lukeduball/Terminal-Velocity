/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.Gui;

/**
 *
 * @author Luke
 */
public class Component 
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    private boolean isHoveredOver;
    private boolean isClicked;
    private boolean isFocused;
    
    private Image buttonImg = ResourceManager.loadImage("src/resources/images/gui/parallelogram_button.png");
    private Image buttonHoveredImg = ResourceManager.loadImage("src/resources/images/gui/parallelogram_button_hover.png");
    
    public Component(float x1, float y1, float w, float h)
    {
        this.x = x1;
        this.y = y1;
        this.width = w;
        this.height = h;
    }
    
    /**
     * Draws the component to the screen
     * @param g graphics instance to draw to the screen
     * @param gameInstance game instance
     */
    public void draw(Graphics2D g, Game gameInstance)
    {

    }
    
    /**
     * Checks to see if the component is hovered over
     * @param parent 
     * @param mouseX mouse x coordinates in psuedo space
     * @param mouseY mouse y coordinates in psuedo space
     */
    public void checkHoveredOver(Gui parent, double mouseX, double mouseY)
    {
        if(mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth())
        {
            if(mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight())
            {
                this.isHoveredOver = true;
                return;
            }
        }
        this.isHoveredOver = false;
    }
    
    /**
     * Called when the button is clicked
     * @param parent parent gui
     */
    public void click(Object parent)
    {

    }
    
    public void onKeyPressed(KeyEvent e, Gui parent)
    {
        
    }
    
    public void setFocus(boolean flag)
    {
        this.isFocused = flag;
    }
    
    public boolean getHoveredOver() { return isHoveredOver; }
    public boolean getClicked() { return isClicked; }
    public boolean getFocused() { return isFocused; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() {return height; }
}
