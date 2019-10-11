/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import racingplatformer.Game;
import racingplatformer.math.PerlinNoise;
import racingplatformer.math.Vector2f;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class TrackSegment 
{
    //This value is used to connect segments that exist in different chunks
    private final int segmentID;
    private List<Vector2f> pointList;
    
    public TrackSegment(int id, int chunkID)
    {
        this.segmentID = id;
        this.pointList = new ArrayList<>();
        this.generatePointList(chunkID);
    }
    
    private void generatePointList(int chunkID)
    {
        Random rand = new Random();
        PerlinNoise pNoise = new PerlinNoise(8932937492483242575L);
        for(int i = 0; i < 251; i++)
        {
            float height = pNoise.getNoise(i+chunkID*250, 100);
            Vector2f point = new Vector2f((chunkID * 250.f) + i,  -height + 150);
            pointList.add(point);
        }
    }
    
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        g.setColor(Color.green);
        g.setStroke(new BasicStroke(10));
        int[] xPoints = new int[pointList.size()+2];
        int[] yPoints = new int[pointList.size()+2];
        
        //Creates a point below the screen to draw a filled in track on the left boundary
        xPoints[0] = (int)((pointList.get(0).getX() - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[0] = (int)(screen.getHeight() + 10);
        
        for(int i = 0; i < pointList.size(); i++)
        { 
            xPoints[i+1] = (int)( (pointList.get(i).getX() - screen.getWorldRenderX()) * screen.getScaleFactor());
            yPoints[i+1] = (int)( (pointList.get(i).getY() - screen.getWorldRenderY()) * screen.getScaleFactor());
        }
        
        //Creates a point below the screen to draw a filled in track on the right boundary
        xPoints[pointList.size()+1] = (int)( (pointList.get(pointList.size()-1).getX() - screen.getWorldRenderX()) * screen.getScaleFactor());
        yPoints[pointList.size()+1] = (int)(screen.getHeight() + 10);
                
        g.fillPolygon(xPoints, yPoints, pointList.size()+2);
        g.setStroke(new BasicStroke());
    }
}
