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
    private static float pixelFactor = 0.25f;
    private static Image rrBodyImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_frame.png");
    private static Image rrWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/rally_racer_wheel.png");

    /*public void constructRallyRacer(Vec2 startingPos, World world){
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
        bd.position.set(0.0f, 0.0f);
        frame = world.createBody(bd);
        frame.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(81*pixelFactor, 27*pixelFactor);
        frontWheel = world.createBody(bd);
        frontWheel.createFixture(fd);

        bd.position.set(-84*pixelFactor, 27*pixelFactor);
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
*/
        //TODO render
}
