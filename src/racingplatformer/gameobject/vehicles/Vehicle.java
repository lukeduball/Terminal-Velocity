/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Graphics2D;
import racingplatformer.gameobject.GameObject;
import racingplatformer.math.Vector2f;

/**
 *
 * @author Luke
 */
public class Vehicle extends GameObject
{
    //Controller which chooses how to decide how to move this vehicle
    Controller controller;
    
    //Mass of the vehicle in Kg
    private float mass;
    
    //Speed of the vehicle
    private float speed;
    
    //Acceleration of the vehicle
    private float acceleration;
    
    private Vector2f velocity;
    

    @Override
    public void onUpdate() 
    {
        //Update the location of the vehicle
        
        //Depending on the controller it will move the vehicle based on those conditions
        //controller.moveVehicle();
    }

    @Override
    public void render(Graphics2D g) 
    {
        //Render the vehicle
    }
    
}
