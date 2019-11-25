/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject;

import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
import racingplatformer.race.Race;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public abstract class GameObject 
{
    protected float rotation = 0.0f;
    protected Vec2 position;
    protected Race race;
    
    public GameObject(Race r)
    {
        this.race = r;
    }
    
    public float getRotation()
    {
        return rotation;
    }
    
    public Vec2 getPosition()
    {
        return position;
    }
    
    public abstract void onUpdate(long delta);
    public abstract void render(Graphics2D g, Screen screen);
}
