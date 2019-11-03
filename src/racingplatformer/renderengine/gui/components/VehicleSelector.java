/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.Gui;
import racingplatformer.renderengine.gui.RenderHelper;

/**
 *
 * @author Luke
 */
public class VehicleSelector extends Component
{
    private int racerID;
    
    private static final Image[] vehicleImage = {
    ResourceManager.loadImage("src/resources/images/vehicles/porche.png"),
    ResourceManager.loadImage("src/resources/images/vehicles/muscle_car.png"),
    ResourceManager.loadImage("src/resources/images/vehicles/rally_racer.png"),
    ResourceManager.loadImage("src/resources/images/vehicles/monster_truck.png")
    };
    
    private static final Image lArrowButtonImg = ResourceManager.loadImage("src/resources/images/gui/left_arrow.png");
    private static final Image lArrowButtonHoveredImg = ResourceManager.loadImage("src/resources/images/gui/left_arrow_hovered.png");
    private static final Image lArrowButtonDisabledImg = ResourceManager.loadImage("src/resources/images/gui/left_arrow_disabled.png");
    
    private static final Image rArrowButtonImg = ResourceManager.loadImage("src/resources/images/gui/right_arrow.png");
    private static final Image rArrowButtonHoveredImg = ResourceManager.loadImage("src/resources/images/gui/right_arrow_hovered.png");
    private static final Image rArrowButtonDisabledImg = ResourceManager.loadImage("src/resources/images/gui/right_arrow_disabled.png");
    
    private ArrayList<Component> componentList;
    
    private Button leftArrow;
    private Button rightArrow;
    
    private int currentIndex;

    private Game gameInstance;
    
    public VehicleSelector(Game game, int rID, float x, float y, float w, float h)
    {
        super(x, y, w, h);

        this.gameInstance = game;

        this.componentList = new ArrayList<>();
        this.racerID = rID;
        this.currentIndex = 0;
        
        if(this.racerID > 2)
        {
            this.componentList.add(new ToggleButton(game, "ActivatedToggle", this.getX() + 70, this.getY() + 2, 30, 15, false));
        }
        
        if(this.racerID > 1)
        {
            this.componentList.add(new ToggleButton(game,"PlayerToggle", this.getX() + 165, this.getY() + 2, 30, 15, false, "AI", "Player"));
        }
        
        this.componentList.add(leftArrow = new Button("LeftArrow", this.getX() + 10, this.getY() + 25, 10, 60, lArrowButtonImg, lArrowButtonHoveredImg, lArrowButtonDisabledImg));
        this.componentList.add(rightArrow = new Button("RightArrow", this.getX() + 110, this.getY() + 25, 10, 60, rArrowButtonImg, rArrowButtonHoveredImg, rArrowButtonDisabledImg));
        leftArrow.setDisabled(true);
    }
    
    @Override
    public void checkHoveredOver(Gui parent, double mouseX, double mouseY)
    {
        super.checkHoveredOver(parent, mouseX, mouseY);
        for(Component c : this.componentList)
        {
            c.checkHoveredOver(parent, mouseX, mouseY);
        }
    }
    
    @Override
    public void click(Object parent)
    {
        for(Component c : this.componentList)
        {
            if(c.getHoveredOver())
            {
                c.click(this);
            }
        }
    }
    
    /**
     * Draws the component to the screen
     * @param g graphics instance to draw to the screen
     * @param gameInstance game instance
     */
    @Override
    public void draw(Graphics2D g, Game gameInstance)
    {
        //White Border
        RenderHelper.drawScaledRect(g, this.getX() - 2, this.getY() - 2, this.getWidth() + 4, this.getHeight() + 4, Color.white, gameInstance);
        
        Color darkRed = new Color(144, 19, 19);
        RenderHelper.drawScaledRect(g, this.getX(), this.getY(), this.getWidth(), this.getHeight(), darkRed, gameInstance);
    
        RenderHelper.drawScaledRect(g, this.getX() + 20, this.getY() + 25, 90, 60, Color.black, gameInstance);
        RenderHelper.drawVehicle(g, vehicleImage[this.currentIndex], this.getX() + 25, this.getY() + 35, 80, gameInstance);
        
        RenderHelper.drawScaledRect(g, this.getX(), this.getY() + 20, this.getWidth(), 2, Color.white, gameInstance);
        
        RenderHelper.drawString(g, "Racer "+this.racerID, this.getX() + 10, this.getY() + 15, Color.white, gameInstance);
   
        RenderHelper.drawCenteredString(g, "Stats", this.getX() + 160, this.getY() + 30, Color.white, gameInstance, 0.5f);
        if(currentIndex == 0){
            RenderHelper.drawString(g, "Speed: 5", this.getX() + 125, this.getY() + 45, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Acceleration: 5", this.getX() + 125, this.getY() + 60, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Weight: 1", this.getX() + 125, this.getY() + 75, Color.white, gameInstance, 0.5f);
        }
        else if(currentIndex == 1){
            RenderHelper.drawString(g, "Speed: 5", this.getX() + 125, this.getY() + 45, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Acceleration: 4", this.getX() + 125, this.getY() + 60, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Weight: 2", this.getX() + 125, this.getY() + 75, Color.white, gameInstance, 0.5f);
        }
        else if(currentIndex == 2){
            RenderHelper.drawString(g, "Speed: 4", this.getX() + 125, this.getY() + 45, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Acceleration: 5", this.getX() + 125, this.getY() + 60, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Weight: 2", this.getX() + 125, this.getY() + 75, Color.white, gameInstance, 0.5f);
        }
        else {
            RenderHelper.drawString(g, "Speed: 4", this.getX() + 125, this.getY() + 45, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Acceleration: 3", this.getX() + 125, this.getY() + 60, Color.white, gameInstance, 0.5f);
            RenderHelper.drawString(g, "Weight: 4", this.getX() + 125, this.getY() + 75, Color.white, gameInstance, 0.5f);
        }

        
        for(Component c : this.componentList)
        {
            c.draw(g, gameInstance);
        }
    }
    
    public void onLeftArrowButtonClicked()
    {
        if(this.currentIndex > 0)
        {
            if(gameInstance.getAreSoundEffectsActivated()) {
                PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav");
            }
            this.currentIndex--;
            this.rightArrow.setDisabled(false);
        }


        
        if(this.currentIndex == 0){
            this.leftArrow.setDisabled(true);
        }
    }
    
    public void onRightArrowButtonClicked()
    {
        if(this.currentIndex < vehicleImage.length - 1)
        {
            if(gameInstance.getAreSoundEffectsActivated()) {
                PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav");
            }
            this.currentIndex ++;
            this.leftArrow.setDisabled(false);
        }

        if(this.currentIndex == vehicleImage.length - 1)
        {
            this.rightArrow.setDisabled(true);
        }
    }
    
    public void onActivatedToggleButtonClicked()
    {
//        if(gameInstance.getAreSoundEffectsActivated() && ) {
//            PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav");
//        }
        
    }
    
    public void onPlayerToggleButtonClicked()
    {
        
    }
}