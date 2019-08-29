/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

/**
 *
 * @author Luke
 */
public abstract class Controller 
{
    private Vehicle parentVehicle;
    
    public Controller(Vehicle vehicle)
    {
        this.parentVehicle = vehicle;
    }
    
    public abstract void moveVehicle();
}
