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
	
    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Need to rework rendering system so that the same aspect ratio is always maintaned in Screen Rendering
        
        this.wheelRotation += 0.010;
        this.rotation += 0.00;
        this.position = this.position.add(new Vec2(0.0f, 0.0f));
        
        float frameWidth = (270.0f/4.0f);
        float factor = frameWidth / muscleCarImg.getWidth(null);
        float frameHeight = (float)muscleCarWheelImg.getHeight(null) * factor;
        
        float wheelWidth = (45.0f/4.0f);
        
        float leftWheelXOffset = translateToGameSpace(42, frameWidth, muscleCarImg.getWidth(null));
        float wheelYOffset = translateToGameSpace(39, frameHeight, muscleCarImg.getHeight(null));
        float rightWheelXOffset = translateToGameSpace(207, frameWidth, muscleCarImg.getWidth(null));
        
        this.drawFrame(g, muscleCarImg, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, muscleCarWheelImg, leftWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, muscleCarWheelImg, rightWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);

    }
    
}
