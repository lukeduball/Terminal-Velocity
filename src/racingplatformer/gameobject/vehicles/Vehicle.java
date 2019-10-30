/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.WheelJoint;
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
import racingplatformer.race.Race;
import racingplatformer.renderengine.Screen;

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
    
    private Vec2 velocity;
    
    protected float wheelRotation;
    
    protected Body frame;
    protected Body rearWheel;
    protected Body frontWheel;
    protected WheelJoint rearWheelSpring;
    protected WheelJoint frontWheelSpring;
    
    
    @Override
    public void onUpdate(Race race) 
    {
        //Update the location of the vehicle
        
        //Depending on the controller it will move the vehicle based on those conditions
        //controller.moveVehicle();
    }

    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance) 
    {
        //Render the vehicle
    }
    
    @Override
    public Vec2 getPosition()
    {
        return this.frame.getPosition();
    }
    
    @Override
    public float getRotation()
    {
        return this.frame.getAngle();
    }
    
    protected void drawFrame(Graphics2D g, Image img, float width, float height, Screen screen, Game gameInstance)
    {
        float scaleX = (width * screen.getScaleFactor()) / img.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / img.getHeight(null);
        
        Vec2 centeredImagePos = this.getPosition().sub(new Vec2(width / 2.f, height / 2.f));
        Vec2 screenPos = screen.worldToScreenCoordinate(centeredImagePos);
        Vec2 rotationOffset = new Vec2(width, height).mul(screen.getScaleFactor() / 2.0f);
        
        AffineTransform at = new AffineTransform();
        at.translate(screenPos.x, screenPos.y);
        at.rotate(this.getRotation(), rotationOffset.x, rotationOffset.y);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
    protected void drawWheel(Graphics2D g, Image img, float xOff, float yOff, float width, float frameWidth, float frameHeight, Screen screen, Game gameInstance)
    {
        float factor = width / img.getWidth(null);
        float height = (float)img.getHeight(null) * factor;
        float scaleX = (width * screen.getScaleFactor()) / img.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / img.getHeight(null);
        
        Vec2 wheelPos = this.getPosition().add(new Vec2(xOff, yOff));
        Vec2 screenPos = screen.worldToScreenCoordinate(wheelPos);
        
        Vec2 frameCenterOffset = new Vec2(-xOff + frameWidth / 2.0f, 
                -yOff + frameHeight / 2.0f).mul(screen.getScaleFactor());
        
        Vec2 wheelCenterOffset = new Vec2(width, height).mul(screen.getScaleFactor() / 2.0f);
        
        AffineTransform at = new AffineTransform();
        at.translate(screenPos.x, screenPos.y);
        at.rotate(this.getRotation(), frameCenterOffset.x, frameCenterOffset.y);
        at.rotate(this.wheelRotation, wheelCenterOffset.x, wheelCenterOffset.y);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
}
