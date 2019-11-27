/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import racingplatformer.Game;
import racingplatformer.race.Race;

/**
 *
 * @author Luke
 */
public class VehicleTest 
{
    private static Vehicle vehicle;
    
    public VehicleTest()
    {
    
    }
    
    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        Race race = new Race(game);
        vehicle = new Porche(race, 10.0f, 10.0f, 1);
        AIController aiController = new AIController(vehicle);
        vehicle.setMovementController(aiController);
    }
    
    @Test
    public void testControllers()
    {
        vehicle.setRacing(false);
        vehicle.processControllers();
        assertEquals(vehicle.getFrontWheelSpring().getMotorSpeed(), 0.0f, 0.0001f);
    }
    
    @Test
    public void testIdleTimer()
    {
        vehicle.processIdleStatus();
        assertEquals(vehicle.getIdleTimer().isActive(), false);
        vehicle.processIdleStatus();
        assertEquals(vehicle.getIdleTimer().isActive(), true);
    }

    
}
