package com.android.lifter.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

public class TimerHelper 
{     
     public TimerHelper(final Runnable ruTask,int iMilisec)
     {
    	 final Handler haTask = new Handler();	
    	 
		 TimerTask ttTask = new TimerTask()
		 {
		    @Override
		    public void run()
		    {
		    	 haTask.post(ruTask);
		    }
		 };
		 
		 new Timer().schedule(ttTask,iMilisec);
     }
}
