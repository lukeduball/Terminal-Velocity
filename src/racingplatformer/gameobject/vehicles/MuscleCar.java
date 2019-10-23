/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Graphics2D;
import java.awt.Image;
import org.jbox2d.common.Vec2;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

import racingplatformer.renderengine.ResourceManager;

/**
 *
 * @author Luke
 */
public class MuscleCar extends Vehicle{
    private static Image muscleCarImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_frame.png");
    private static Image muscleCarWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_wheel.png");
    private Vec2 velocity;
    
	public MuscleCar(float x, float y, Vec2 velocity) {
		super(x, y, 350, 10, velocity);
	}
    
}
