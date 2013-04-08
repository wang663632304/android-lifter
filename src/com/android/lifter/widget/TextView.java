package com.android.lifter.widget;

import com.android.lifter.R;
import com.android.lifter.logging.Loger;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


public class TextView extends android.widget.TextView
{
	 public TextView(Context context) 
	 {
		 super(context);
	 }

    public TextView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }
    
    private void parseAttributes(Context context, AttributeSet attrs) 
    {    	
        TypedArray taValue  = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        String szTypeface   = taValue.getString(R.styleable.TextView_typeface);
        Typeface tfTypeface = null;
                
        
        if(szTypeface != null && szTypeface.length() > 0)
        {
        	tfTypeface = Typeface.createFromAsset(context.getAssets(),String.format("fonts/%s", szTypeface));
        	
        	if(tfTypeface == null)
        	{ 
        		throw new RuntimeException("TextViewBase::parseAttributes() - can't load typeface - " + szTypeface);
        	}
        	
            setTypeface(tfTypeface);
        }
        
    }
}
