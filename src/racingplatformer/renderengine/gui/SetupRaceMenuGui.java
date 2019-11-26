/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.VehicleSelector;

/**
 *
 * @author Luke
 */
public class SetupRaceMenuGui extends Gui
{
    
    private VehicleSelector[] vehicleData;
    
    public SetupRaceMenuGui(Game game) 
    {
        super(game);
        vehicleData = new VehicleSelector[4];
        vehicleData[0] = new VehicleSelector(game,1, 32, 40, 200, 95);
        vehicleData[1] = new VehicleSelector(game, 2, 262, 40, 200, 95);
        vehicleData[2] = new VehicleSelector(game,3, 32, 145, 200, 95);
        vehicleData[3] = new VehicleSelector(game,4, 262, 145, 200, 95);
        for(int i = 0; i < vehicleData.length; i++)
        {
            this.componentList.add(vehicleData[i]);
        }
        this.componentList.add(new Button("Race", 160, 244, 185, 35, "RACE!"));
        this.componentList.add(new Button("MainMenu", 30, 244, 100, 35, "Main Menu"));
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        
        RenderHelper.drawCenteredString(g, "Setup Race!", 500 / 2, 15, Color.white, this.gameInstance, 1.5f);
    }
    
    public void onRaceButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/StartRaceSFX.wav", gameInstance);
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance, this.vehicleData));
    }
    
    public void onMainMenuButtonClicked()
    {
        PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav", gameInstance);
        MainMenuGui mainMenu = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenu);
    }
    
}
