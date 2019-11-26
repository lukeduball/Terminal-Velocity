/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Luke
 */
public class StringRenderer 
{
    //Standard height and width of the image font was created in order to scale the font a new window size
    private static final float STANDARD_WIDTH = 1920.0f;
    private static final float STANDARD_HEIGHT = 1080.0f;
    
    //Bauhaus 93 font with a font size of 63
    private static final Font BAUHAUS93 = new Font("Bauhaus 93", Font.PLAIN, 63);
    
    /**
     * Draws the passed string to the screen with the current scale
     * @param g graphics instance to draw to the screen
     * @param s string to draw
     * @param x psuedo space x coordinate
     * @param y psuedo space y coordinate
     * @param color color to draw the string in
     * @param gameInstance current game instance
     * @param scale scale of the string
     */
    public static void drawString(Graphics2D g, String s, Vec2 position, Color color, Screen screen, float scale)
    {
        Vec2 screenSpace = screen.worldToScreenCoordinate(position);
         drawScaledString(g, s, screenSpace, color, screen, scale);
    }
    
    /**
     * Draws the passed string to the screen with a scale of 1
     * @param g graphics instance to draw to the screen
     * @param s string to draw
     * @param x psuedo space x coordinate
     * @param y psuedo space y coordinate
     * @param color color to draw the string in
     * @param gameInstance current game instance
     */
    public static void drawString(Graphics2D g, String s, Vec2 position, Color color, Screen screen)
    {
        Vec2 screenSpace = screen.worldToScreenCoordinate(position);
        drawScaledString(g, s, screenSpace, color, screen, 1.0f);
    }
    
    /**
     * Draws the string on the screen centered on the x and y coordinates
     * @param g graphics instance to draw to the screen
     * @param s string to draw
     * @param x psuedo space x coordinate
     * @param y psuedo space y coordinate
     * @param color color to draw the string in
     * @param gameInstance current game instance
     */
    public static void drawCenteredString(Graphics2D g, String s, Vec2 position, Color color, Screen screen)
    {
        drawCenteredString(g, s, position, color, screen, 1.0f);
    }
    
        /**
     * Draws the string on the screen centered on the x and y coordinates
     * @param g graphics instance to draw to the screen
     * @param s string to draw
     * @param x psuedo space x coordinate
     * @param y psuedo space y coordinate
     * @param color color to draw the string in
     * @param gameInstance current game instance
     * @param scale text scale
     */
    public static void drawCenteredString(Graphics2D g, String s, Vec2 position, Color color, Screen screen, float scale)
    {
        FontMetrics metrics = g.getFontMetrics(getScaledFont(screen, scale));
        Vec2 screenSpace = screen.worldToScreenCoordinate(position);
        screenSpace.x -= metrics.stringWidth(s) / 2.0f;
        screenSpace.y -= (metrics.getHeight() / 2.0f) + metrics.getAscent();
        drawScaledString(g, s, screenSpace, color, screen, scale);
    }
    
    public static void drawRightAlignedString(Graphics2D g, String s, Vec2 position, Color color, Screen screen)
    {
        FontMetrics metrics = g.getFontMetrics(getScaledFont(screen, 1.0f));
        Vec2 screenSpace = screen.worldToScreenCoordinate(position);
        screenSpace.x -= metrics.stringWidth(s);
        drawScaledString(g, s, screenSpace, color, screen, 1.0f);
    }
    
    /**
     * Gets the scaled font based on the scale and window size
     * @param gameInstance current game instance
     * @param scale scale of the string
     * @return returns the scaled font to load to the graphics
     */
    private static Font getScaledFont(Screen screen, float scale)
    {
        AffineTransform at = new AffineTransform();
        float xScale = screen.getWidth() / STANDARD_WIDTH;
        float yScale = screen.getHeight() / STANDARD_HEIGHT;
        at.scale(xScale * scale, yScale * scale);
        return BAUHAUS93.deriveFont(at);
    }
    
    /**
     * Draws the scaled string to the real window space x and y coordinates
     * @param g graphics instance to draw to the screen with
     * @param s string to draw
     * @param x real window space x coordinate
     * @param y real window space y coordinate
     * @param color color to draw the string in
     * @param gameInstance current game instance
     * @param scale scale of the string to draw
     */
    private static void drawScaledString(Graphics2D g, String s, Vec2 position, Color color, Screen screen, float scale)
    {
        Font font = getScaledFont(screen, scale);
        //Sets the graphics to antialiase the font
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(color);
        g.drawString(s, position.x, position.y);
    }
}
