package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;
import racingplatformer.renderengine.gui.components.VehicleSelector;

public class WinnerGui extends Gui{
    private final List finishList;
    private final List disqualificationList;
    private final VehicleSelector[] selectorData;
    
    public WinnerGui(Game game, List<Integer> fnList, List<Integer> dqList, VehicleSelector[] data)
    {
        super(game);
        this.selectorData = data;
        this.finishList = fnList;
        this.disqualificationList = dqList;
        this.componentList.add(new Button("MainMenu",152 - 500/5, 222, 186, 36, "Main Menu"));
        this.componentList.add(new Button("RaceAgain",  152 + 500/5, 222, 186, 36, "Race Again"));
    }

    @Override
    public void draw(Graphics2D g){
        Color gold = new Color(255, 215, 0);
        Color silver = new Color(192, 192, 192);
        Color bronze = new Color(205, 127, 50);
        //TODO:: Add a way to determine if the winner is an AI or a player
        String player = "Player ";
        String AI = "AI ";
        
        Color[] colors = new Color[]{new Color(255, 215, 0), new Color(192, 192, 192), new Color(205, 127, 50), Color.black};
        String[] place = new String[]{"1st Place", "2nd Place", "3rd Place", "4th Place"};


        super.draw(g);
        RenderHelper.drawCenteredString(g, "Race Results", 500/2, 20, Color.white, this.gameInstance, 1.5f);
        int i = 0;
        for(i = 0; i < this.finishList.size(); i++)
        {
            RenderHelper.drawString(g, place[i], 500/3, 70 + 40*i, colors[i], gameInstance);
            RenderHelper.drawRightAlignedString(g, player+this.finishList.get(i), 1000/3, 70 + 40*i, colors[i], gameInstance);
        }
        
        for(int j = 0; j < this.disqualificationList.size(); j++)
        {
            RenderHelper.drawString(g, "Disqualified", 500/3, 70 + 40*i, Color.gray, gameInstance);
            RenderHelper.drawRightAlignedString(g, player+this.disqualificationList.get(j), 1000/3, 70 + 40*i, Color.gray, gameInstance);
            i++;
        }
    }

    public void onMainMenuButtonClicked(){
        //if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav", gameInstance);
        //}
        MainMenuGui mainMenuGui = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenuGui);
    }

    public void onRaceAgainButtonClicked()
    {
       // if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/StartRaceSFX.wav", gameInstance);
        //}
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance, this.selectorData));
    }
}
