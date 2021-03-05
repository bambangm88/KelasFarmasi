package com.kelasFarmasi.ujian;
import android.content.*;
import android.util.*;
import android.content.res.*;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridUrutUjian extends RecyclerView
{
	private GridLayoutManager grid;
	private int columnWidth=-1;
	
	public GridUrutUjian(Context c){
		super(c);
	}
	
	public GridUrutUjian(Context c,AttributeSet attr){
		super(c,attr);
	}
	
	public GridUrutUjian(Context c,AttributeSet attr,int defStyle){
		super(c,attr,defStyle);
	}
	
	private void init(Context c,AttributeSet attr){
		if(attr!=null){
			int[] attrArray={android.R.attr.columnWidth};
			TypedArray array=c.obtainStyledAttributes(attr,attrArray);
			columnWidth=array.getDimensionPixelSize(0,-1);
			array.recycle();
		}
		grid=new GridLayoutManager(getContext(),1);
		setLayoutManager(grid);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthSpec, heightSpec);
		if(columnWidth>0){
			int spanCount=Math.max(1,getMeasuredWidth()/columnWidth);
			grid.setSpanCount(spanCount);
		}
	}
	
}
