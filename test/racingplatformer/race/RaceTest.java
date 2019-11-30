package racingplatformer.race;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import racingplatformer.Game;
//import racingplatformer.renderengine.gui.components.VehicleSelector;

public class RaceTest {

    private static Race race;

    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
//        VehicleSelector[] vehicleSelectors = new VehicleSelector[1];
//        vehicleSelectors[0] = new VehicleSelector(game,1, 32, 40, 200, 95);
        race = new Race(game);
    }

    @Test
    public void testCheckForEndOfRace(){
        //private method
    }

    @Test
    public void testGetChunk(){
        assertTrue(race.getChunk(0) != null);
        assertNull(race.getChunk(-1));
    }

    @Test
    public void testGetChunkFromLocation(){
        assertTrue(race.getChunkFromLocation(100) != null);
        assertNull(race.getChunkFromLocation(-1000));
        assertEquals(race.getChunkFromLocation(10), race.getChunk(0));
    }

    @Test
    public void testIsChunkLoadedAndLoadAndUnloadChunkMethods(){
        assertFalse(race.isChunkLoaded(race.getChunk(1)));
        race.loadChunk(race.getChunk(0));
        assertTrue(race.isChunkLoaded(race.getChunk(0)));
        race.unloadChunk(race.getChunk(0));
        assertFalse(race.isChunkLoaded(race.getChunk(0)));
    }

}
