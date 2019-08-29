/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject;

import java.awt.Graphics2D;
import racingplatformer.math.Vector2f;

/**
 *
 * @author Luke
 */
public abstract class GameObject 
{
    private float rotation = 0.0f;
    private Vector2f position;
    
    public GameObject()
    {
        
    }
    
    public float getRotation()
    {
        return rotation;
    }
    
    public Vector2f getPosition()
    {
        return position;
    }
    
    public abstract void onUpdate();
    public abstract void render(Graphics2D g);
}
