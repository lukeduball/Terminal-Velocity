/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;

/**
 *
 * @author Luke
 */
public class TutorialPopupGui extends PopupGui
{  
    public TutorialPopupGui(Game game, Gui parent) 
    {
        super(game, parent);
        this.backgroundImg = null;
        //Add buttons here into the button list
        this.componentList.add(new Button("Yes", 162, 145, 80, 25, "Yes"));
        this.componentList.add(new Button("No", 249, 145, 80, 25, "No"));
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        //To make the color these are the Red, Blue, Green, and Alpha values between from 0-255
        Color shadedColor = new Color(66, 66, 66, 128);
        RenderHelper.drawScaledBackgroundRect(g, shadedColor, gameInstance);
        
        //White Border
        RenderHelper.drawScaledRect(g, 145, 75, 210, 110, Color.white, gameInstance);
        
        Color solidGrey = new Color(170, 170, 170);
        RenderHelper.drawScaledRect(g, 150, 80, 200, 100, solidGrey, gameInstance);
        
        super.draw(g);
        //Draw text strings below
        RenderHelper.drawCenteredString(g, "Proceed with Tutorial?", 250, 115, Color.white, gameInstance, 1f);
    }
    
    //Add button call methods below -- To close the popup just call its close method
    public void onNoButtonClicked(){
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav");
        }
        close();
    }

    public void onYesButtonClicked(){
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/StartRaceSFX.wav");
        }
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance));
    }
}
