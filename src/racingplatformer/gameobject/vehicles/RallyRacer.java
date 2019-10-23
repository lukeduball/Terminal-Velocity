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
public class RallyRacer extends Vehicle{
	
    private static Image rallyracerImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_frame.png");
    private static Image porcheWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/raller_racer_wheel.png");
    private Vec2 velocity;
    
	public RallyRacer(float x, float y, Vec2 velocity) {
	    	super(x, y, 300, y, velocity);
	        this.position = new Vec2(x, y);
	    }
    
}
