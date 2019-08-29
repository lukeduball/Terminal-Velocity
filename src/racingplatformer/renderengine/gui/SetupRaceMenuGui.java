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
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.VehicleSelector;

/**
 *
 * @author Luke
 */
public class SetupRaceMenuGui extends Gui
{
    
    public SetupRaceMenuGui(Game game) 
    {
        super(game);
        this.componentList.add(new VehicleSelector(1, 32, 40, 200, 95));
        this.componentList.add(new VehicleSelector(2, 262, 40, 200, 95));
        this.componentList.add(new VehicleSelector(3, 32, 145, 200, 95));
        this.componentList.add(new VehicleSelector(4, 262, 145, 200, 95));
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
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        
    }
    
    public void onMainMenuButtonClicked()
    {
        MainMenuGui mainMenu = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenu);
    }
    
}
