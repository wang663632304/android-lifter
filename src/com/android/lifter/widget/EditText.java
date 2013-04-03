package com.android.lifter.widget;

import com.android.lifter.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditText extends android.widget.EditText
{
	 public EditText(Context context) 
	 {
		 super(context);
	 }

    public EditText(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyle) 
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
