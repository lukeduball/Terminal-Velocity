/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import racingplatformer.race.Race;

/**
 *
 * @author Luke
 */
public class AIController extends Controller
{
    public AIController(Vehicle vehicle)
    {
        super(vehicle);
    }

    @Override
    public void moveVehicle(Race race)
    {
        //Use the AI system to get information about the surrowndings
    }
    
    @Override
    public String getControllerLabel()
    {
        return "CPU";
    }
}
