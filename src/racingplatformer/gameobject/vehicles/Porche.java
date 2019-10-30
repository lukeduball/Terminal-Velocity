/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Graphics2D;
import java.awt.Image;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;
import racingplatformer.Game;
import racingplatformer.race.Race;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Porche extends Vehicle
{
    private static float pixelFactor = 1/64f;
    private static Image porcheImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_frame.png");
    private static Image porcheWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_wheel.png");
    
    public Porche(World world, float x, float y)
    {
        this.constructPorche(world, new Vec2(x, y));
        this.position = new Vec2(x, y);
    }

    public void constructPorche(World world, Vec2 startingPos)
    {
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(-134.5f, -38.5f);
        vertices[1] = new Vec2(8.5f, -38.5f);
        vertices[2] = new Vec2(128.5f, -5.5f);
        vertices[3] = new Vec2(134.5f, 29.5f);
        vertices[4] = new Vec2(65.5f, 38.5f);
        vertices[5] = new Vec2(-44.5f, 38.5f);
        vertices[6] = new Vec2(-125.5f, 26.5f);
        vertices[7] = new Vec2(-128.5f, 3.5f);

        for(int i = 0; i < vertices.length; i++){
            vertices[i].set(vertices[i].x*pixelFactor, vertices[i].y*pixelFactor);
        }
        PolygonShape chassis = new PolygonShape();
        chassis.set(vertices, vertices.length);

        CircleShape wheel = new CircleShape();
        wheel.m_radius = 22*pixelFactor;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(startingPos);
        this.frame = world.createBody(bd);
        FixtureDef fdf=new FixtureDef();
        fdf.shape=chassis;
        fdf.filter.groupIndex=-2;
        fdf.density=1f;
        fdf.friction=(0.0f);
        fdf.restitution = 0;
        this.frame.createFixture(fdf);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 0.1f;
        fd.friction = 2.5f;
        fd.restitution = 0;
        fd.filter.groupIndex=-2;

        Vec2 pWheelFPos = new Vec2(-70f*pixelFactor, 22.5f*pixelFactor).add(startingPos);
        bd.position.set(pWheelFPos);
        this.rearWheel = world.createBody(bd);
        this.rearWheel.createFixture(fd);

        Vec2 pWheelRPos = new Vec2(95f*pixelFactor, 22.5f*pixelFactor).add(startingPos);
        bd.position.set(pWheelRPos);
        this.frontWheel = world.createBody(bd);
        this.frontWheel.createFixture(fd);

        RevoluteJointDef jd = new RevoluteJointDef();
        Vec2 axis = new Vec2(0.0f, 1f);

        jd.initialize(this.frame, this.rearWheel, this.rearWheel.getPosition());
        jd.motorSpeed = 10.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        //jd.frequencyHz = 10f;
        //jd.dampingRatio = 1f;
        this.rearWheelSpring = (RevoluteJoint)world.createJoint(jd);

        jd.initialize(this.frame, this.frontWheel, this.frontWheel.getPosition());
        jd.motorSpeed = 10.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        //jd.frequencyHz = 10f;
        //jd.dampingRatio = 1f;
        this.frontWheelSpring = (RevoluteJoint)world.createJoint(jd);

        //TODO flip vehicle along y-axis
    }

    @Override
    public void onUpdate(Race race)
    {
        if(race.isMappedKeyDown(1, Game.FORWARD))
        {
            this.frontWheelSpring.enableMotor(true);
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(100000.0f);
            this.frontWheelSpring.setMotorSpeed(100000.0f);
        }
        else if(race.isMappedKeyDown(1, Game.BACKWARD))
        {
            this.frontWheelSpring.enableMotor(true);
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(-100000.0f);
            this.frontWheelSpring.setMotorSpeed(-100000.0f);
        }
        else
        {
            this.rearWheelSpring.enableMotor(false);
            this.frontWheelSpring.enableMotor(false);
        }

        if(race.isMappedKeyDown(1, Game.TILT_UP))
        {
            this.frame.applyAngularImpulse(-15000.0f);
        }

        if(race.isMappedKeyDown(1, Game.TILT_DOWN))
        {
            this.frame.applyAngularImpulse(15000.0f);
        }
    }

    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Need to rework rendering system so that the same aspect ratio is always maintained in Screen Rendering
        
        this.wheelRotation += 0.01;
        this.rotation += 0.00;
        this.position = this.position.add(new Vec2(0.0f, 0.0f));
        
        float frameWidth = (270.0f/4.0f);
        float factor = frameWidth / porcheImg.getWidth(null);
        float frameHeight = (float)porcheImg.getHeight(null) * factor;
        
        float wheelWidth = (45.0f/4.0f);
        
        float leftWheelXOffset = translateToGameSpace(42, frameWidth, porcheImg.getWidth(null));
        float wheelYOffset = translateToGameSpace(39, frameHeight, porcheImg.getHeight(null));
        float rightWheelXOffset = translateToGameSpace(207, frameWidth, porcheImg.getWidth(null));
        
        this.drawFrame(g, porcheImg, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, porcheWheelImg, this.frontWheel, wheelWidth, screen, gameInstance);
        this.drawWheel(g, porcheWheelImg, this.rearWheel, wheelWidth, screen, gameInstance);

    }
    
    //TODO move this into a static function in a helper class
    private float translateToGameSpace(float offset, float transformed, float original)
    {
        float result = offset * transformed / original;
        return result;
    }
}
