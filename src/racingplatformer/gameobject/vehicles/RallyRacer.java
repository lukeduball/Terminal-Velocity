/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;
import racingplatformer.Game;
import racingplatformer.race.Race;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

import java.awt.*;

/**
 *
 * @author Luke
 */
public class RallyRacer extends Vehicle{
    private static float pixelFactor = 1/64f;
    private static Image rrBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_frame.png");
    private static Image rrWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_wheel.png");

    public RallyRacer(World world, float x, float y)
    {
        this.constructRallyRacer(new Vec2(x, y), world);
        this.position = new Vec2(x, y);
    }

    public void constructRallyRacer(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(109,-4);
        vertices[1] = new Vec2(127,14);
        vertices[2] = new Vec2(130,40);
        vertices[3] = new Vec2(58,43);
        vertices[4] = new Vec2(-58,43);
        vertices[5] = new Vec2(-130,25);
        vertices[6] = new Vec2(-121,-43);
        vertices[7] = new Vec2(1,-43);

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
        fdf.friction=(2.0f);
        fdf.restitution = 0;
        this.frame.createFixture(fdf);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 200f;
        fd.filter.groupIndex=-2;

        Vec2 frontWheelPos = bd.position.set(83*pixelFactor, 26*pixelFactor).add(startingPos);
        bd.position.set(frontWheelPos);
        this.frontWheel = world.createBody(bd);
        this.frontWheel.createFixture(fd);

        Vec2 rearWheelPos = bd.position.set(-88*pixelFactor, 28*pixelFactor).add(startingPos);
        bd.position.set(rearWheelPos);
        this.rearWheel = world.createBody(bd);
        this.rearWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 1.0f);

        jd.initialize(frame, frontWheel, frontWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 10.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 15f;
        jd.dampingRatio = 0.9f;
        frontWheelSpring = (WheelJoint) world.createJoint(jd);

        jd.initialize(frame, rearWheel, rearWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 10.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 15f;
        jd.dampingRatio = 0.9f;
        rearWheelSpring = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }

    @Override
    public void onUpdate(Race race)
    {
        if(race.isMappedKeyDown(1, Game.FORWARD))
        {
            this.frontWheelSpring.enableMotor(true);
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(10000.0f);
            this.frontWheelSpring.setMotorSpeed(10000.0f);
        }
        else if(race.isMappedKeyDown(1, Game.BACKWARD))
        {
            this.frontWheelSpring.enableMotor(true);
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(-10000.0f);
            this.frontWheelSpring.setMotorSpeed(-10000.0f);
        }
        else
        {
            this.rearWheelSpring.enableMotor(false);
            this.frontWheelSpring.enableMotor(false);
        }

        if(race.isMappedKeyDown(1, Game.TILT_UP))
        {
            this.frame.applyAngularImpulse(-.5f);
        }

        if(race.isMappedKeyDown(1, Game.TILT_DOWN))
        {
            this.frame.applyAngularImpulse(.5f);
        }
    }
    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Need to rework rendering system so that the same aspect ratio is always maintained in Screen Rendering

        this.wheelRotation += 0.01;
        this.rotation += 0.00;
        this.position = this.position.add(new Vec2(0.0f, 0.0f));

        float frameWidth = (270.0f/64.0f);
        float factor = frameWidth / rrBodyImg.getWidth(null);
        float frameHeight = (float)rrBodyImg.getHeight(null) * factor;

        float wheelWidth = (45.0f/64.0f);

        float leftWheelXOffset = translateToGameSpace(42, frameWidth, rrBodyImg.getWidth(null));
        float wheelYOffset = translateToGameSpace(39, frameHeight, rrBodyImg.getHeight(null));
        float rightWheelXOffset = translateToGameSpace(207, frameWidth, rrBodyImg.getWidth(null));

        this.drawFrame(g, rrBodyImg, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, rrWheelImg, this.frontWheel, wheelWidth, screen, gameInstance);
        this.drawWheel(g, rrWheelImg, this.rearWheel, wheelWidth, screen, gameInstance);

    }

    //TODO move this into a static function in a helper class
    private float translateToGameSpace(float offset, float transformed, float original)
    {
        float result = offset * transformed / original;
        return result;
    }
}
