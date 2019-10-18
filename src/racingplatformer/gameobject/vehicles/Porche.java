/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import java.awt.Graphics2D;
import java.awt.Image;
import org.jbox2d.common.Vec2;
import racingplatformer.Game;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class Porche extends Vehicle
{
    private static Image porcheImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_frame.png");
    private static Image porcheWheelImg = ResourceManager.loadImage("src/resources/images/vehicles/porche_wheel.png");
    
    public Porche(float x, float y)
    {
        this.position = new Vec2(x, y);
    }
    
    @Override
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        //Need to rework rendering system so that the same aspect ratio is always maintaned in Screen Rendering
        
        this.wheelRotation += 0.01;
        //this.rotation += 0.01;
        this.position = this.position.add(new Vec2(1.0f, 0.0f));
        
        float frameWidth = (270.0f/4.0f);
        float factor = frameWidth / porcheImg.getWidth(null);
        float frameHeight = (float)porcheImg.getHeight(null) * factor;
        
        float wheelWidth = (45.0f/4.0f);
        
        float leftWheelXOffset = translateToGameSpace(42, frameWidth, porcheImg.getWidth(null));
        float wheelYOffset = translateToGameSpace(39, frameHeight, porcheImg.getHeight(null));
        float rightWheelXOffset = translateToGameSpace(207, frameWidth, porcheImg.getWidth(null));
        
        this.drawFrame(g, porcheImg, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, porcheWheelImg, leftWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);
        this.drawWheel(g, porcheWheelImg, rightWheelXOffset, wheelYOffset, wheelWidth, frameWidth, frameHeight, screen, gameInstance);

    }
    
    //TODO move this into a static function in a helper classs
    private float translateToGameSpace(float offset, float transformed, float original)
    {
        float result = offset * transformed / original;
        return result;
    }
}
