/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.math;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luke
 */
public class PerlinNoiseTest {
	
	@Test
	public void testgetSmoothNoise() {
		Random random = new Random();
		long seed = random.nextLong();
		long seed2= random.nextLong();
		PerlinNoise pNoise = new PerlinNoise(seed);
		PerlinNoise pNoise2 = new PerlinNoise(seed);
		PerlinNoise pNoise3 = new PerlinNoise(seed2);
		assertEquals(pNoise.getNoise(100, 90),pNoise2.getNoise(100, 90),.2);
		assertNotEquals(pNoise.getNoise(100, 90), pNoise3.getNoise(100,90));
	}

	@Test
	public void testGetNoise() {
		Random random = new Random();
		long seed = random.nextLong();
		long seed2= random.nextLong();
		PerlinNoise pNoise = new PerlinNoise(seed);
		PerlinNoise pNoise2 = new PerlinNoise(seed);
		PerlinNoise pNoise3 = new PerlinNoise(seed2);
		assertEquals(pNoise.getNoise(100, 90), pNoise2.getNoise(100, 90),.2);
		assertNotEquals(pNoise.getNoise(100, 90), pNoise3.getNoise(100,90));
		
	}

}
