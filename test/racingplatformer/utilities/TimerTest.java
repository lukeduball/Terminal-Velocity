/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.utilities;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luke
 */
public class TimerTest 
{
    
    public TimerTest() 
    {
        
    }

    @Test
    public void testInActiveTimer()
    {
        Timer timer = new Timer(1);
        //Sets the timer as inactive
        timer.pauseTimer();
        //Timer should be finished after this amount of time if it is active
        timer.decreaseCounter(1000000000);
        assertEquals(timer.hasTimerExpired(), false);
    }
    
    @Test
    public void testActiveTimer()
    {
        Timer timer = new Timer(1);
        timer.startTimer();
        timer.decreaseCounter(1000000000);
        assertEquals(timer.hasTimerExpired(), true);
    }
    
    @Test
    public void testResetTimer()
    {
        Timer timer = new Timer(1);
        timer.startTimer();
        timer.decreaseCounter(1000000000/2);
        timer.resetTimer();
        timer.decreaseCounter(1000000000/2);
        assertEquals(timer.hasTimerExpired(), false);
        timer.decreaseCounter(1000000000/2);
        assertEquals(timer.hasTimerExpired(), true);
    }
    
}
