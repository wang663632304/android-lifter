package com.android.lifter.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenHelper 
{
	public static int getScreenHeight(Context ctx)
	{
		return ctx.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int getScreenWidth(Context ctx)
	{
		return ctx.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreeenInches(Context ctx)
	{
		 DisplayMetrics dm = ctx.getResources().getDisplayMetrics();

         float screenWidth  = dm.widthPixels / dm.xdpi;
         float screenHeight = dm.heightPixels / dm.ydpi;

         return (int) Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
	}
}
