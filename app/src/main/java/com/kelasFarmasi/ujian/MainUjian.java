package com.kelasFarmasi.ujian;
import android.os.*;

import com.google.android.material.tabs.TabLayout;
import com.kelasFarmasi.R;
import android.view.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainUjian extends AppCompatActivity
{
	TabLayout tab;
	ViewPager vp;
	Page adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_ujian);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		tab=findViewById(R.id.tabUjian);
		tab.addTab(tab.newTab().setText("Belum Ujian"));
		tab.addTab(tab.newTab().setText("Selesai Ujian"));
		adapter=new Page(this,getSupportFragmentManager(),tab.getTabCount());
		
		vp=findViewById(R.id.vpUjian);
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
	
}
