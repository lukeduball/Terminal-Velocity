package racingplatformer.renderengine.gui;

import racingplatformer.Game;
import racingplatformer.PlayMusic;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;

import java.awt.*;

public class TutorialWinnerGui extends Gui {

    public TutorialWinnerGui(Game game){
        super(game);

        this.componentList.add(new Button("MainMenu",152 - 500/5, 280/2, 186, 36, "Main Menu"));
        this.componentList.add(new Button("RepeatTutorial",  152 + 500/5, 280/2, 186, 36, "Repeat Tutorial"));
    }

    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        RenderHelper.drawCenteredString(g, "You beat the Tutorial! Woohoo!", 500/2, 60, new Color(255, 215, 0), gameInstance);
    }

    public void onMainMenuButtonClicked(){
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/TVNegativeSFX.wav");
        }
        MainMenuGui mainMenuGui = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenuGui);
    }

    public void onRepeatTutorialButtonClicked()
    {
        if(gameInstance.getAreSoundEffectsActivated()) {
            PlayMusic.soundFX("src/resources/SFX/StartRaceSFX.wav");
        }
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance));
    }
}
