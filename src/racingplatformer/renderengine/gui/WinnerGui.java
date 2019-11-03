package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import racingplatformer.Game;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.components.Button;

public class WinnerGui extends Gui{
    private List finishList;
    
    public WinnerGui(Game game, List<Integer> fnList)
    {
        super(game);
        this.finishList = fnList;
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
        
        Color[] colors = new Color[]{new Color(255, 215, 0), new Color(192, 192, 192), new Color(205, 127, 50), Color.black};
        String[] place = new String[]{"1st Place", "2nd Place", "3rd Place", "4th Place"};


        super.draw(g);
        RenderHelper.drawCenteredString(g, "Race Results", 500/2, 20, Color.white, this.gameInstance, 1.5f);
        for(int i = 0; i < this.finishList.size(); i++)
        {
            RenderHelper.drawString(g, place[i], 500/3, 70 + 40*i, colors[i], gameInstance);
            RenderHelper.drawRightAlignedString(g, player+this.finishList.get(i), 1000/3, 70 + 40*i, colors[i], gameInstance);
        }
    }

    public void onMainMenuButtonClicked(){
        MainMenuGui mainMenuGui = new MainMenuGui(this.gameInstance);
        this.gameInstance.setActiveGui(mainMenuGui);
    }

    public void onRaceAgainButtonClicked()
    {
        System.out.println("Start Race!");
        this.gameInstance.setActiveGui(null);
        this.gameInstance.setActiveRace(new Race(this.gameInstance));
    }
}
