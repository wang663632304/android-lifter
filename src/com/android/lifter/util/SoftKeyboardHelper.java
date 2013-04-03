package com.android.lifter.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftKeyboardHelper 
{
	public static void show(Context ctx,EditText edit)
	{
		((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edit,InputMethodManager.SHOW_FORCED);
	}
	
	public static void hide(Context ctx,EditText edit)
	{
        ((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}
}
