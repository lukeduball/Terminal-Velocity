/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.math;

/**
 *
 * @author Luke
 */
public class Vector2d 
{
    private double x, y;
    
    public Vector2d(double x1, double y1)
    {
        x = x1;
        y = y1;
    }
    
    public Vector2d(Vector2i veci)
    {
        this((double)veci.getX(), (double)veci.getY());
    }
    
    public Vector2d(Vector2f vecf)
    {
        this((double)vecf.getX(), (double)vecf.getY());
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public Vector2d add(Vector2d vec)
    {
        return new Vector2d(this.x+vec.x, this.y+vec.y);
    }
    
    public Vector2d subt(Vector2d vec)
    {
        return new Vector2d(this.x-vec.x, this.y-vec.y);
    }
    
    public Vector2d multScalar(double d)
    {
        return new Vector2d(this.x*d, this.y*d);
    }
}

