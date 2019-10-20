/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Luke
 */
public class Track 
{
    
    public Track()
    {
    }
    
    /***
     * Generates a random track and returns the object
     * @return randomly generated track
     */
    public static Track generateRandomTrack()
    {
        //long seed; Generate a random seed
        //generate each section of the track
        //return the track back to the calling class
        return null;
    }
    
    public static void generateTrack(World world, long seed, List<Chunk> chunkList)
    {
        Random rand = new Random();
        int totalChunks = rand.nextInt(75) + 200;
        for(int i = 0; i < totalChunks; i++)
        {
            Chunk chunk = new Chunk(i);
            chunk.addBoundary(new TrackSegment(1, i, world));
            chunkList.add(chunk);
        }
    }
}