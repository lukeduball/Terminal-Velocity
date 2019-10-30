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
public class MuscleCar extends Vehicle{
    private static Body mcBody, mcWheelF, mcWheelR;
    private WheelJoint mcSpringF, mcSpringR;
    private static float pixelFactor = 1/64f;
    private static Image mcBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_frame.png");
    private static Image mcWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/muscle_car_wheel.png");

    public MuscleCar(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(254,33);
        vertices[1] = new Vec2(130,0);
        vertices[2] = new Vec2(51,18);
        vertices[3] = new Vec2(27,18);
        vertices[4] = new Vec2(0,56);
        vertices[5] = new Vec2(63,68);
        vertices[6] = new Vec2(158,68);
        vertices[7] = new Vec2(242,62);

        for(int i = 0; i < vertices.length; i++){
            vertices[i].set(vertices[i].x*pixelFactor, vertices[i].y*pixelFactor);
        }
        PolygonShape chassis = new PolygonShape();
        chassis.set(vertices, vertices.length);

        CircleShape wheel = new CircleShape();
        wheel.m_radius = 19*pixelFactor;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(0.0f, 0.0f);
        mcBody = world.createBody(bd);
        mcBody.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(37*pixelFactor, 61*pixelFactor);
        mcWheelF = world.createBody(bd);
        mcWheelF.createFixture(fd);

        bd.position.set(196*pixelFactor, 61*pixelFactor);
        mcWheelR = world.createBody(bd);
        mcWheelR.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 0.0f);

        jd.initialize(mcBody, mcWheelF, mcWheelF.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        mcSpringF = (WheelJoint) world.createJoint(jd);

        jd.initialize(mcBody, mcWheelR, mcWheelR.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        mcSpringR = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }
}
