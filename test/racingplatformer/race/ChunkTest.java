/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import org.jbox2d.common.Vec2;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import racingplatformer.Game;
import racingplatformer.gameobject.vehicles.Porche;

/**
 *
 * @author Luke
 */
public class ChunkTest {
    
    private static Race race;
    
    public ChunkTest() 
    {
        
    }
    
    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        race = new Race(game);
    }
    
    @Test
    public void testAddGameObject()
    {
        Chunk chunk = new Chunk(race, 0);
        Porche p = new Porche(race, 0.0f, 0.0f, 1);
        chunk.addGameObject(p);
        assertTrue(chunk.getGameObjectList().contains(p));
        
        //Make sure a game object can not be added to the list twice
        int listSize = chunk.getGameObjectList().size();
        chunk.addGameObject(p);
        assertEquals(chunk.getGameObjectList().size(), listSize);
    }
    
    @Test
    public void testRemoveGameObject()
    {
        Chunk chunk = new Chunk(race, 0);
        Porche p = new Porche(race, 0.0f, 0.0f, 1);
        chunk.addGameObject(p);
        assertTrue(chunk.getGameObjectList().contains(p));
        chunk.removeGameObject(p);
        assertFalse(chunk.getGameObjectList().contains(p));
    }
    
    @Test
    public void testAddBoundary()
    {
        Chunk chunk = new Chunk(race, 0);
        TextSegment t = new TextSegment(race.getWorld(), "", new Vec2(0.0f, 0.0f));
        chunk.addBoundary(t);
        assertTrue(chunk.getBoundaryList().contains(t));
        
        //Make sure a game object can not be added to the list twice
        int listSize = chunk.getBoundaryList().size();
        chunk.addBoundary(t);
        assertEquals(chunk.getBoundaryList().size(), listSize);
    }
    
}
