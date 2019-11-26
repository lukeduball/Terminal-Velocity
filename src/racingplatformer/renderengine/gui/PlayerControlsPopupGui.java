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
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.CharField;

/**
 *
 * @author Luke
 */
public class PlayerControlsPopupGui extends PopupGui
{
    private CharField[] inputFields;
    private Button[] pButtons;
    private int selectedPlayer;
    
    public PlayerControlsPopupGui(Game game, Gui parent)
    {
        super(game, parent);
        this.backgroundImg = null;
        this.inputFields = new CharField[4];
        this.pButtons = new Button[4];
        this.selectedPlayer = 0;
        //add components to components list here
        this.componentList.add(new Button("Exit", 176, 210, 148, 25, "Exit"));
        this.componentList.add(inputFields[Game.FORWARD] = new CharField("Forward", 316, 97, 30, 30, 87));
        this.componentList.add(inputFields[Game.TILT_UP] = new CharField("TiltL", 282, 131, 30, 30, 65));
        this.componentList.add(inputFields[Game.TILT_DOWN] = new CharField("TiltR", 350, 131, 30, 30, 68));
        this.componentList.add(inputFields[Game.BACKWARD] = new CharField("Back", 316, 131, 30, 30, 83));
        this.componentList.add(pButtons[0] = new Button("P1", 96, 70, 60, 30, "P1"));
        this.componentList.add(pButtons[1] = new Button("P2", 96, 102, 60, 30, "P2"));
        this.componentList.add(pButtons[2] = new Button("P3", 96, 134, 60, 30, "P3"));
        this.componentList.add(pButtons[3] = new Button("P4", 96, 166, 60, 30, "P4"));
        
        this.changePlayerInput(selectedPlayer);
    }
    
    public void updateInputFields()
    {
        int[] keyControls = this.gameInstance.getPlayerControlKeys(this.selectedPlayer);
        for(int i = 0; i < 4; i++)
        {
            this.inputFields[i].setKeyCode(keyControls[i]);
        }
                
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        //To make the color these are the Red, Blue, Green, and Alpha values between from 0-255
        Color shadedColor = new Color(66, 66, 66, 128);
        RenderHelper.drawScaledBackgroundRect(g, shadedColor, gameInstance);
        
        //White Border
        RenderHelper.drawScaledRect(g, 45, 45, 410, 210, Color.white, gameInstance);
        
        Color solidGrey = new Color(170, 170, 170);
        RenderHelper.drawScaledRect(g, 50, 50, 400, 200, solidGrey, gameInstance);
        
        super.draw(g);
        //Draw text strings below
        RenderHelper.drawCenteredString(g, "Customize your controls below!", 333, 72, Color.white, gameInstance, 0.5f);
        RenderHelper.drawString(g, "Accelerate", 315, 92, Color.white, gameInstance, 0.4f);
        RenderHelper.drawString(g, "Tilt Left", 286, 169, Color.white, gameInstance, 0.4f);
        RenderHelper.drawString(g, "Tilt Right", 352, 169, Color.white, gameInstance, 0.4f);
        RenderHelper.drawString(g, "Reverse", 320, 169, Color.white, gameInstance, 0.4f);
        RenderHelper.drawCenteredString(g, "One key mapped to multiple controls isn't a good idea!", 333, 190, Color.white, gameInstance, 0.5f);
    }
    
    //Add button methods here -- to close the popup call the close method
    public void onExitButtonClicked() {
        PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav", gameInstance);
        close();
    }
    
    public void onP1ButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.changePlayerInput(0);
    }
    
    public void onP2ButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.changePlayerInput(1);
    }
        
    public void onP3ButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.changePlayerInput(2);
    }
            
    public void onP4ButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.changePlayerInput(3);
    }
    
    private void changePlayerInput(int playerID)
    {
        this.selectedPlayer = playerID;
        for(int i = 0; i < 4; i++)
        {
            pButtons[i].setDisabled(false);
        }
        pButtons[playerID].setDisabled(true);
        this.updateInputFields();
    }
    
    public void onForwardKeyFieldTyped()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.gameInstance.getPlayerControlKeys(this.selectedPlayer)[Game.FORWARD] = this.inputFields[Game.FORWARD].getKeyCode();
    }
    
    public void onTiltLKeyFieldTyped()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.gameInstance.getPlayerControlKeys(this.selectedPlayer)[Game.TILT_UP] = this.inputFields[Game.TILT_UP].getKeyCode();
    }
        
    public void onTiltRKeyFieldTyped()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.gameInstance.getPlayerControlKeys(this.selectedPlayer)[Game.TILT_DOWN] = this.inputFields[Game.TILT_DOWN].getKeyCode();
    }
    
    public void onBackKeyFieldTyped()
    {
        PlayMusic.soundFX("src/resources/SFX/TVAffirmativeSFX.wav", gameInstance);
        this.gameInstance.getPlayerControlKeys(this.selectedPlayer)[Game.BACKWARD] = this.inputFields[Game.BACKWARD].getKeyCode();
    }
    
}
