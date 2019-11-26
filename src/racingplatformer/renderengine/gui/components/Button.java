/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.Gui;
import racingplatformer.renderengine.gui.RenderHelper;

/**
 *
 * @author Luke
 */
public class Button extends Component
{
    private String name;
    private float x;
    private float y;
    private float width;
    private float height;
    private String text;
    
    private boolean isHoveredOver;
    private boolean isClicked;
    private boolean isDisabled;
    
    private Image buttonImg = ResourceManager.loadImage("src/resources/images/gui/parallelogram_button.png");
    private Image buttonHoveredImg = ResourceManager.loadImage("src/resources/images/gui/parallelogram_button_hover.png");
    private Image buttonDisabledImg = ResourceManager.loadImage("src/resources/images/gui/parallelogram_button_disabled.png");
    
    public Button(String n, float x1, float y1, float w, float h)
    {
        super(x1, y1, w, h);
        this.name = n;
        text = null;
    }
    
    public Button(String n, float x1, float y1, float w, float h, String t)
    {
        this(n, x1, y1, w, h);
        this.text = t;
    }
    
    public Button(String n, float x1, float y1, float w, float h, Image i1, Image i2, Image i3)
    {
        this(n, x1, y1, w, h);
        buttonImg = i1;
        buttonHoveredImg = i2;
        buttonDisabledImg = i3;
    }
    
    /**
     * Draws the button to the screen
     * @param g graphics instance to draw to the screen
     * @param gameInstance game instance
     */
    @Override
    public void draw(Graphics2D g, Game gameInstance)
    {
        Image drawImg = this.buttonImg;
        if(this.isDisabled && this.buttonDisabledImg != null)
        {
            drawImg = this.buttonDisabledImg;
        }
        else if(this.getHoveredOver())
        {
            drawImg = this.buttonHoveredImg;
        }
        RenderHelper.drawScaledImage(g, drawImg, this.getX(), this.getY(), this.getWidth(), this.getHeight(), gameInstance);
        
        if(this.getText() != null)
        {
            RenderHelper.drawCenteredString(g, this.getText(), this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, Color.white, gameInstance);
        }
    }
    
    
    //NOTE: This can be removed because it functions the same as its super class's method in Component
    /**
     * Checks to see if the button is hovered over
     * @param parent 
     * @param mouseX mouse x coordinates in psuedo space
     * @param mouseY mouse y coordinates in psuedo space
     */
    @Override
    public void checkHoveredOver(Gui parent, double mouseX, double mouseY)
    {
        super.checkHoveredOver(parent, mouseX, mouseY);
    }
    
    /**
     * Called when the button is clicked
     * @param parent parent gui
     */
    @Override
    public void click(Object parent)
    {
        //Reflection in Java used to call a method in the parent gui with the name on(name of the button)ButtonClicked
        Method method = null;
        try
        {
            method = parent.getClass().getMethod("on"+name+"ButtonClicked");
        }
        catch(NoSuchMethodException e) { e.printStackTrace(); }
        catch(SecurityException e) { e.printStackTrace(); }
        
        //Calls the method above in the parent gui
        try
        {
            if(method != null)
            {
                method.invoke(parent);
            }
        }
        catch (IllegalArgumentException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (InvocationTargetException e) { e.printStackTrace(); }
    }
    
    public void setDisabled(boolean flag)
    {
        this.isDisabled = flag;
    }
    
    public String getText() { return text; }
}
