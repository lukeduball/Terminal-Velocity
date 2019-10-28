/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.OBBViewportTransform;

/**
 *
 * @author Luke
 */
public class DebugDrawTV extends DebugDraw
{
    private Graphics2D graphics;
    private Screen screen;

    public DebugDrawTV(Screen s, Graphics2D g)
    {
        super(new OBBViewportTransform());
        this.graphics = g;
        this.screen = s;
    }

    @Override
    public void drawPoint(Vec2 vec2, float f, Color3f clrf)
    {
        Vec2 screenCoords = this.screen.worldToScreenCoordinate(vec2);
        Color c = new Color(clrf.x, clrf.y, clrf.z);
        graphics.setColor(c);
        screenCoords.x -= f;
        screenCoords.y -= f;
        graphics.fillOval((int)screenCoords.x, (int)screenCoords.y, (int)f*2, (int)f*2);
        
    }

    @Override
    public void drawSolidPolygon(Vec2[] vec2s, int vertexCount, Color3f clrf) 
    {
        int[] xCoords = new int[vertexCount];
        int[] yCoords = new int[vertexCount];
        
        for(int i = 0; i < vertexCount; i++)
        {
            Vec2 screenCoords = this.screen.worldToScreenCoordinate(vec2s[i]);
            xCoords[i] = (int)screenCoords.x;
            yCoords[i] = (int)screenCoords.y;
        }
        Color c = new Color(clrf.x, clrf.y, clrf.z, 0.5f);
        graphics.setColor(c);
        graphics.fillPolygon(xCoords, yCoords, vertexCount);
        
        drawPolygon(vec2s, vertexCount, clrf);
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f clrf) 
    {
        Vec2 screenCoords = this.screen.worldToScreenCoordinate(center);
        screenCoords.x -= radius * this.screen.getScaleFactor();
        screenCoords.y -= radius * this.screen.getScaleFactor();
        Color c = new Color(clrf.x, clrf.y, clrf.z);
        graphics.setColor(c);
        graphics.drawOval((int)screenCoords.x, (int)screenCoords.y, (int)(radius*this.screen.getScaleFactor()*2), (int)(radius*this.screen.getScaleFactor()*2));
    }

    @Override
    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f clrf) 
    {
        Vec2 screenCoords = this.screen.worldToScreenCoordinate(center);
        screenCoords.x -= radius * this.screen.getScaleFactor();
        screenCoords.y -= radius * this.screen.getScaleFactor();
        Color c = new Color(clrf.x, clrf.y, clrf.z, 0.5f);
        
        graphics.setColor(c);
        graphics.fillOval((int)screenCoords.x, (int)screenCoords.y, (int)(radius*this.screen.getScaleFactor()*2), (int)(radius*this.screen.getScaleFactor()*2));
        
        if (axis != null) 
        {
            Vec2 saxis = new Vec2();
            saxis.set(axis).mulLocal(radius).addLocal(center);
            drawSegment(center, saxis, clrf);
        }
    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f clrf) 
    {
        Vec2 screenPoint1 = this.screen.worldToScreenCoordinate(p1);
        Vec2 screenPoint2 = this.screen.worldToScreenCoordinate(p2);
        
        Color c = new Color(clrf.x, clrf.y, clrf.z);
        this.graphics.setColor(c);
        this.graphics.drawLine((int)screenPoint1.x, (int)screenPoint1.y, (int)screenPoint2.x, (int)screenPoint2.y);
    }

    @Override
    public void drawTransform(Transform t) 
    {
        //Left blank because the screen is already drawn
    }

    @Override
    public void drawString(float x, float y, String string, Color3f clrf) 
    {
        Color c = new Color(clrf.x, clrf.y, clrf.z);
        this.graphics.setColor(c);
        this.graphics.drawString(string, x, y);
    }
    
}
