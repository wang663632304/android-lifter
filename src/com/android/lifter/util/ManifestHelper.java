package com.android.lifter.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ManifestHelper 
{
	private String getProperty(Context coContext,String szProperty)
	{
		ApplicationInfo aiInfo = null;
		String szOut = "";
		
		try 
		{
			aiInfo = coContext.getPackageManager().getApplicationInfo(coContext.getPackageName(), PackageManager.GET_META_DATA);
			szOut  = aiInfo.metaData.getString(szProperty);
		} 
		catch(Exception e) 
		{
		}
		
		return szOut;
	}
}
