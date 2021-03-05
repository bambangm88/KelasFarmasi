package com.kelasFarmasi.forum;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.kelasFarmasi.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class Forum extends AppCompatActivity
{
	Toolbar toolbar;
	ImageView logo;
	TabLayout tab;
	ViewPager vp;
	Page adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forum);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Forum");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		tab=findViewById(R.id.tabForum);
		tab.addTab(tab.newTab().setText("All Question"));
		tab.addTab(tab.newTab().setText("My Question"));
		adapter=new Page(this,getSupportFragmentManager(),tab.getTabCount());
		
		vp=findViewById(R.id.vpForum);
		vp.setAdapter(adapter);
		vp.setOffscreenPageLimit(adapter.getCount()>1?adapter.getCount()-1:1);
		vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
		
		tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
					vp.setCurrentItem(p1.getPosition());
				}

				@Override
				public void onTabUnselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTabReselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}
			});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: finish(); return true;
			default : return super.onOptionsItemSelected(item);
		}
	}
	
	
}
