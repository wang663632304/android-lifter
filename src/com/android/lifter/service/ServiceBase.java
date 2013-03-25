package com.android.lifter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceBase extends Service
{
	

	@Override
	public IBinder onBind(Intent inIntent) 
	{
		return null;
	}
	
}
