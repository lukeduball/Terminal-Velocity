/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import racingplatformer.Game;
import racingplatformer.gameobject.vehicles.Porche;
import racingplatformer.gameobject.vehicles.Vehicle;
import racingplatformer.race.Chunk;
import racingplatformer.race.Race;

/**
 *
 * @author Luke
 */
public class ScreenTest {
    private static Screen screen;
    private static Race race;
    private static Vehicle focusVehicle;
    
    public ScreenTest() {
    }

    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        race = new Race(game);
        focusVehicle = new Porche(race, 0.0f, 0.0f, 1);
        screen = new Screen(1, race, focusVehicle);
    }
    
    @Test
    public void testGetChunkToUnload()
    {
        Chunk[] chunkArr = new Chunk[5];
        for(int i = 0; i < 5; i++)
        {
            chunkArr[i] = new Chunk(race, i);
        }
        
        screen.getLoadedChunks()[0] = chunkArr[1];
        screen.getLoadedChunks()[1] = chunkArr[2];
        screen.getLoadedChunks()[2] = chunkArr[3];
        
        assertEquals(screen.getChunkToUnload(chunkArr[3], screen.getLoadedChunks()[1]), chunkArr[1]);
        assertEquals(screen.getChunkToUnload(chunkArr[0], screen.getLoadedChunks()[1]), chunkArr[3]);
        assertNull(screen.getChunkToUnload(null, null));
    }
    
    @Test
    public void testGetWorldRenderX()
    {
        assertEquals(screen.getWorldRenderX(), -(float)Chunk.CHUNK_WIDTH, 0.01f);
    }
    
    @Test
    public void testWorldRenderY()
    {
        assertEquals(screen.getWorldRenderY(), -(float)Chunk.CHUNK_HEIGHT / 1.5f, 0.01f);
    }
    
    @Test
    public void testIsActiveChunk()
    {
        Chunk[] chunkArr = new Chunk[5];
        for(int i = 0; i < 5; i++)
        {
            chunkArr[i] = new Chunk(race, i);
        }
        
        screen.getLoadedChunks()[0] = chunkArr[1];
        screen.getLoadedChunks()[1] = chunkArr[2];
        screen.getLoadedChunks()[2] = chunkArr[3];
        
        assertTrue(screen.isActiveChunk(chunkArr[1]));
        assertTrue(screen.isActiveChunk(chunkArr[2]));
        assertTrue(screen.isActiveChunk(chunkArr[3]));
        
        assertFalse(screen.isActiveChunk(chunkArr[0]));

    }
    
    
    
}
