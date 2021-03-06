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
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;
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
    
    public Porche(Race race, float x, float y, int rid)
    {
        super(race, rid);
        this.constructPorche(race.getWorld(), new Vec2(x, y));
        this.position = new Vec2(x, y);
        this.speed = 250.0f;
        this.halfWidth = 134.5f * pixelFactor;
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
        fdf.density=0.5f;
        fdf.friction=(2.0f);
        fdf.restitution = 0;
        this.frame.createFixture(fdf);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 0.5f;
        fd.friction = 0.2f;
        fd.restitution = 0;
        fd.filter.groupIndex=-2;

        Vec2 rearWheelPos = new Vec2(-70f*pixelFactor, 22.5f*pixelFactor).add(startingPos); //22.5
        bd.position.set(rearWheelPos);
        this.rearWheel = world.createBody(bd);
        this.rearWheel.createFixture(fd);

        Vec2 frontWheelPos = new Vec2(95f*pixelFactor, 20.5f*pixelFactor).add(startingPos);
        bd.position.set(frontWheelPos);
        this.frontWheel = world.createBody(bd);
        this.frontWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 1f);

        jd.initialize(this.frame, this.rearWheel, this.rearWheel.getPosition(), axis);
        jd.motorSpeed = 0f;
        jd.maxMotorTorque = 10.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 20f;
        jd.dampingRatio = 0.9f;
        this.rearWheelSpring = (WheelJoint)world.createJoint(jd);

        jd.initialize(this.frame, this.frontWheel, this.frontWheel.getPosition(), axis);
        jd.motorSpeed = 0f;
        jd.maxMotorTorque = 10.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 20f;
        jd.dampingRatio = 0.9f;
        this.frontWheelSpring = (WheelJoint)world.createJoint(jd);

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
        float factor = frameWidth / porcheImg.getWidth(null);
        float frameHeight = (float)porcheImg.getHeight(null) * factor;
        
        float wheelWidth = (45.0f/64.0f);
        
        this.drawFrame(g, porcheImg, frameWidth, frameHeight, screen);
        this.drawWheel(g, porcheWheelImg, this.frontWheel, wheelWidth, screen);
        this.drawWheel(g, porcheWheelImg, this.rearWheel, wheelWidth, screen);

        super.render(g, screen);
    }
}
