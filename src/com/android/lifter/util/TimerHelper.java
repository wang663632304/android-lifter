package com.android.lifter.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

public class TimerHelper 
{     
	private Handler haTask = new Handler();	
	private Timer tiTimer = new Timer();
	 
	private Runnable ruTimer = null;
	
	private TimerTask ttTask = new TimerTask()
	 {
	    @Override
	    public void run()
	    {
	    	 haTask.post(ruTimer);
	    }
	 };
	
    public TimerHelper(Runnable ruTask)
    {
    	if(ruTask == null)
    	{
    		throw new RuntimeException("TimerHelper::TimerHelper() - Runnable for task is null");
    	}
    	
    	ruTimer = ruTask;
    }
    
    public TimerHelper(Runnable ruTask,int delay)
    {
    	if(ruTask == null)
    	{
    		throw new RuntimeException("TimerHelper::TimerHelper() - Runnable for task is null");
    	}
    	
    	ruTimer = ruTask;
    	schedule(delay);
    }
    
    public void schedule(int delay)
    {
    	tiTimer.schedule(ttTask,delay);
    }
    
    public void schedlule(int delay,int period)
    {
    	tiTimer.schedule(ttTask, delay, period);
    }
    
    public void cancel()
    {
    	tiTimer.cancel();
    	tiTimer.purge();
    	haTask.removeCallbacks(ruTimer);
    }
}
