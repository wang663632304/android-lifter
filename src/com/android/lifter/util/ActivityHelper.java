package com.android.lifter.util;

import android.content.Context;
import android.content.Intent;

public class ActivityHelper 
{
	public void runWifiSettings(Context ctx)
	{
		Intent iIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
        iIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        ctx.startActivity(iIntent);
	}
}
