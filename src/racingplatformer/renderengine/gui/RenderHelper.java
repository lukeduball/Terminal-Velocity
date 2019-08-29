/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import racingplatformer.Game;

/**
 *
 * @author Luke
 */
public class RenderHelper 
{
    //Standard height and width of the image font was created in order to scale the font a new window size
    private static final float STANDARD_WIDTH = 1920.0f;
    private static final float STANDARD_HEIGHT = 1080.0f;
    
    //Bauhaus 93 font with a font size of 63
    private static final Font BAUHAUS93 = new Font("Bauhaus 93", Font.PLAIN, 63);
    
    public static void clearScreen(Graphics2D g, Game gameInstance)
    {
        g.clearRect(0, 0, gameInstance.getWidth(), gameInstance.getHeight());
    }
    
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
    public static void drawString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance, float scale)
    {
         drawScaledString(g, s, x * Game.getWidthFactor(), y * Game.getHeightFactor(), color, gameInstance, scale);
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
    public static void drawString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance)
    {   
        drawScaledString(g, s, x * Game.getWidthFactor(), y * Game.getHeightFactor(), color, gameInstance, 1.0f);
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
    public static void drawCenteredString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance)
    {
        FontMetrics metrics = g.getFontMetrics(getScaledFont(gameInstance, 1.0f));
        float xPos = x * Game.getWidthFactor() - (metrics.stringWidth(s) / 2.0f);
        float yPos = y * Game.getHeightFactor() - (metrics.getHeight() / 2.0f) + metrics.getAscent();
        drawScaledString(g, s, xPos, yPos, color, gameInstance, 1.0f);
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
    public static void drawCenteredString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance, float scale)
    {
        FontMetrics metrics = g.getFontMetrics(getScaledFont(gameInstance, scale));
        float xPos = x * Game.getWidthFactor() - (metrics.stringWidth(s) / 2.0f);
        float yPos = y * Game.getHeightFactor() - (metrics.getHeight() / 2.0f) + metrics.getAscent();
        drawScaledString(g, s, xPos, yPos, color, gameInstance, scale);
    }
    
    public static void drawRightAlignedString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance)
    {
        FontMetrics metrics = g.getFontMetrics(getScaledFont(gameInstance, 1.0f));
        float xPos = x * Game.getWidthFactor() - (metrics.stringWidth(s));
        float yPos = y * Game.getHeightFactor();
        drawScaledString(g, s, xPos, yPos, color, gameInstance, 1.0f);
    }
    
    /**
     * Gets the scaled font based on the scale and window size
     * @param gameInstance current game instance
     * @param scale scale of the string
     * @return returns the scaled font to load to the graphics
     */
    private static Font getScaledFont(Game gameInstance, float scale)
    {
        AffineTransform at = new AffineTransform();
        float xScale = gameInstance.getParent().getWidth() / STANDARD_WIDTH;
        float yScale = gameInstance.getParent().getHeight() / STANDARD_HEIGHT;
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
    private static void drawScaledString(Graphics2D g, String s, float x, float y, Color color, Game gameInstance, float scale)
    {
        Font font = getScaledFont(gameInstance, scale);
        //Sets the graphics to antialiase the font
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(color);
        g.drawString(s, x, y);
    }
    
    /**
     * Draw an image to the screen that is scaled to the current window
     * @param g graphics instance used to draw to the screen
     * @param img image to draw
     * @param x psuedo space x coordinate
     * @param y psuedo space y coordinate
     * @param width psuedo space width
     * @param height psuedo space height
     * @param gameInstance current game instance
     */
    public static void drawScaledImage(Graphics2D g, Image img, float x, float y, float width, float height, Game gameInstance)
    {
        AffineTransform at = new AffineTransform();
        //Translates the image into real space coordinates
        at.translate(x * Game.getWidthFactor(), y * Game.getHeightFactor());
        //Sets the scale based on the width and height and changes them to real space coordinates
        float scaleX = (width * Game.getWidthFactor()) / img.getWidth(null);
        float scaleY = (height * Game.getHeightFactor()) / img.getHeight(null);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
    /**
     * Draws the background image scaled to the full width and height of the screen
     * @param g graphics instance used to draw the screen
     * @param img image to draw
     * @param gameInstance current game instance
     */
    public static void drawScaledBackgroundImage(Graphics2D g, Image img, Game gameInstance)
    {
        //Calculates the scale to scale the image to the width and height of the full window
        float scaleX = gameInstance.getParent().getWidth() / (float)img.getWidth(null);
        float scaleY = gameInstance.getParent().getHeight() / (float)img.getHeight(null);
        AffineTransform at = new AffineTransform();
        //Translates the image to the top left hand corner
        at.translate(0.0f, 0.0f);
        at.scale(scaleX, scaleY);
        g.drawImage(img, at, gameInstance);
    }
    
    public static void drawScaledBackgroundRect(Graphics2D g, Color color, Game gameInstance)
    {
        int width = gameInstance.getParent().getWidth();
        int height = gameInstance.getParent().getHeight();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
    }
    
    public static void drawScaledRect(Graphics2D g, float x, float y, float width, float height, Color color, Game gameInstance)
    {
        int sX = (int)(x * Game.getWidthFactor());
        int sY = (int)(y * Game.getHeightFactor());
        int sWidth = (int)(width * Game.getWidthFactor());
        int sHeight = (int)(height * Game.getHeightFactor());
        g.setColor(color);
        g.fillRect(sX, sY, sWidth, sHeight);
    }
    
    public static void drawVehicle(Graphics2D g, Image vehicleImg, float x, float y, float refWidth, Game gameInstance)
    {
        float factor = refWidth / vehicleImg.getWidth(null);
        float height = (float)vehicleImg.getHeight(null) * factor;
        drawScaledImage(g, vehicleImg, x, y, refWidth, height, gameInstance);
    }
}
