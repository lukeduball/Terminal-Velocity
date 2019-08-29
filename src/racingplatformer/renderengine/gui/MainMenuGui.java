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

/**
 *
 * @author Luke
 */
public class MainMenuGui extends Gui
{
    private static Image mainMenuImage = ResourceManager.loadImage("src/resources/images/gui/main_menu_background.png");
    
    TutorialPopupGui tutorialPopup;
    boolean flag = false;
    
    public MainMenuGui(Game game)
    {
        super(game);
        backgroundImg = mainMenuImage;
        //Adds the buttons to the list so that they get input and are drawn to the screen
        this.componentList.add(new Button("Race", 264, 49, 185, 35, "Race"));
        this.componentList.add(new Button("Options", 264, 106, 185, 35, "Options"));
        this.componentList.add(new Button("Help", 264, 163, 185, 35, "Tutorial"));
        this.componentList.add(new Button("Exit", 264, 220, 185, 35, "Exit"));
        
        this.tutorialPopup = new TutorialPopupGui(gameInstance, this);
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
        RenderHelper.drawString(g, "Terminal Velocity", 271, 35, Color.white, this.gameInstance, 1.5f);
        
        if(this.currentPopup != null)
        {
            this.currentPopup.draw(g);
        }
    }
    
    /**
     * Called when the race button is clicked
     */
    public void onRaceButtonClicked()
    {
        SetupRaceMenuGui setupRaceMenu = new SetupRaceMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(setupRaceMenu);
    }
    
    /**
     * Called when the options button is clicked
     */
    public void onOptionsButtonClicked()
    {
        OptionsMenuGui optionsGui = new OptionsMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(optionsGui);
    }
    
    /**
     * Called when the help button is clicked
     */
    public void onHelpButtonClicked()
    {
        this.setCurrentPopup(this.tutorialPopup);
        //this.setCurrentPopup(new PlayerControlsPopupGui(this.gameInstance, this));
    }
    
    /**
     * Called when the exit button is clicked
     */
    public void onExitButtonClicked()
    {
        //Closes the program
        System.exit(0);
    }
}
