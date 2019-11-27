/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.utilities;

/**
 *
 * @author Luke
 */
public class Timer 
{
    private boolean isActive;
    private int counter;
    private final int fullTimer;
    private long nanoCounter;
    
    private static final long ONE_SECOND = 1000000000;
    
    public Timer(int sec)
    {
        fullTimer = sec;
        counter = sec;
        nanoCounter = ONE_SECOND;
    }
    
    public void startTimer()
    {
        this.isActive = true;
    }
    
    public void pauseTimer()
    {
        this.isActive = false;
    }
    
    public void resetTimer()
    {
        this.counter = fullTimer;
        this.nanoCounter = ONE_SECOND;
    }
    
    public void decreaseCounter(long delta)
    {
        if(this.isActive)
        {
            nanoCounter -= delta;
            if(nanoCounter <= 0)
            {
                this.nanoCounter = ONE_SECOND;
                counter--;
            }
        }
    }
    
    public boolean hasTimerExpired()
    {
        return this.counter <= 0;
    }
    
    public boolean isActive()
    {
        return this.isActive;
    }
}
