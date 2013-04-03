package com.android.lifter.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

public class Loger 
{
	public static void d(String szText)
	{
		
		String szFile  = Thread.currentThread().getStackTrace()[3].getFileName();
		String szClass = Thread.currentThread().getStackTrace()[3].getClassName() + ":" + Thread.currentThread().getStackTrace()[3].getMethodName() + "()";
		String szLine = "" + Thread.currentThread().getStackTrace()[3].getLineNumber();
		
		
		Log.d("@@@","");
		Log.d("@@@","-------------------------------------------------------------------------------------");
		Log.d("@@@","file:  " + szFile);
		Log.d("@@@","line:  " + szLine);
		Log.d("@@@","class: " + szClass);
		Log.d("@@@","time:  " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		Log.d("@@@","-------------------------------------------------------------------------------------");
		Log.d("@@@",szText.trim());
		Log.d("@@@","-------------------------------------------------------------------------------------");
		Log.d("@@@","");
	}
	
	public static void memory()
	{
		Long lMemFree = Runtime.getRuntime().freeMemory();
		Long lMemMax = Runtime.getRuntime().maxMemory();
		Long lMemTotal = Runtime.getRuntime().totalMemory();
		
		String szMem = "free mem: " + humanReadableByteCount(lMemFree,false) + " max mem: " + humanReadableByteCount(lMemMax,false) + " total mem: " + humanReadableByteCount(lMemTotal,false);
	
		Log.d("@@@","-------------------------------------------------------------------------------------");
		Log.d("@@@",szMem);
		Log.d("@@@","-------------------------------------------------------------------------------------");
	}
	
	private static String humanReadableByteCount(long bytes, boolean si) 
	{
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
