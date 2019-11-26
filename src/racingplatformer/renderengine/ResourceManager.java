/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.renderengine;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Luke
 */
public class ResourceManager 
{
    /**
     * Attempts to load an image and prints to the screen when the image can not be found
     * @param path the file path to the image to be drawn
     * @return the image if it is found, or an empty image if no image can be found at the path
     */
    public static Image loadImage(String path)
    {
        try 
        {
            Image img = ImageIO.read(new File(path));
            return img;
        } 
        catch (IOException ex) 
        {
            System.err.println("ERROR: Image not found at path: "+path);
        }
        return new ImageIcon("").getImage();
    }
}
