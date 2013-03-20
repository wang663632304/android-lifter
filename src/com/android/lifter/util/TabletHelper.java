package com.android.lifter.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Configuration;

public class TabletHelper 
{
	public boolean isTabletDevice(Context ctx)
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            Configuration con = ctx.getResources().getConfiguration();

            try
            {
                Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con,0x00000003); // Configuration.SCREENLAYOUT_SIZE_LARGE
                return r;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }
}
