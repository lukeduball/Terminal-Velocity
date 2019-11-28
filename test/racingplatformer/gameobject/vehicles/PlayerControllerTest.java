package racingplatformer.gameobject.vehicles;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import racingplatformer.Game;
import racingplatformer.race.Race;

public class PlayerControllerTest {

    private static Vehicle vehicle;
    private static PlayerController playerController;

    @BeforeClass
    public static void initialize()
    {
        int rid = 1;
        Game game = new Game();
        Race race = new Race(game);
        vehicle = new Porche(race, 10.0f, 10.0f, rid);
        playerController = new PlayerController(vehicle, rid);
    }

    @Test
    public void testGetControllerLabel() {
        assertEquals(playerController.getControllerLabel(), "Player 1");
    }
}
