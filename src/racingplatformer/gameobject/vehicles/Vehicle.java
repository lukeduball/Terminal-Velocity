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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.WheelJoint;
import racingplatformer.gameobject.GameObject;
import racingplatformer.race.Race;
import racingplatformer.renderengine.Screen;
import racingplatformer.renderengine.StringRenderer;
import racingplatformer.utilities.Timer;

/**
 *
 * @author Luke
 */
public class Vehicle extends GameObject
{    
    //Mass of the vehicle in Kg
    private float mass;
    
    //Speed of the vehicle
    protected float speed;
    
    //Acceleration of the vehicle
    private float acceleration;
    
    private Vec2 velocity;
    
    protected float wheelRotation;
    
    protected boolean isRacing;
    private String finishPosition;
    
    private int racerID;
    
    private float flippedVehicleTimer;
    
    protected Controller movementController;
    
    protected Body frame;
    protected Body rearWheel;
    protected boolean isRearWheelOnGround;
    protected Body frontWheel;
    protected boolean isFrontWheelOnGround;
    protected WheelJoint rearWheelSpring;
    protected WheelJoint frontWheelSpring;
    
    protected float halfWidth;
    
    private Timer idleTimer;
    private Vec2 lastPosition;
    private boolean active;
    
    public Vehicle(Race race, int rid)
    {
        super(race);
        this.racerID = rid;
        this.isRacing = true;
        this.lastPosition = new Vec2(0,0);
        this.idleTimer = new Timer(5);
        race.registerTimer(idleTimer);
        this.active = true;
    }
    
    @Override
    public void onUpdate(long delta) 
    {
        //Update the location of the vehicle
        
        //Depending on the controller it will move the vehicle based on those conditions
        if(this.movementController != null && isRacing)
        {
            this.movementController.moveVehicle();
        }
        else
        {
            this.frontWheelSpring.setMotorSpeed(0.0f);
            this.rearWheelSpring.setMotorSpeed(0.0f);
        }
        
        Vec2 deltaPosition = this.frame.getPosition().sub(this.lastPosition);
        if(deltaPosition.lengthSquared() < 0.00001)
        {
            this.idleTimer.startTimer();
        }
        else
        {
            this.idleTimer.pauseTimer();
            this.idleTimer.resetTimer();
        }
        
        if(this.idleTimer.hasTimerExpired())
        {
            this.active = false;
        }
        
        this.lastPosition = new Vec2(this.frame.getPosition());

    }

    @Override
    public void render(Graphics2D g, Screen screen) 
    {
        if(this.movementController != null)
        {
            this.drawPlayerLabel(g, screen);
        }
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
    
    protected void drawFrame(Graphics2D g, Image img, float width, float height, Screen screen)
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
        g.drawImage(img, at, race.getGameInstance());
    }
    
    protected void drawWheel(Graphics2D g, Image img, Body wheelBody, float width, Screen screen)
    {
        float factor = width / img.getWidth(null);
        float height = (float)img.getHeight(null) * factor;
        float scaleX = (width * screen.getScaleFactor()) / img.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / img.getHeight(null);
        
        Vec2 wheelPos = wheelBody.getPosition().sub(new Vec2(width / 2.0f, height / 2.0f));
        Vec2 screenPos = screen.worldToScreenCoordinate(wheelPos);
        
        Vec2 wheelCenterOffset = new Vec2(width, height).mul(screen.getScaleFactor() / 2.0f);
        
        AffineTransform at = new AffineTransform();
        at.translate(screenPos.x, screenPos.y);
        at.rotate(wheelBody.getAngle() * 0.1, wheelCenterOffset.x, wheelCenterOffset.y);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, race.getGameInstance());
    }
    
    private void drawPlayerLabel(Graphics2D g, Screen screen)
    {
        StringRenderer.drawCenteredString(g, this.movementController.getControllerLabel(), 
        this.frame.getPosition(),
        Color.black, screen, 0.5f);
    }
    
    public Body getFrame()
    {
        return this.frame;
    }
    
    public Body getFrontWheel()
    {
        return this.frontWheel;
    }
    
    public Body getRearWheel()
    {
        return this.rearWheel;
    }
    
    public WheelJoint getRearWheelSpring()
    {
        return this.rearWheelSpring;
    }
    
    public WheelJoint getFrontWheelSpring()
    {
        return this.frontWheelSpring;
    }
    
    public void setMovementController(Controller controller)
    {
        this.movementController = controller;
    }
    
    public float getSpeed()
    {
        return this.speed;
    }
    
    public Vec2 getFrontOfVehiclePosition()
    {
        return this.getPosition().add(new Vec2(this.halfWidth, 0.0f));
    }
    
    public void setRacing(boolean flag)
    {
        this.isRacing = flag;
    }
    
    public void setFinishPosition(String s)
    {
        this.finishPosition = s;
    }
    
    public String getCurrentPosition()
    {
        if(this.isRacing)
        {
            return race.getCurrentPosition(this);
        }
        return this.finishPosition;
    }
    
    public int getRacerID()
    {
        return this.racerID;
    }
    
    public void setRearWheelOnGround(boolean flag)
    {
        this.isRearWheelOnGround = flag;
    }
    
    public boolean getRearWheelOnGround()
    {
        return this.isRearWheelOnGround;
    }
    
    public void setFrontWheelOnGround(boolean flag)
    {
        this.isFrontWheelOnGround = flag;
    }
    
    public boolean getFrontWheelOnGround()
    {
        return this.isFrontWheelOnGround;
    }
    
    public boolean isActive()
    {
        return this.active;
    }
    
    public Race getRace()
    {
        return this.race;
    }
    
}
