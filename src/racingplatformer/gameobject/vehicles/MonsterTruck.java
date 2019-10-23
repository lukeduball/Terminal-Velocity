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
public class MonsterTruck extends Vehicle{
    private static Image monsterTruckFrameImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_frame.png");
    private static Image monsterTruckWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_wheel.png");
    private Vec2 velocity;
    
	public MonsterTruck(float x, float y, float mass, Vec2 velocity) {
		super(x, y, mass, 525, velocity);
		// TODO Auto-generated constructor stub
	}
    
}
