/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import org.jbox2d.common.Vec2;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;
import racingplatformer.renderengine.ResourceManager;

import java.awt.Image;

/**
 *
 * @author Luke
 */
public class RallyRacer extends Vehicle{
    private static Body rrBody, rrWheelF, rrWheelR;
    private WheelJoint rrSpringF, rrSpringR;
    private static float pixelFactor = 1/64f;
    private static Image rrBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_frame.png");
    private static Image rrWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_wheel.png");

    public RallyRacer(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(21,39);
        vertices[1] = new Vec2(3,57);
        vertices[2] = new Vec2(0,83);
        vertices[3] = new Vec2(72,86);
        vertices[4] = new Vec2(188,86);
        vertices[5] = new Vec2(260,68);
        vertices[6] = new Vec2(251,0);
        vertices[7] = new Vec2(129,0);

        for(int i = 0; i < vertices.length; i++){
            vertices[i].set(vertices[i].x*pixelFactor, vertices[i].y*pixelFactor);
        }
        PolygonShape chassis = new PolygonShape();
        chassis.set(vertices, vertices.length);

        CircleShape wheel = new CircleShape();
        wheel.m_radius = 22*pixelFactor;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(0.0f, 0.0f);
        rrBody = world.createBody(bd);
        rrBody.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(49*pixelFactor, 70*pixelFactor);
        rrWheelF = world.createBody(bd);
        rrWheelF.createFixture(fd);

        bd.position.set(214*pixelFactor, 70*pixelFactor);
        rrWheelR = world.createBody(bd);
        rrWheelR.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 0.0f);

        jd.initialize(rrBody, rrWheelF, rrWheelF.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        rrSpringF = (WheelJoint) world.createJoint(jd);

        jd.initialize(rrBody, rrWheelR, rrWheelR.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        rrSpringR = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }

        //TODO render
}
