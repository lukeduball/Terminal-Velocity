/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Graphics2D;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import racingplatformer.Game;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public abstract class TrackSegment 
{
    protected Body physicsBody;
    
    public TrackSegment(World world)
    {
        
    }
    
    public abstract void render(Graphics2D g, Screen screen, Game gameInstance);
}
