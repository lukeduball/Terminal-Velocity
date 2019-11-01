/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.race;

import java.util.List;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import racingplatformer.math.PerlinNoise;

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
        PerlinNoise pNoise = new PerlinNoise(8932937492483242570L);
        
        Chunk firstChunk = new Chunk(0);
        float yCoordinate = -pNoise.getSmoothNoise(Chunk.CHUNK_WIDTH, 100) + 150;
        Vec2 point1 = new Vec2(-5, yCoordinate);
        Vec2 point2 = new Vec2(Chunk.CHUNK_WIDTH, yCoordinate);
        firstChunk.addBoundary(new FlatTrackSegment(world, point1, point2));
        
        Vec2 wallPoint1 = new Vec2(0, yCoordinate);
        Vec2 wallPoint2 = new Vec2(0, yCoordinate - 20);
        firstChunk.addBoundary(new WallTrackSegment(world, wallPoint1, wallPoint2, 5));
        chunkList.add(firstChunk);
        
        int totalChunks = rand.nextInt(75) + 200;
        for(int i = 1; i < totalChunks; i++)
        {
            Chunk chunk = new Chunk(i);
            chunk.addBoundary(new CurvedTrackSegment(i, pNoise, world));
            chunkList.add(chunk);
        }
    }
    
    public static void generateFlatTrack(World world, List<Chunk> chunkList)
    {
        Chunk firstChunk = new Chunk(0);
        float yCoordinate = 100.0f;
        Vec2 point1 = new Vec2(-5, yCoordinate);
        Vec2 point2 = new Vec2(Chunk.CHUNK_WIDTH, yCoordinate);
        firstChunk.addBoundary(new FlatTrackSegment(world, point1, point2));
        
        Vec2 wallPoint1 = new Vec2(0, yCoordinate);
        Vec2 wallPoint2 = new Vec2(0, yCoordinate - 20);
        firstChunk.addBoundary(new WallTrackSegment(world, wallPoint1, wallPoint2, 5));
        chunkList.add(firstChunk);
        
        for(int  i = 1; i < 100; i++)
        {
            Vec2 p1 = new Vec2(i * Chunk.CHUNK_WIDTH, yCoordinate);
            Vec2 p2 = new Vec2((i+1) * Chunk.CHUNK_WIDTH, yCoordinate);
            Chunk chunk = new Chunk(i);
            chunk.addBoundary(new FlatTrackSegment(world, p1, p2));
            chunkList.add(chunk);
        }
    }
}