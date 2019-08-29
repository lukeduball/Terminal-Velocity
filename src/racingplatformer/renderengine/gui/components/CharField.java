/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import racingplatformer.Game;
import racingplatformer.renderengine.gui.Gui;
import racingplatformer.renderengine.gui.RenderHelper;

/**
 *
 * @author Luke
 */
public class CharField extends Component
{
    private int keyCode;
    private String name;
    
    public CharField(String name, float x1, float y1, float w, float h, int keyCode) 
    {
        super(x1, y1, w, h);
        this.keyCode = keyCode;
        this.name = name;
    }
    
    @Override
    public void draw(Graphics2D g, Game gameInstance)
    {
        Color outlineColor = Color.white;
        if(this.getFocused())
        {
            outlineColor = Color.yellow;
        }
        RenderHelper.drawScaledRect(g, this.getX() - 1, this.getY() - 1, this.getWidth() + 2, this.getHeight() + 2, outlineColor, gameInstance);
        Color darkRed = new Color(144, 19, 19);
        RenderHelper.drawScaledRect(g, this.getX(), this.getY(), this.getWidth(), this.getHeight(), darkRed, gameInstance);
        
        float textScale = 0.5f;
        RenderHelper.drawCenteredString(g, KeyEvent.getKeyText(this.keyCode)+"", this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, Color.white, gameInstance, textScale);
        
        if(this.getHoveredOver())
        {
            Color hoveredColor = new Color(255, 255, 255, 128);
            RenderHelper.drawScaledRect(g, this.getX(), this.getY(), this.getWidth(), this.getHeight(), hoveredColor, gameInstance);
        }
    }
    
    /**
    * Called when the button is clicked
    * @param parent parent gui
    */
    @Override
    public void click(Object parent)
    {
        this.setFocus(true);
    }
    
    @Override
    public void onKeyPressed(KeyEvent e, Gui parent)
    {
        if(this.getFocused())
        {
            this.keyCode = e.getKeyCode();
        }
        //Reflection in Java used to call a method in the parent gui with the name on(name of the button)ButtonClicked
        Method method = null;
        try
        {
            method = parent.getClass().getMethod("on"+name+"KeyFieldTyped");
        }
        catch(NoSuchMethodException ex) { ex.printStackTrace(); }
        catch(SecurityException ex) { ex.printStackTrace(); }
        
        //Calls the method above in the parent gui
        try
        {
            if(method != null)
            {
                method.invoke(parent);
            }
        }
        catch (IllegalArgumentException ex) { ex.printStackTrace(); }
        catch (IllegalAccessException ex) { ex.printStackTrace(); }
        catch (InvocationTargetException ex) { ex.printStackTrace(); }
    }
    
    public int getKeyCode()
    {
        return this.keyCode;
    }
    
    public void setKeyCode(int key)
    {
        this.keyCode = key;
    }
    
}
