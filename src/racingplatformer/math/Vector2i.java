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
public class Vector2i 
{
    private int x, y;
    
    public Vector2i(int x1, int y1)
    {
        x = x1;
        y = y1;
    }
    
    public Vector2i(Vector2f vecf)
    {
        this((int)vecf.getX(), (int)vecf.getY());
    }
    
    public Vector2i(Vector2d vecd)
    {
        this((int)vecd.getX(), (int)vecd.getY());
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public Vector2i add(Vector2i vec)
    {
        return new Vector2i(this.x+vec.x, this.y+vec.y);
    }
    
    public Vector2i subt(Vector2i vec)
    {
        return new Vector2i(this.x-vec.x, this.y-vec.y);
    }
    
    public Vector2i multScalar(int i)
    {
        return new Vector2i(this.x*i, this.y*i);
    }
}
