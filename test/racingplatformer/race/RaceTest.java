package racingplatformer.race;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import racingplatformer.Game;
import racingplatformer.renderengine.gui.components.VehicleSelector;

public class RaceTest {

    private static Race raceTut, raceGame;
    private static Game game;

    @BeforeClass
    public static void initialize()
    {
        game = new Game();
        raceTut = new Race(game);

        VehicleSelector[] vehicleSelectors = new VehicleSelector[2];
        vehicleSelectors[0] = new VehicleSelector(game,1, 32, 40, 200, 95);
        vehicleSelectors[1] = new VehicleSelector(game,2, 32, 40, 200, 95);
        raceGame = new Race(game, vehicleSelectors);

    }

    @Test
    public void testCheckForEndOfRace(){
        game.setActiveRace(raceTut);
        assertEquals(game.getActiveRace(), raceTut);
        raceTut.checkForEndOfRace();
        assertNull(game.getActiveGui());

        game.setActiveRace(raceGame);
        assertEquals(game.getActiveRace(), raceGame);
        raceGame.checkForEndOfRace();
        assertNull(game.getActiveGui());
    }

    @Test
    public void testGetChunk(){
        assertNotNull(raceTut.getChunk(0));
        assertNull(raceTut.getChunk(-1));
    }

    @Test
    public void testGetChunkFromLocation(){
        assertNotNull(raceTut.getChunkFromLocation(100));
        assertNull(raceTut.getChunkFromLocation(-1000));
        assertEquals(raceTut.getChunkFromLocation(10), raceTut.getChunk(0));
    }

    @Test
    public void testIsChunkLoadedAndLoadAndUnloadChunkMethods(){
        assertFalse(raceTut.isChunkLoaded(raceTut.getChunk(1)));
        raceTut.loadChunk(raceTut.getChunk(0));
        assertTrue(raceTut.isChunkLoaded(raceTut.getChunk(0)));
        raceTut.unloadChunk(raceTut.getChunk(0));
        assertFalse(raceTut.isChunkLoaded(raceTut.getChunk(0)));
    }

}
