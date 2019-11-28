package racingplatformer.gameobject.vehicles;

import org.junit.BeforeClass;
import org.junit.Test;
import racingplatformer.Game;
import racingplatformer.race.Race;

import static org.junit.Assert.assertEquals;

public class AIControllerTest {

    private static Vehicle vehicle;
    private static AIController aiController;

    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        Race race = new Race(game);
        vehicle = new Porche(race, 10.0f, 10.0f, 1);
        aiController = new AIController(vehicle);
    }

    @Test
    public void testGetControllerLabel() {
        assertEquals(aiController.getControllerLabel(), "CPU");
    }
}
