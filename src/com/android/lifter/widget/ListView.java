package com.android.lifter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ListView extends android.widget.ListView
{
	 public ListView(Context context) 
	 {
		 super(context);
	 }

    public ListView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
    }

    public ListView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
    }
   
    public LinearLayout setBottomViewLinearLayoutXML(int resId)
    {
    	if(getAdapter() != null)
    	{
    		throw new RuntimeException("ListView::setBottomViewLinearLayoutXML - adspter must be set after this function call!");
    	}
    	
    	LayoutInflater liInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	LinearLayout llLayout = (LinearLayout) liInflater.inflate(resId, null, false);
    	
    	if(llLayout == null)
    	{
    		throw new RuntimeException("ListView::setBottomViewLinearLayoutXML - can't inflate this layout");
    	}
    	
    	return llLayout;
    }
}
