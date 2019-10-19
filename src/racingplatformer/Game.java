/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import racingplatformer.race.Race;
import racingplatformer.renderengine.gui.Gui;
import racingplatformer.renderengine.gui.MainMenuGui;

/**
 *
 * @author Luke
 */
public class Game extends Canvas
{
    private final BufferStrategy strategy;
    
    private boolean gameRunning;
    
    //Pseudo values for the height and width, place all our things within these bounds and they are scaled up to the window size
    private static final float PSUEDO_WIDTH = 500;
    private static final float PSUEDO_HEIGHT = 280;
    
    //Factors that can convert the Psuedo width and height to the real screen value
    private static float widthFactor;
    private static float heightFactor;
    
    
    //The current active gui which is drawn on the screen
    private Gui activeGui;
    
    //The current active race
    private Race activeRace;
    
    //Booleans to tell if music is enabled
    private boolean isMusicActivated;
    //Booleans to tell if sound effects are enabled 
    private boolean areSoundEffectsActivated;
    
    private int[][] playerInputButtons;
    
    //Constants used to make setting player inputs easier to read
    public static final int FOWARD = 0;
    public static final int BACKWARD = 1;
    public static final int TILT_UP = 2;
    public static final int TILT_DOWN = 3;
    
    public Game()
    {
        this.gameRunning = true;
        
        activeGui = null;
        activeRace = null;
        
        isMusicActivated = true;
        areSoundEffectsActivated = true;
        playerInputButtons = new int[4][4];
        this.initDefualtKeyControls();
        
        //Creates the window that contains the canvas
        JFrame container = new JFrame("Racing Platformer");
        
        //Sets the window to the default screen size
        Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel panel = (JPanel)container.getContentPane();
        panel.setPreferredSize(fullScreen);
        panel.setLayout(null);
        
        //Sets the bounds of the canvas so that it fills the whole screen
        this.setBounds(0, 0, (int)fullScreen.getWidth(), (int)fullScreen.getHeight());
        panel.add(this);
        
        this.setIgnoreRepaint(true);
        
        container.pack();
        
        //Sets the window to be able to resize and that it is visible
        container.setResizable(true);
        container.setVisible(true);
        
        //Sets the exit button on the top right of the window so it closes
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Registers these classes so that it caputes keyboard input and mouse input
        this.addKeyListener(new KeyHandler(this));
        this.addMouseListener(new MouseHandler(this));
        
        //Sets the window so that it is the current window to caputre input
        this.requestFocus();
        
        //Sets the canvas so that there things are drawn to an offscreen buffer
        //This make sure when things are drawn when moving there are no visual artifacts
        this.createBufferStrategy(2);
        this.strategy = this.getBufferStrategy();
    } 
    
    private void gameLoop()
    {
        long lastLoopTime = System.currentTimeMillis();
        long secondCounter = 0l;
        int frames = 0;
        double theta = 0.0f;
        double scale = 1.0f;
        
        MainMenuGui mainMenu = new MainMenuGui(this);
        //this.activeGui = mainMenu;
        this.setActiveRace(new Race(this));
        
        while(gameRunning)
        {
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
            
            //Sets the width factors by dividing the window width and height in pixels by our psuedo values
            Game.widthFactor = this.getParent().getWidth() / PSUEDO_WIDTH;
            Game.heightFactor = this.getParent().getHeight() / PSUEDO_HEIGHT;
            
            //Gets the mouse location on the screen relative to the jFrame window
            double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - this.getParent().getLocationOnScreen().x;
            double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - this.getParent().getLocationOnScreen().y; 
            
            //Update the races current logic
            if(this.getActiveRace() != null)
            {
                this.getActiveRace().onUpdate();
            }
            
            
            if(this.getActiveGui() != null)
            {
                //Dividing by widthFactor and heightFactor moves the coordinates from the screen size to our psuedo size
                this.getActiveGui().onUpdate(mouseX / Game.getWidthFactor(), mouseY / Game.getHeightFactor());
            }
            
            //Gets the graphics which is used to draw to the canvas
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            //Render the current races screens
            if(this.getActiveRace() != null)
            {
                this.getActiveRace().render(g);
            }
            
            //Calls the draw method of the active gui if it is not null
            if(this.getActiveGui() != null)
            {
                this.getActiveGui().draw(g);
            }

            g.dispose();
            //Shows the drawn image on the screen
            strategy.show();
            
            //Just a simple counter that counts how many frames there are per second
            secondCounter += delta;
            frames ++;
            if(secondCounter > 1000)
            {
                secondCounter = 0l;
                frames = 0;
            }
            
            try{ Thread.sleep(10); } catch(InterruptedException e) {}
        }
    }
    
    public static void main(String args[])
    {
        Game g = new Game();
        g.gameLoop();
    }
    
    private void initDefualtKeyControls()
    {
        this.playerInputButtons[0][FOWARD] = 87;
        this.playerInputButtons[0][BACKWARD] = 83;
        this.playerInputButtons[0][TILT_UP] = 65;
        this.playerInputButtons[0][TILT_DOWN] = 68;
        
        this.playerInputButtons[1][FOWARD] = 89;
        this.playerInputButtons[1][BACKWARD] = 72;
        this.playerInputButtons[1][TILT_UP] = 71;
        this.playerInputButtons[1][TILT_DOWN] = 74;
        
        this.playerInputButtons[2][FOWARD] = 80;
        this.playerInputButtons[2][BACKWARD] = 59;
        this.playerInputButtons[2][TILT_UP] = 76;
        this.playerInputButtons[2][TILT_DOWN] = 222;
        
        this.playerInputButtons[3][FOWARD] = 38;
        this.playerInputButtons[3][BACKWARD] = 40;
        this.playerInputButtons[3][TILT_UP] = 37;
        this.playerInputButtons[3][TILT_DOWN] = 39;
    }
    
    /**
     * Gives the factor that changes the psuedo width to real window width space
     * @return width factor to compute real window space
     */
    public static float getWidthFactor()
    {
        return widthFactor;
    }
    
    /**
     * Gives the height factor that changes the psuedo height to real window height space
     * @return height factor to compute real window space
     */
    public static float getHeightFactor()
    {
        return heightFactor;
    }
    
    /**
     * Gets the current active gui that is being drawn to the screen
     * @return the active gui or null if no gui is active
     */
    public Gui getActiveGui()
    {
        return this.activeGui;
    }
    
    /**
     * Sets the current active gui to the gui passed, active gui can be set to null
     * @param gui Gui to set the current active gui to
     */
    public void setActiveGui(Gui gui)
    {
        this.activeGui = gui;
    }
    
    public Race getActiveRace()
    {
        return this.activeRace;
    }
    
    public void setActiveRace(Race race)
    {
        this.activeRace = race;
    }
    
    public boolean getIsMusicActivated()
    {
        return this.isMusicActivated;
    }
    
    public void toggleMusic()
    {
        if(this.isMusicActivated)
        {
            this.isMusicActivated = false;
        }
        else
        {
            this.isMusicActivated = true;
        }
    }
    
    public boolean getAreSoundEffectsActivated()
    {
        return this.areSoundEffectsActivated;
    }
    
    public void toggleSoundEffects()
    {
        if(this.areSoundEffectsActivated)
        {
            this.areSoundEffectsActivated = false;
        }
        else
        {
            this.areSoundEffectsActivated = true;
        }
    }
    
    public int[] getPlayerControlKeys(int playerID)
    {
        return this.playerInputButtons[playerID];
    }
    
    public Graphics2D getGraphics()
    {
        return (Graphics2D)this.strategy.getDrawGraphics();
    }
}
