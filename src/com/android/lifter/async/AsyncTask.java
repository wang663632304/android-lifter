package com.android.lifter.async;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * customowy asynctask
 * 
 */
public class AsyncTask
{
	
	/**
	 * interfejs do zadan tasku
	 */
	public abstract interface OnTaskListener
	{			
		public abstract void onBefore();
		public abstract void onThread();
		public abstract void onFinish();
		public abstract void onResult(int iWhat,Object obResult);
		public abstract void onInterrupted();
	}
	
	/**
	 * listener tasku
	 */
	private OnTaskListener otlTaskListener = null;
	
	/**
	 * konstruktor
	 * 
	 * @param otlTaskListener listener implementujacy zadania tasku
	 */
	public AsyncTask(OnTaskListener otlTaskListener)
	{
		this.otlTaskListener = otlTaskListener;
	}
	
	/**
	 * watek tasku
	 */
	private Thread thTask = null;
	
	/**
	 * runnable tasku
	 */
	private Runnable ruTask = new Runnable()
	{
		@Override
		public void run() 
		{			
			try
			{
				Thread.sleep(0);
				otlTaskListener.onThread();
				Thread.sleep(0);
				haFinish.sendEmptyMessage(0);
			}
			catch(InterruptedException e)
			{
				haInterrupt.sendEmptyMessage(0);
			}
		}
	};
	
	/**
	 * handler obslugujacy rpzerwaniw wykonywania tasku
	 */
	private Handler haInterrupt = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			otlTaskListener.onInterrupted();
		}
	};
	
	private Handler haFinish = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			otlTaskListener.onFinish();
		}
	};
	
	/**
	 * handler dla rezultatow tasku
	 */
	private Handler haResult = new Handler()
	{
		@Override
		public void handleMessage(Message mMsg) 
		{
			otlTaskListener.onResult(mMsg.what,mMsg.obj);
		}
	};
	
	/**
	 * uruchamia task
	 */
	public void start()
	{
		if(otlTaskListener == null)
		{
			throw new RuntimeException("AsyncTask - listiner not set");
		}
		
		if(Looper.myLooper() == null)
		{
			throw new RuntimeException("AsyncTask() must be used in the UI thread!");
		}
				
		otlTaskListener.onBefore();
		
		thTask = new Thread(ruTask);
		thTask.start();
	}
	
	/**
	 * postuje rezultat tasku
	 */
	public void result(int iWhat,Object obObj)
	{
		Message mMsg = Message.obtain();
		mMsg.what = iWhat;
		mMsg.obj  = obObj;
		haResult.sendMessage(mMsg);
	}
	
	
	
	/**
	 * przerywa wykonywanie tasku
	 */
	public void interrupt()
	{
		if(thTask != null)
		{
			thTask.interrupt();
			thTask = null;
		}
	}
}