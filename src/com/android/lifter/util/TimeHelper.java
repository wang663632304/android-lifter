package com.android.lifter.util;

public class TimeHelper 
{
	public static String secToMinSec(int iSec)
	{
		 int seconds = iSec;
		 int hours = seconds/(60*60); // integer/integer = integer
		 seconds = seconds % (60*60);	// modulo (remainder after division)

		 int minutes = seconds/60;
		 seconds = seconds % 60;
		 
		 return String.format("%02d:%02d", minutes,seconds);
	}
	
	public static String secToHourMinSec(int iSec)
	{
		 int seconds = iSec;
		 int hours = seconds/(60*60); // integer/integer = integer
		 seconds = seconds % (60*60);	// modulo (remainder after division)

		 int minutes = seconds/60;
		 seconds = seconds % 60;
		 
		 return String.format("%02:%02d:%02d",hours, minutes,seconds);
	}
}
