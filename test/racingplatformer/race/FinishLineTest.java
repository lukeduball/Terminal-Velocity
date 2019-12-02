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
import racingplatformer.gameobject.vehicles.Vehicle;

/**
 *
 * @author Luke
 */
public class FinishLineTest {
    
    private static Race race;
    
    public FinishLineTest() {
    }
   
    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        race = new Race(game);
    }
    
    @Test
    public void testVehicleIsPastFinishLine()
    {
        FinishLine fnLine = new FinishLine(race, new Vec2(100.0f, 0.0f));
        Vehicle notFinishedVehicle = new Porche(race, 0.0f, 0.0f, 1);
        Vehicle finishedVehicle = new Porche(race, 100.0f, 0.0f, 2);
        
        assertFalse(fnLine.hasVehicleFinishedRace(notFinishedVehicle));
        assertTrue(fnLine.hasVehicleFinishedRace(finishedVehicle));
    }
}
