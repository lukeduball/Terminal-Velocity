/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.RenderHelper;

/**
 *
 * @author Luke
 */
public class ToggleButton extends Button
{
    private static Image offImg = ResourceManager.loadImage("src/resources/images/gui/toggle_button_off.png");
    private static Image onImg = ResourceManager.loadImage("src/resources/images/gui/toggle_button_on.png");
    private static Image hoverTransparencyImg = ResourceManager.loadImage("src/resources/images/gui/toggle_button_hover.png");
    
    private boolean isOn;
    
    private String onText;
    private String offText;
    private Game gameInstance;

    public ToggleButton(Game game, String n, float x1, float y1, float w, float h, boolean initialState)
    {
        super(n, x1, y1, w, h);
        this.gameInstance = game;
        this.isOn = initialState;
        this.onText = "on";
        this.offText = "off";
    }
    
    public ToggleButton(Game game, String n, float x1, float y1, float w, float h, boolean initialState, String onT, String offT)
    {
        this(game, n, x1, y1, w, h, initialState);
        this.gameInstance = game;
        this.onText = onT;
        this.offText = offT;
    }
    
    @Override
    public void draw(Graphics2D g, Game gameInstance)
    {
        Image drawImg = offImg;
        String text = this.offText;
        float textXPos = this.getX() + this.getWidth() * 0.26f;
        if(this.getIsOn())
        {
            drawImg = onImg;
            text = this.onText;
            textXPos = this.getX() + (this.getWidth() - this.getWidth() * 0.28f);
        }
        RenderHelper.drawScaledImage(g, drawImg, this.getX(), this.getY(), this.getWidth(), this.getHeight(), gameInstance);
        
        //Draw the on/off text
        float scale = this.getWidth() / 70;
        RenderHelper.drawCenteredString(g, text, textXPos, this.getY() + this.getHeight() / 2, Color.white, gameInstance, 0.6f*scale);
        
        //Draws the transparency over the image to tell that it is being hovered over
        if(this.getHoveredOver())
        {
            RenderHelper.drawScaledImage(g, hoverTransparencyImg, this.getX(), this.getY(), this.getWidth(), this.getHeight(), gameInstance);
        }
    }
    
    @Override
    public void click(Object parent)
    {
        this.toggle();
        super.click(parent);
    }
    
    public void toggle()
    {
        if(this.getIsOn())
        {
            this.isOn = false;
        }
        else
        {
            this.isOn = true;
        }
    }
    
    public boolean getIsOn()
    {
        return this.isOn;
    }
    
}
