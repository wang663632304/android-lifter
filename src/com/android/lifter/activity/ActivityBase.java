package com.android.lifter.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.lang.reflect.ParameterizedType;

import com.android.lifter.logging.Loger;


public abstract class ActivityBase extends Activity 
{
	@Override
	public final void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    
	    int iContentView = onActivityContentView();
	    
	    if(iContentView == 0)
	    {
	    	throw new RuntimeException("ActivityBase::onActivityContentView() returned 0"); 
	    }
	    	    
	    
	    onRequestFeature();
	    setContentView(iContentView);
	    onActivityCreate();
	}
	
	protected abstract void onRequestFeature();
	protected abstract int onActivityContentView();
	protected abstract void onActivityCreate();
	
	
	protected void removeTitle()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	protected void removeNotificatioBar()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	protected void orientationPortrait()
	{
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	protected void orientationLandscape()
	{
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	
	public EditText widgetEditText(int resId)
	{
		EditText widget = (EditText) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetEditText() - now such resource"); 
		}
		
		return widget;
	}
	
	public TextView widgetTextView(int resId)
	{
		TextView widget = (TextView) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetTextView() - now such resource"); 
		}
		
		return widget;
	}
	
	public CheckBox widgetCheckBox(int resId)
	{
		CheckBox widget = (CheckBox) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetCheckBox() - now such resource"); 
		}
		
		return widget;
	}
	
	public Button widgetButton(int resId)
	{
		Button widget = (Button) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetButton() - now such resource"); 
		}
		
		return widget;
	}
	
	public GridView widgetGridView(int resId)
	{
		GridView widget = (GridView) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetGridView() - now such resource"); 
		}
		
		return widget;
	}
	
	public Gallery widgetGallery(int resId)
	{
		Gallery widget = (Gallery) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetGallery() - now such resource"); 
		}
		
		return widget;
	}
	
	public ListView widgetListView(int resId)
	{
		ListView widget = (ListView) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetListView() - now such resource"); 
		}
		
		return widget;
	}
	
	public ImageButton widgetImageButton(int resId)
	{
		ImageButton widget = (ImageButton) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetImageButton() - now such resource"); 
		}
		
		return widget;
	}
	
	public ImageView widgetImageView(int resId)
	{
		ImageView widget = (ImageView) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetImageView() - now such resource"); 
		}
		
		return widget;
	}
	
	public Spinner widgetSpinner(int resId)
	{
		Spinner widget = (Spinner) findViewById(resId);
		
		if(widget == null)
		{
			throw new RuntimeException("ActivityBase::widgetSpinner() - now such resource"); 
		}
		
		return widget;
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
		super.onCreateOptionsMenu(menu);
		onActivityOptionsMenu(menu);
		
        return true;
    }
	
	protected abstract void onActivityOptionsMenu(Menu menu);
	
	public boolean onOptionsItemSelected(Menu menu) 
    {
		onActivityOptionsItemSelected(menu);
        return true;
    }
	
	protected abstract void onActivityOptionsItemSelected(Menu menu);
    
    @Override
    protected final void onPause() 
    {
    	onActivityPause();
    	super.onPause();
    }
    
    protected abstract void onActivityPause();
    
    
    @Override
    protected void onDestroy()
    {
    	onActivityDestroy();
    	super.onDestroy();
    }
    
    protected abstract void onActivityDestroy();
    
    
    @Override
    public void onBackPressed() 
    {
    	onActivityBackPressed();
        super.onBackPressed();
    }
    
    protected abstract void onActivityBackPressed();
    
    
    protected void runActivity(Class<?> cls)
    {
    	 startActivity(new Intent(getApplicationContext(),cls));
    }
}
