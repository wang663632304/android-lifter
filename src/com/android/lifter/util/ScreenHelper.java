package com.android.lifter.util;

import android.content.Context;

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
}
