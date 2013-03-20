package com.android.lifter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class SimpleAdapter<T> extends BaseAdapter 
{
    private ArrayList<T> alItem = new ArrayList<T>();
    private LayoutInflater mInflater;
    
    public SimpleAdapter(Context ctx)
    {
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
  
    
    public void add(T item)
    { 
    	if(alItem.contains(item))
    	{
    		return;
    	}
    	
    	alItem.add(item);
    }
    
    public T get(int id)
    {
        return alItem.get(id);
    }
    
    
    @Override
    public final long getItemId(int i) 
    {
        return 0;
    }
    
    @Override
    public final int getCount()
    {
        return alItem.size();
    }

    @Override
    public final Object getItem(int i)
    {
        return null;
    }

	@Override
	public View getView(int position,View convertView, final ViewGroup parent) 
	{
		T item = alItem.get(position);
		View view = convertView;
		
		if(view == null) 
        {
			view = mInflater.inflate(getItemAdapter(), null, false);
        }        
	    
		if(item == null)
        {
            return view;
        }
		
		
		getItemView(item,view,parent);
		return view; 
	}
	
	protected abstract int getItemAdapter();
	protected abstract void getItemView(T item,View convertView,ViewGroup parent);
}


