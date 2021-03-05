package com.kelasFarmasi.chat;
import android.os.*;
import android.view.*;

import com.google.android.material.tabs.TabLayout;
import com.kelasFarmasi.R;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kelasFarmasi.*;

public class MainChat extends Fragment
{
	TabLayout tab;
	ViewPager vp;
	Page adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.main_chat,container,false);
		getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getActivity().getWindow().setStatusBarColor(getActivity().getColor(android.R.color.white));
		
		tab=v.findViewById(R.id.tabChat);
		tab.addTab(tab.newTab().setText("Chatting"));
		if(new Sesi(getActivity()).getLevel().toString().equals("member")){
		tab.addTab(tab.newTab().setText("Kontak"));
		}
		adapter=new Page(getActivity(),getFragmentManager(),tab.getTabCount());
		
		vp=v.findViewById(R.id.vpChat);
		vp.setAdapter(adapter);
		vp.setOffscreenPageLimit(adapter.getCount()>1? adapter.getCount()-1 : 1);
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
		return v;
	}
	
}
