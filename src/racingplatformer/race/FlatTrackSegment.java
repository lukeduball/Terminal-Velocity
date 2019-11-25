/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class FlatTrackSegment extends TrackSegment
{
    private Vec2[] points;
    
    public FlatTrackSegment(World world, Vec2 startPoint, Vec2 endPoint)
    {
        super(world);
        
        points = new Vec2[]{startPoint, endPoint};
        
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        ChainShape cShape = new ChainShape();
        cShape.createChain(points, 2);
        FixtureDef fd = new FixtureDef();
        fd.shape = cShape;
        fd.density = 0.5f;
        fd.friction = 5.0f;
        fd.restitution = 0.0f;
        fd.filter.groupIndex=2;
        physicsBody = world.createBody(bd);
        physicsBody.setUserData(new TrackUserData());
        physicsBody.createFixture(fd);
    }

    @Override
    public void render(Graphics2D g, Screen screen)
    {
        g.setColor(Color.green);
        g.setStroke(new BasicStroke(10));
        int[] xPoints = new int[points.length+2];
        int[] yPoints = new int[points.length+2];
        
        //Creates a point below the screen to draw a filled in track on the left boundary
        xPoints[0] = screen.getX() + (int)((points[0].x - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[0] = screen.getY() + (int)(screen.getHeight() + 10);
        
        for(int i = 0; i < points.length; i++)
        { 
            xPoints[i+1] = screen.getX() + (int)( (points[i].x - screen.getWorldRenderX()) * screen.getScaleFactor());
            yPoints[i+1] = screen.getY() + (int)( (points[i].y - screen.getWorldRenderY()) * screen.getScaleFactor());
        }
        
        //Creates a point below the screen to draw a filled in track on the right boundary
        xPoints[points.length+1] = screen.getX() + (int)( (points[points.length-1].x - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[points.length+1] = screen.getY() + (int)(screen.getHeight() + 10);
                
        g.fillPolygon(xPoints, yPoints, points.length+2);
        g.setStroke(new BasicStroke());
    }
    
    
}
