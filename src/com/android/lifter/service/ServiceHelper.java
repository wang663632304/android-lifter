package com.android.lifter.service;

import android.content.Context;
import android.content.Intent;

public class ServiceHelper 
{
	public static void start(Context coContext,Class<?> cls)
	{
		coContext.startService(new Intent(coContext,cls)); 
	}
	
	public static void stop(Context coContext,Class<?> cls)
	{
		coContext.stopService(new Intent(coContext,cls));
	}
}
