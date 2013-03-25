package com.android.lifter.logging;

import android.util.Log;

public class Loger 
{
	public static void d(String szText)
	{
		
		String szFile  = Thread.currentThread().getStackTrace()[3].getFileName();
		String szClass = Thread.currentThread().getStackTrace()[3].getClassName() + ":" + Thread.currentThread().getStackTrace()[3].getMethodName() + "()";
		String szLine = "" + Thread.currentThread().getStackTrace()[3].getLineNumber();
		
		Log.d("@@@","-----------------------------");
		Log.d("@@@","-----------------------------");
		Log.d("@@@","file:  " + szFile);
		Log.d("@@@","line:  " + szLine);
		Log.d("@@@","class: " + szClass);
		Log.d("@@@","-----------------------------");
		Log.d("@@@",szText.trim());
		Log.d("@@@","-----------------------------");
		Log.d("@@@","-----------------------------");
	}
}