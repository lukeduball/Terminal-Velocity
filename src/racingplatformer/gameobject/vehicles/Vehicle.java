/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import racingplatformer.Game;
import racingplatformer.gameobject.GameObject;
import racingplatformer.math.Vector2f;
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
    
    private Vector2f velocity;
    
    protected float wheelRotation;
    
    
    @Override
    public void onUpdate() 
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
    
    protected void drawFrame(Graphics2D g, Image img, float width, float height, Screen screen, Game gameInstance)
    {
        float scaleX = (width * screen.getScaleFactor()) / img.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / img.getHeight(null);
        
        AffineTransform at = new AffineTransform();
        at.translate(screen.getX() + (this.getPosition().getX() - screen.getWorldRenderX()) * screen.getScaleFactor(), screen.getY() + (this.getPosition().getY() - screen.getWorldRenderY()) * screen.getScaleFactor());
        at.rotate(this.getRotation(), width * screen.getScaleFactor() / 2.0f, height * screen.getScaleFactor() / 2.0f);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
    protected void drawWheel(Graphics2D g, Image img, float xOff, float yOff, float width, float frameWidth, float frameHeight, Screen screen, Game gameInstance)
    {
        float factor = width / img.getWidth(null);
        float height = (float)img.getHeight(null) * factor;
        float scaleX = (width * screen.getScaleFactor()) / img.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / img.getHeight(null);
        
        float xPos = screen.getX() + (this.getPosition().getX() - screen.getWorldRenderX() + xOff) * screen.getScaleFactor();
        float yPos = screen.getY() + (this.getPosition().getY() - screen.getWorldRenderY() + yOff) * screen.getScaleFactor();
        
        float frameCenterX = (-xOff  + frameWidth / 2.0f) * screen.getScaleFactor();
        float frameCenterY = (-yOff + frameHeight / 2.0f) * screen.getScaleFactor();
        
        float wheelCenterX = width / 2.0f * screen.getScaleFactor();
        float wheelCenterY = height / 2.0f * screen.getScaleFactor();
        
        AffineTransform at = new AffineTransform();
        at.translate(xPos, yPos);
        at.rotate(this.getRotation(), frameCenterX, frameCenterY);
        at.rotate(this.wheelRotation, wheelCenterX, wheelCenterY);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
}
