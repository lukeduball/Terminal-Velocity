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
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;
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
    private static Body pBody, pWheelF, pWheelR;
    private WheelJoint pSpringF, pSpringR;
    private static float pixelFactor = 1/64f;
    private static Image porcheImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_frame.png");
    private static Image porcheWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_wheel.png");
    
    public Porche(World world, float x, float y)
    {
        this.constructPorche(world, new Vec2(x, y));
        this.position = new Vec2(x, y);
    }
    public Porche(Vec2 startingPos, World world){
        Vec2[] vertices = new Vec2[8];
        vertices[0] = new Vec2(269,0);
        vertices[1] = new Vec2(126,0);
        vertices[2] = new Vec2(6,33);
        vertices[3] = new Vec2(0,68);
        vertices[4] = new Vec2(69,77);
        vertices[5] = new Vec2(179,77);
        vertices[6] = new Vec2(260,65);
        vertices[7] = new Vec2(263,42);

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
        pBody = world.createBody(bd);
        pBody.createFixture(chassis, 1.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = wheel;
        fd.density = 1.0f;
        fd.friction = 0.9f;

        bd.position.set(40*pixelFactor, 61*pixelFactor);
        pWheelF = world.createBody(bd);
        pWheelF.createFixture(fd);

        bd.position.set(205*pixelFactor, 61*pixelFactor);
        pWheelR = world.createBody(bd);
        pWheelR.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 0.0f);

        jd.initialize(pBody, pWheelF, pWheelF.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        pSpringF = (WheelJoint) world.createJoint(jd);

        jd.initialize(pBody, pWheelR, pWheelR.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 20.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 0f;
        jd.dampingRatio = 0f;
        pSpringR = (WheelJoint) world.createJoint(jd);

        //TODO set position of vehicle to startingPos
        //TODO flip vehicle along y-axis
    }

    @Override
    public void onUpdate(Race race)
    {
        if(race.isMappedKeyDown(1, Game.FORWARD))
        {
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(100000.0f);
            this.frontWheelSpring.setMotorSpeed(100000.0f);
        }
        else if(race.isMappedKeyDown(1, Game.BACKWARD))
        {
            this.rearWheelSpring.enableMotor(true);
            this.rearWheelSpring.setMotorSpeed(-100000.0f);
            this.frontWheelSpring.setMotorSpeed(-100000.0f);
        }
        else
        {
            this.rearWheelSpring.enableMotor(false);
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
        //this.drawWheel(g, porcheWheelImg, leftWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);
        //this.drawWheel(g, porcheWheelImg, rightWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);

    }
    
    //TODO move this into a static function in a helper class
    private float translateToGameSpace(float offset, float transformed, float original)
    {
        float result = offset * transformed / original;
        return result;
    }

    private void constructPorche(World world, Vec2 position)
    {
        PolygonShape chassis = new PolygonShape();
        Vec2 vertices[] = new Vec2[8];
        vertices[0] = new Vec2(-15f, 5f);
        vertices[1] = new Vec2(15f, 5f);
        vertices[2] = new Vec2(15f, 0.0f);
        vertices[3] = new Vec2(0.0f, -9f);
        vertices[4] = new Vec2(-11.5f, -9f);
        vertices[5] = new Vec2(-15f, -2f);
        chassis.set(vertices, 6);

        CircleShape circle = new CircleShape();
        circle.m_radius = 4f;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(position);
        frame = world.createBody(bd);
        frame.createFixture(chassis, 5.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = circle;
        fd.density = 1.0f;
        fd.friction = 1.0f;

        Vec2 rearWheelPos = new Vec2(-10f, 4.5f).add(position);
        bd.position.set(rearWheelPos);
        rearWheel = world.createBody(bd);
        rearWheel.createFixture(fd);

        Vec2 frontWheelPos = new Vec2(10f, 4.5f).add(position);
        bd.position.set(frontWheelPos);
        frontWheel = world.createBody(bd);
        frontWheel.createFixture(fd);

        WheelJointDef jd = new WheelJointDef();
        Vec2 axis = new Vec2(0.0f, 1.0f);

        jd.initialize(frame, rearWheel, rearWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 50000.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 4.0f;
        jd.dampingRatio = 1.0f;
        this.rearWheelSpring = (WheelJoint)world.createJoint(jd);

        jd.initialize(frame, frontWheel, frontWheel.getPosition(), axis);
        jd.motorSpeed = 0.0f;
        jd.maxMotorTorque = 50000.0f;
        jd.enableMotor = true;
        jd.frequencyHz = 4.0f;
        jd.dampingRatio = 1.0f;
        this.frontWheelSpring = (WheelJoint)world.createJoint(jd);
    }
}
