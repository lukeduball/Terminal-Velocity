package racingplatformer.race;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import racingplatformer.Game;
import racingplatformer.renderengine.gui.components.VehicleSelector;

public class RaceTest {

    private static Race raceTut, raceGame;
    private static Game gameTut, gameRace;

    @BeforeClass
    public static void initialize()
    {
        gameTut = new Game();
        raceTut = new Race(gameTut);

        gameRace = new Game();
        VehicleSelector[] vehicleSelectors = new VehicleSelector[2];
        vehicleSelectors[0] = new VehicleSelector(gameRace,1, 32, 40, 200, 95);
        vehicleSelectors[1] = new VehicleSelector(gameRace,2, 32, 40, 200, 95);
        raceGame = new Race(gameRace, vehicleSelectors);

    }

    @Test
    public void testCheckForEndOfRace(){
        raceTut.checkForEndOfRace();
        assertNull(gameTut.getActiveGui());

        raceGame.checkForEndOfRace();
        assertNull(gameRace.getActiveGui());
    }

    @Test
    public void testGetChunk(){
        assertTrue(raceTut.getChunk(0) != null);
        assertNull(raceTut.getChunk(-1));
    }

    @Test
    public void testGetChunkFromLocation(){
        assertTrue(raceTut.getChunkFromLocation(100) != null);
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
