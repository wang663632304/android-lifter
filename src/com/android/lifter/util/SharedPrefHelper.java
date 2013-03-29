package com.android.lifter.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefHelper 
{
	private SharedPreferences spPref = null; 
	
	
	public SharedPrefHelper(Context ctx)
	{
		spPref = PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	
	public String get(String name)
	{
		return spPref.getString(name,"");
	}
	
	public void set(String name,String value)
	{
		 SharedPreferences.Editor spEditor = spPref.edit();
		 spEditor.putString(name, value);
		 spEditor.commit();
	}
}
