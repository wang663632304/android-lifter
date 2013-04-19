package com.android.lifter.layout;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Klasa abstrakcyjna dla wszystkich widokow w aplikacji
 *
 */

public abstract class BaseLayout extends LinearLayout  
{
	protected Context coContext = null;
	protected AttributeSet asAttr = null; 
	private LayoutInflater liInflater  = null;
	private LinearLayout llView = null;

    public BaseLayout(Context coContext, AttributeSet asAttr) 
	{
		super(coContext,asAttr);
		this.coContext = coContext;
		this.asAttr = asAttr;
		
		liInflater = (LayoutInflater) coContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		llView     = (LinearLayout) liInflater.inflate(onLayoutContentView(), this,true);
	}
	
    
	@Override
	protected void onFinishInflate() 
	{
		super.onFinishInflate();
		llView = (LinearLayout) liInflater.inflate(onLayoutContentView(), this);
		
		onLayoutCreate(llView);
	}

	protected abstract int onLayoutContentView();

	
	@Override
	protected void onAttachedToWindow() 
	{
		super.onAttachedToWindow();
		onLayoutContent();
	}
	
	@Override
	protected void onDetachedFromWindow() 
	{
		super.onDetachedFromWindow();
		onLayoutDetach();
	}
	
	abstract protected void onLayoutCreate(LinearLayout llView);	
	abstract protected void onLayoutContent();
	abstract protected void onLayoutDetach();
}