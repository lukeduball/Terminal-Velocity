/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import racingplatformer.math.PerlinNoise;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class CurvedTrackSegment extends TrackSegment
{
    private List<Vec2> pointList;
    
    public CurvedTrackSegment(int chunkID, PerlinNoise pNoise, World world)
    {
        super(world);
        this.pointList = new ArrayList<>();
        this.generatePointList(chunkID, pNoise);
        
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        ChainShape cShape = new ChainShape();
        cShape.createChain(this.pointList.toArray(new Vec2[this.pointList.size()]), this.pointList.size());
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
    
    private void generatePointList(int chunkID, PerlinNoise pNoise)
    {
        Random rand = new Random();
        for(int i = 0; i < Chunk.CHUNK_WIDTH + 1; i++)
        {
            float height = pNoise.getSmoothNoise(i+chunkID*Chunk.CHUNK_WIDTH, 100);
            Vec2 point = new Vec2((chunkID * Chunk.CHUNK_WIDTH) + i,  -height + 150);
            pointList.add(point);
        }
    }
    
    @Override
    public void render(Graphics2D g, Screen screen)
    {
        g.setColor(Color.green);
        g.setStroke(new BasicStroke(10));
        int[] xPoints = new int[pointList.size()+2];
        int[] yPoints = new int[pointList.size()+2];
        
        //Creates a point below the screen to draw a filled in track on the left boundary
        xPoints[0] = screen.getX() + (int)((pointList.get(0).x - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[0] = screen.getY() + (int)(screen.getHeight() + 10);
        
        for(int i = 0; i < pointList.size(); i++)
        { 
            xPoints[i+1] = screen.getX() + (int)( (pointList.get(i).x - screen.getWorldRenderX()) * screen.getScaleFactor());
            yPoints[i+1] = screen.getY() + (int)( (pointList.get(i).y - screen.getWorldRenderY()) * screen.getScaleFactor());
        }
        
        //Creates a point below the screen to draw a filled in track on the right boundary
        xPoints[pointList.size()+1] = screen.getX() + (int)( (pointList.get(pointList.size()-1).x - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[pointList.size()+1] = screen.getY() + (int)(screen.getHeight() + 10);
                
        g.fillPolygon(xPoints, yPoints, pointList.size()+2);       
        g.setStroke(new BasicStroke());
    }
}
