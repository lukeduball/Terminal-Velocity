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
import racingplatformer.race.Race;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

import java.awt.*;

/**
 *
 * @author Luke
 */
public class MonsterTruck extends Vehicle{
    private static float pixelFactor = 1/64f;
    private static Image mtBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_frame.png");
    private static Image mtWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_wheel.png");

    public MonsterTruck(Race race, float x, float y, int rid)
    {
        super(race, rid);
        this.constructMonsterTruck(new Vec2(x, y), race.getWorld());
        this.position = new Vec2(x, y);
        this.speed = 54.1f;
        this.halfWidth = 130.0f * pixelFactor;
    }

    public void constructMonsterTruck(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(-130,-29.5f);
        vertices[1] = new Vec2(22,-56.5f);
        vertices[2] = new Vec2(82,-29.5f);
        vertices[3] = new Vec2(124,-20.5f);
        vertices[4] = new Vec2(124,11.5f);
        vertices[5] = new Vec2(89,56.5f);
        vertices[6] = new Vec2(-77,56.5f);
        vertices[7] = new Vec2(-124,8.5f);

        for(int i = 0; i < vertices.length; i++){
            vertices[i].set(vertices[i].x*pixelFactor, vertices[i].y*pixelFactor);
        }
        PolygonShape chassis = new PolygonShape();
        chassis.set(vertices, vertices.length);

        CircleShape wheel = new CircleShape();
        wheel.m_radius = 44*pixelFactor;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(startingPos);
        this.frame = world.createBody(bd);
        FixtureDef fdf=new FixtureDef();
        fdf.shape=chassis;
        fdf.filter.groupIndex=-2;
        fdf.density=3f;
        fdf.friction=(2.0f);
        fdf.restitution = 0;
        this.frame.createFixture(fdf);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 3.0f;
        fd.friction = 5f;
        fd.filter.groupIndex=-2;

        Vec2 frontWheelPos = bd.position.set(89*pixelFactor, 48.5f*pixelFactor).add(startingPos);
        bd.position.set(frontWheelPos);
        this.frontWheel = world.createBody(bd);
        this.frontWheel.createFixture(fd);

        Vec2 rearWheelPos = bd.position.set(-77*pixelFactor, 48.5f*pixelFactor).add(startingPos);
        bd.position.set(rearWheelPos);
        this.rearWheel = world.createBody(bd);
        this.rearWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 1.0f);

        jd.initialize(frame, frontWheel, frontWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 80.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 8f;
        jd.dampingRatio = 0.6f;
        frontWheelSpring = (WheelJoint) world.createJoint(jd);

        jd.initialize(frame, rearWheel, rearWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 80.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 8f;
        jd.dampingRatio = 0.6f;
        rearWheelSpring = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }

    @Override
    public void onUpdate(long delta)
    {
        super.onUpdate(delta);
    }
    @Override
    public void render(Graphics2D g, Screen screen)
    {
        float frameWidth = (270.0f/64.0f);
        float factor = frameWidth / mtBodyImg.getWidth(null);
        float frameHeight = (float)mtBodyImg.getHeight(null) * factor;

        float wheelWidth = (84.0f/64.0f);

        this.drawFrame(g, mtBodyImg, frameWidth, frameHeight, screen);
        this.drawWheel(g, mtWheelImg, this.frontWheel, wheelWidth, screen);
        this.drawWheel(g, mtWheelImg, this.rearWheel, wheelWidth, screen);

        super.render(g, screen);
    }
}
