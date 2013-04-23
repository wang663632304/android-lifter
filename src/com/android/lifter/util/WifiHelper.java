package com.android.lifter.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class WifiHelper 
{
	public static boolean isWifi(Context ctx)
    {
        ConnectivityManager hConnMngr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean bInetWifi = hConnMngr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        return bInetWifi;
    }
	
}
