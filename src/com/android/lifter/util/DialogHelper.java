package com.android.lifter.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHelper
{
	public static void createYesNoDialog(Context ctx,String title,String msg,String aTxt,final Runnable aRunnable,String bTxt,final Runnable bRunnable)
	{
	    AlertDialog.Builder adBuilder = new AlertDialog.Builder(ctx);
        adBuilder.setTitle(title);
        adBuilder.setMessage(msg);
        adBuilder.setCancelable(false);
        adBuilder.setNegativeButton(aTxt, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface diDialog, int id)
            {
            	aRunnable.run();
            }
        });
        
        adBuilder.setNeutralButton(bTxt, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface diDialog, int id) 
            {
            	bRunnable.run();
            }
        });

        adBuilder.create().show();
	}
	
}
