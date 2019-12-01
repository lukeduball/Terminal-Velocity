/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import racingplatformer.renderengine.Screen;
import racingplatformer.renderengine.StringRenderer;

/**
 *
 * @author Luke
 */
public class TextSegment extends TrackSegment
{
    private final String text;
    private final Vec2 position;

    public TextSegment(World world, String s, Vec2 pos) 
    {
        super(world);
        this.text = s;
        this.position = pos;
    }

    @Override
    public void render(Graphics2D g, Screen screen) 
    {
        StringRenderer.drawString(g, text, position, Color.black, screen, 1.0f);
    }
    
}
