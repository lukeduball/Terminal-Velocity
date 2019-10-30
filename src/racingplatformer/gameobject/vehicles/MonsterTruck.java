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
import racingplatformer.renderengine.ResourceManager;

import java.awt.*;

/**
 *
 * @author Luke
 */
public class MonsterTruck extends Vehicle{
    private static float pixelFactor = 0.25f;
    private static Image mcBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_frame.png");
    private static Image mcWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_wheel.png");

    public MonsterTruck(Vec2 startingPos, World world){
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
        bd.position.set(0.0f, 0.0f);
        frame = world.createBody(bd);
        frame.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(89*pixelFactor, 48.5f*pixelFactor);
        frontWheel = world.createBody(bd);
        frontWheel.createFixture(fd);

        bd.position.set(-77*pixelFactor, 48.5f*pixelFactor);
        rearWheel = world.createBody(bd);
        rearWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 0.0f);

        jd.initialize(frame, frontWheel, frontWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 4f;
        jd.dampingRatio = 1f;
        frontWheelSpring = (WheelJoint) world.createJoint(jd);

        jd.initialize(frame, rearWheel, rearWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 4f;
        jd.dampingRatio = 1f;
        rearWheelSpring = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }
}
