package com.kelasFarmasi;
import android.widget.*;
import android.graphics.*;
import android.view.View.*;
import android.view.*;
import android.os.*;

public class PreLoad
{
	TextView preload;
	public PreLoad(final TextView preload){
		this.preload=preload;
		preload.setTextColor(Color.parseColor("#4F4F4F"));
		gone();
	}
	
	public void loading(String msg){
		preload.setText(msg);
		preload.setBackgroundColor(Color.parseColor("#DDDDDD"));
		preload.setVisibility(View.VISIBLE);
	}
	
	public void success(String msg){
		preload.setText(msg);
		preload.setBackgroundColor(Color.parseColor("#95FB92"));
		preload.setVisibility(View.VISIBLE);
		cancel();
	}
	
	public void failed(String msg){
		preload.setText(msg);
		preload.setBackgroundColor(Color.parseColor("#FB9C95"));
		preload.setVisibility(View.VISIBLE);
		cancel();
	}
	
	private void cancel(){
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					preload.setVisibility(View.GONE);
				}
			}, 3000);
	}
	
	public void gone(){
		preload.setVisibility(View.GONE);
	}
}
