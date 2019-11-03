/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import org.jbox2d.common.Vec2;
import racingplatformer.Game;
import racingplatformer.gameobject.vehicles.Vehicle;
import racingplatformer.renderengine.ResourceManager;
import racingplatformer.renderengine.Screen;

/**
 *
 * @author Luke
 */
public class FinishLine 
{
    private final Vec2 position;
    
    private static final Image finishLineImg = ResourceManager.loadImage("src/resources/images/objects/racingflag.png");
    
    public FinishLine(Vec2 pos)
    {
        this.position = pos;
    }
    
    public void render(Graphics2D g, Screen screen, Game gameInstance)
    {
        float width = (199.0f/64.0f);
        float factor = width / finishLineImg.getWidth(null);
        float height = (float)finishLineImg.getHeight(null) * factor;
        
        float scaleX = (width * screen.getScaleFactor()) / finishLineImg.getWidth(null);
        float scaleY = (height * screen.getScaleFactor()) / finishLineImg.getHeight(null);
        
        Vec2 imagePosition = this.position.sub(new Vec2(0.0f, height-0.5f));
        Vec2 screenPos = screen.worldToScreenCoordinate(imagePosition);
        
        AffineTransform at = new AffineTransform();
        at.translate(screenPos.x, screenPos.y);
        at.scale(scaleX, scaleY);
        g.drawImage(finishLineImg, at, gameInstance);
    }
    
    public boolean hasVehicleFinishedRace(Vehicle vehicle)
    {
        return vehicle.getPosition().x > this.position.x;
    }
    
}
