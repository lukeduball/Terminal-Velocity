package racingplatformer.renderengine.gui;

import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;

import java.awt.*;

public class WinnerGui extends Gui{
    public WinnerGui(Game game, int[] placements){
        super(game);
        this.componentList.add(new Button("MainMenu",152 - 500/5, 222, 186, 36, "Main Menu"));
        this.componentList.add(new Button("RaceAgain",  152 + 500/5, 222, 186, 36, "Race Again"));
    }

    @Override
    public void draw(Graphics2D g){
        //TODO read in placements[] to draw who won, and whether they were an AI or player "AI or Player" + " ID"
        Color gold = new Color(255, 215, 0);
        Color silver = new Color(192, 192, 192);
        Color bronze = new Color(205, 127, 50);
        String player = "Player ";
        String AI = "AI ";


        super.draw(g);
        RenderHelper.drawCenteredString(g, "Race Results", 500/2, 20, Color.white, this.gameInstance, 1.5f);
        RenderHelper.drawString(g, "1st Place:", 500/3, 70, gold, gameInstance);
        RenderHelper.drawString(g, "2nd Place:", 500/3, 110, silver, gameInstance);
        RenderHelper.drawString(g, "3rd Place:", 500/3, 150, bronze, gameInstance);
        RenderHelper.drawString(g, "4th Place:", 500/3, 190, Color.black, gameInstance);
        RenderHelper.drawRightAlignedString(g, player, 1000/3, 70, gold, gameInstance);
        RenderHelper.drawRightAlignedString(g, player, 1000/3, 110, silver, gameInstance);
        RenderHelper.drawRightAlignedString(g, player, 1000/3, 150, bronze, gameInstance);
        RenderHelper.drawRightAlignedString(g, player, 1000/3, 190, Color.black, gameInstance);

    }

    public void onMainMenuButtonClicked(){
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav");
        }
        MainMenuGui mainMenuGui = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenuGui);
    }

    public void onRaceAgainButtonClicked()
    {
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/StartRaceSFX.wav");
        }
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance));
    }
}
