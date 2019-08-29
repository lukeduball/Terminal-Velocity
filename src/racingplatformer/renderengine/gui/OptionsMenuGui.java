/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.ToggleButton;

/**
 *
 * @author Luke
 */
public class OptionsMenuGui extends Gui
{
    private static Image optionsMenu = ResourceManager.loadImage("src/resources/images/gui/blue_gradient_background.png");
    PlayerControlsPopupGui pConGUI;
    
    
    public OptionsMenuGui(Game game) 
    {
        super(game);
        backgroundImg = optionsMenu;
        this.componentList.add(new Button("PlayerControls", 152, 164, 186, 36, "Player Controls"));
        this.componentList.add(new Button("MainMenu", 152, 222, 186, 36, "Main Menu"));
        this.componentList.add(new ToggleButton("MusicToggle", 245, 55, 70, 35, gameInstance.getIsMusicActivated()));
        this.componentList.add(new ToggleButton("SoundToggle", 245, 103, 70, 35, gameInstance.getAreSoundEffectsActivated()));
    }
    
    public void onMainMenuButtonClicked()
    {
        MainMenuGui mainMenuGUI = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenuGUI);
    }
    
    /**
     * Draws the button to the screen
     * @param g graphics instance to draw to the screen
     */
    @Override
    public void draw(Graphics2D g)
    {
        //Calls the parent Gui's draw method
        super.draw(g);
        //Draws the terminal velocity with a 1.5 scale to make the font larger
        RenderHelper.drawString(g, "Options", 198, 30, Color.white, this.gameInstance, 1.5f);
        RenderHelper.drawRightAlignedString(g, "Music", 203, 77, Color.white, gameInstance);
        RenderHelper.drawRightAlignedString(g, "Sound", 203, 124, Color.white, gameInstance);
        
        
        if(this.currentPopup != null)
        {
            this.currentPopup.draw(g);
        }
    }
    
    /**
     * Called when the PlayerControls button is clicked
     */
    public void onPlayerControlsButtonClicked()
    {
        this.setCurrentPopup(new PlayerControlsPopupGui(this.gameInstance, this));
    }
    
    public void onMusicToggleButtonClicked()
    {
        this.gameInstance.toggleMusic();
    }
    
    public void onSoundToggleButtonClicked()
    {
        this.gameInstance.toggleSoundEffects();
    }
    
}
