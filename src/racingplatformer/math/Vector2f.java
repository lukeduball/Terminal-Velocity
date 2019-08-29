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
public class Vector2f 
{
    private float x, y;
    
    public Vector2f(float x1, float y1)
    {
        x = x1;
        y = y1;
    }
    
    public Vector2f(Vector2i veci)
    {
        this((float)veci.getX(), (float)veci.getY());
    }
    
    public Vector2f(Vector2d vecd)
    {
        this((float)vecd.getX(), (float)vecd.getY());
    }
    
    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public Vector2f add(Vector2f vec)
    {
        return new Vector2f(this.x+vec.x, this.y+vec.y);
    }
    
    public Vector2f subt(Vector2f vec)
    {
        return new Vector2f(this.x-vec.x, this.y-vec.y);
    }
    
    public Vector2f multScalar(float f)
    {
        return new Vector2f(this.x*f, this.y*f);
    }
}

