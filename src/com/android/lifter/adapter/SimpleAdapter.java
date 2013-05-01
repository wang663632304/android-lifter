package com.android.lifter.adapter;

import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class SimpleAdapter<T> extends BaseAdapter 
{
	private Context coContext = null;
    private ArrayList<T> alItem = new ArrayList<T>();
    private Hashtable<Integer,View> htView = new Hashtable<Integer,View>();
    private LayoutInflater mInflater;
    private boolean bUnique = false;
     
    
    public SimpleAdapter(Context ctx)
    {
    	coContext = ctx;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
  
    protected void runActivity(Class<?> cls)
    {
    	Intent inIntent = new Intent(coContext,cls);
    	inIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	coContext.startActivity(inIntent);
   }
    
    public void setUnique(boolean bUnique)
    {
    	this.bUnique = bUnique;
    }
    
    
    public void add(T item)
    { 
    	if(bUnique == true && alItem.contains(item))
    	{
    		return;
    	}
    	
    	alItem.add(item);
    }
    
    public void add(ArrayList<T> list)
    {
    	for(T item : list)
    	{
    		add(item);
    	}
    }
    
    public T get(int id)
    {
        return alItem.get(id);
    }
    
    public ArrayList<T> getAll()
    {
    	return alItem;
    }
    
    public void remove(int id)
    {
    	alItem.remove(id);
    	htView.clear();
    }
    
    public void remove(T item)
    {
    	alItem.remove(item);
    	htView.clear();
    }
    
    public void clear()
    {
    	alItem.clear();
    	htView.clear();
    }
    
    @Override
    public final long getItemId(int i) 
    {
        return i;
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
		
		if(htView.containsKey(position) == false)
		{
			view = mInflater.inflate(getItemAdapter(), null, false);
			getItemView(position,item,view,parent);
			htView.put(position, view);
		}
		else
		{
			view = htView.get(position);
		}
	    
		return view; 
	}
	
	protected abstract int getItemAdapter();
	protected abstract void getItemView(int position,T item,View convertView,ViewGroup parent);
	
	
	public void refresh()
	{
		notifyDataSetInvalidated();
		notifyDataSetChanged();
	}
	
	public void update()
	{
		refresh();
	}
	
	public void reload()
	{
		refresh();
	}
}


