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
public class MonsterTruck {
    private static Body mtBody, mtWheelF, mtWheelR;
    private WheelJoint mtSpringF, mtSpringR;
    private static float pixelFactor = 1/64f;
    private static Image mcBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_frame.png");
    private static Image mcWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/monster_truck_wheel.png");

    public MonsterTruck(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(260,27);
        vertices[1] = new Vec2(108,0);
        vertices[2] = new Vec2(48,27);
        vertices[3] = new Vec2(6,36);
        vertices[4] = new Vec2(6,68);
        vertices[5] = new Vec2(41,113);
        vertices[6] = new Vec2(207,113);
        vertices[7] = new Vec2(254,65);

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
        mtBody = world.createBody(bd);
        mtBody.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(40*pixelFactor, 105*pixelFactor);
        mtWheelF = world.createBody(bd);
        mtWheelF.createFixture(fd);

        bd.position.set(207*pixelFactor, 105*pixelFactor);
        mtWheelR = world.createBody(bd);
        mtWheelR.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 0.0f);

        jd.initialize(mtBody, mtWheelF, mtWheelF.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        mtSpringF = (WheelJoint) world.createJoint(jd);

        jd.initialize(mtBody, mtWheelR, mtWheelR.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        mtSpringR = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }
}
