package com.android.lifter.util;

import android.os.Build;

/**
 * pobiera unikalne id urzadzenia
 * 
 * @author matrut
 */
public class DeviceIDHelper 
{
	/**
	 * pobiera unikalne ID urzadzenia
	 * 
	 * @returnpobiera ID urzadzenia
	 */
	public static String getDeviceId() 
	{
		String id = "35" +
            Build.BOARD.length()%10+ Build.BRAND.length()%10 +
            Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
            Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
            Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
            Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
            Build.TAGS.length()%10 + Build.TYPE.length()%10 +
            Build.USER.length()%10 ;
		
		return id;
	}
}

