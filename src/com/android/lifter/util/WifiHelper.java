package com.android.lifter.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.Settings;

public class WifiHelper 
{
	public static boolean isWifi(Context ctx)
    {
        boolean bAirplane  = Settings.System.getInt(ctx.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        
        if(bAirplane == true)
        {
        	return false;
        }
        
        ConnectivityManager hConnMngr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean bInetWifi = hConnMngr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        boolean bInetMobile = hConnMngr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
        
        if(bInetWifi == true)
        {
        	return true;
        }
        
        if(bInetMobile == true)
        {
        	return true;
        }
       
        
        return false;
    }
	
}
