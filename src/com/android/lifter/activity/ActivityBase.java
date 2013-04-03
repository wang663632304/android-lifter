package com.android.lifter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
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
import com.android.lifter.util.ScreenHelper;


public abstract class ActivityBase extends Activity 
{
	private PowerManager pmPower = null;
	private WakeLock wlScreen = null;
	
	
	@Override
	public final void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    
	    int iContentView = onActivityContentView();
	    
	    if(iContentView == 0)
	    {
	    	throw new RuntimeException("ActivityBase::onActivityContentView() onActivityContentView() is not set"); 
	    }
	   
	    int iScreenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
	    int iPixWidth   = ScreenHelper.getScreenWidth(getApplicationContext());
	    int iPixHeight  = ScreenHelper.getScreenHeight(getApplicationContext());
	    int iScreenInches = ScreenHelper.getScreeenInches(getApplicationContext());
	    
	    switch(iScreenSize) 
	    {
	    case Configuration.SCREENLAYOUT_SIZE_LARGE:
	    	Loger.d("ActivityBase::onCreate() - screen large - width " + iPixWidth + " height " + iPixHeight + " inches " + iScreenInches);
	        break;
	        
	    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
	    	Loger.d("ActivityBase::onCreate() - screen normal - width " + iPixWidth + " height " + iPixHeight + " inches " + iScreenInches);
	        break;
	        
	    case Configuration.SCREENLAYOUT_SIZE_SMALL:
	    	Loger.d("ActivityBase::onCreate() - screen small - width " + iPixWidth + " height " + iPixHeight + " inches " + iScreenInches);
	        break;
	        
	    default:
	    	Loger.d("ActivityBase::onCreate() - screen unknown width " + iPixWidth + " height " + iPixHeight + " inches " + iScreenInches);
	    	break;
	    }
	    

	    Loger.memory();
	    
	    
        pmPower  = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wlScreen = pmPower.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock Tag");
        wlScreen.acquire();
	    
        
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
	
	protected void hideSoftKeyboard()
	{
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	
	@Override
	protected final void onResume() 
	{
		super.onResume();
		onActivityResume();
	}

	protected abstract void onActivityResume();
	
	
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
