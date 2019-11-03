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
    
    public static void generateTrack(Race race, World world, long seed, List<Chunk> chunkList)
    {
        Random rand = new Random();
        PerlinNoise pNoise = new PerlinNoise(8932937492483242560L);
        
        Chunk firstChunk = new Chunk(0);
        float yCoordinate = -pNoise.getSmoothNoise(Chunk.CHUNK_WIDTH, 100) + 150;
        Vec2 point1 = new Vec2(-5, yCoordinate);
        Vec2 point2 = new Vec2(Chunk.CHUNK_WIDTH, yCoordinate);
        firstChunk.addBoundary(new FlatTrackSegment(world, point1, point2));
        
        Vec2 wallPoint1 = new Vec2(0, yCoordinate);
        Vec2 wallPoint2 = new Vec2(0, yCoordinate - 20);
        firstChunk.addBoundary(new WallTrackSegment(world, wallPoint1, wallPoint2, 5));
        chunkList.add(firstChunk);
        
        int totalChunks = rand.nextInt(75) + 50;
        for(int i = 1; i < totalChunks; i++)
        {
            Chunk chunk = new Chunk(i);
            chunk.addBoundary(new CurvedTrackSegment(i, pNoise, world));
            chunkList.add(chunk);
        }
        
        float finalChunkHeight = -pNoise.getSmoothNoise(Chunk.CHUNK_WIDTH*totalChunks, 100) + 150;
        generateFinishLineArea(race, world, chunkList, totalChunks, finalChunkHeight);
    }
    
    public static void generateFlatTrack(Race race, World world, List<Chunk> chunkList)
    {
        Chunk firstChunk = new Chunk(0);
        float yCoordinate = 100.0f;
        Vec2 point1 = new Vec2(-5, yCoordinate);
        Vec2 point2 = new Vec2(Chunk.CHUNK_WIDTH, yCoordinate);
        firstChunk.addBoundary(new FlatTrackSegment(world, point1, point2));
        
        Vec2 wallPoint1 = new Vec2(0, yCoordinate);
        Vec2 wallPoint2 = new Vec2(0, yCoordinate - 20);
        firstChunk.addBoundary(new WallTrackSegment(world, wallPoint1, wallPoint2, 5.0f));
        chunkList.add(firstChunk);
        
        int  i = 1;
        for(i = 1; i < 100; i++)
        {
            Vec2 p1 = new Vec2(i * Chunk.CHUNK_WIDTH, yCoordinate);
            Vec2 p2 = new Vec2((i+1) * Chunk.CHUNK_WIDTH, yCoordinate);
            Chunk chunk = new Chunk(i);
            chunk.addBoundary(new FlatTrackSegment(world, p1, p2));
            chunkList.add(chunk);
        }
        generateFinishLineArea(race, world, chunkList, i++, yCoordinate);
    }
    
    private static void generateFinishLineArea(Race race, World world, List<Chunk> chunkList, int nextChunkID, float height)
    {
        for(int i = 0; i < 4; i++)
        {
            Vec2 p1 = new Vec2((i+nextChunkID) * Chunk.CHUNK_WIDTH, height);
            Vec2 p2 = new Vec2((i+nextChunkID+1)* Chunk.CHUNK_WIDTH, height);
            Chunk chunk = new Chunk(nextChunkID + i);
            chunk.addBoundary(new FlatTrackSegment(world, p1, p2));
            chunkList.add(chunk);
            
            //Add the finish line flag into the race
            if(i == 0)
            {
                FinishLine fnLine = new FinishLine(p1);
                race.setFinishLine(fnLine);
            }
            
            //Add a back wall into the race
            if(i == 3)
            {
                Vec2 wallPoint1 = new Vec2(p2.x - 5.0f, height);
                Vec2 wallPoint2 = new Vec2(p2.x - 5.0f, height - 20);
                chunk.addBoundary(new WallTrackSegment(world, wallPoint1, wallPoint2, -5.0f));
            }
        }
    }
}