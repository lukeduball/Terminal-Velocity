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
public class MuscleCar extends Vehicle{
    private static float pixelFactor = 1/64f;
    private static Image mcBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_frame.png");
    private static Image mcWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_wheel.png");

    public MuscleCar(World world, float x, float y)
    {
        this.constructMuscleCar(new Vec2(x, y), world);
        this.position = new Vec2(x, y);
        this.speed = 9000.0f;
    }

    public void constructMuscleCar(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(-127,-1);
        vertices[1] = new Vec2(-3,-34);
        vertices[2] = new Vec2(76,-16);
        vertices[3] = new Vec2(100,-16);
        vertices[4] = new Vec2(127,22);
        vertices[5] = new Vec2(64,34);
        vertices[6] = new Vec2(-31,34);
        vertices[7] = new Vec2(-115,28);

        for(int i = 0; i < vertices.length; i++){
            vertices[i].set(vertices[i].x*pixelFactor, vertices[i].y*pixelFactor);
        }
        PolygonShape chassis = new PolygonShape();
        chassis.set(vertices, vertices.length);

        CircleShape wheel = new CircleShape();
        wheel.m_radius = 23*pixelFactor;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(startingPos);
        this.frame = world.createBody(bd);
        FixtureDef fdf=new FixtureDef();
        fdf.shape=chassis;
        fdf.filter.groupIndex=-2;
        fdf.density=2f;
        fdf.friction=(0.0f);
        fdf.restitution = 0;
        this.frame.createFixture(fdf);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 300f;
        fd.filter.groupIndex=-2;

        Vec2 frontWheelPos = bd.position.set(90*pixelFactor, 27*pixelFactor).add(startingPos);
        bd.position.set(frontWheelPos);
        this.frontWheel = world.createBody(bd);
        this.frontWheel.createFixture(fd);

        Vec2 rearWheelPos = bd.position.set(-69*pixelFactor, 27*pixelFactor).add(startingPos);
        bd.position.set(rearWheelPos);
        this.rearWheel = world.createBody(bd);
        this.rearWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 1.0f);

        jd.initialize(frame, frontWheel, frontWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 15.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 15f;
        jd.dampingRatio = 0.9f;
        frontWheelSpring = (WheelJoint) world.createJoint(jd);

        jd.initialize(frame, rearWheel, rearWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 15.0f;
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
        super.onUpdate(race);
    }
    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Need to rework rendering system so that the same aspect ratio is always maintained in Screen Rendering

        this.wheelRotation += 0.01;
        this.rotation += 0.00;
        this.position = this.position.add(new Vec2(0.0f, 0.0f));

        float frameWidth = (270.0f/64.0f);
        float factor = frameWidth / mcBodyImg.getWidth(null);
        float frameHeight = (float)mcBodyImg.getHeight(null) * factor;

        float wheelWidth = (45.0f/64.0f);

        float leftWheelXOffset = translateToGameSpace(42, frameWidth, mcBodyImg.getWidth(null));
        float wheelYOffset = translateToGameSpace(39, frameHeight, mcBodyImg.getHeight(null));
        float rightWheelXOffset = translateToGameSpace(207, frameWidth, mcBodyImg.getWidth(null));

        this.drawFrame(g, mcBodyImg, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, mcWheelImg, this.frontWheel, wheelWidth, screen, gameInstance);
        this.drawWheel(g, mcWheelImg, this.rearWheel, wheelWidth, screen, gameInstance);

    }

    //TODO move this into a static function in a helper class
    private float translateToGameSpace(float offset, float transformed, float original)
    {
        float result = offset * transformed / original;
        return result;
    }
}
