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
        for(int i = 0; i < 50; i++)
        {
            Vector2f point = new Vector2f((chunkID * 250.f) + i*5, rand.nextFloat() * 10 + 150);
            pointList.add(point);
        }
    }
    
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        g.setColor(Color.green);
        int[] xPoints = new int[pointList.size()];
        int[] yPoints = new int[pointList.size()];
        for(int i = 0; i < pointList.size(); i++)
        {
            xPoints[i] = (int)( (pointList.get(i).getX() - screen.getWorldRenderX()) * screen.getScaleFactor());
            yPoints[i] = (int)( (pointList.get(i).getY() - screen.getWorldRenderY()) * screen.getScaleFactor());
        }
        g.drawPolyline(xPoints, yPoints, pointList.size());
    }
}
