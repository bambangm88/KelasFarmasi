package com.kelasFarmasi.ujian;
import android.content.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Page extends FragmentStatePagerAdapter
{
	int tabCount;
	Context c;
	public Page(Context c, FragmentManager fm, int tabCount){
		super(fm);
		this.c=c;
		this.tabCount=tabCount;
	}

	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return tabCount;
	}

	@Override
	public Fragment getItem(int posisi)
	{
		// TODO: Implement this method
		switch(posisi){
			case 0 : return new BelumUjian();
			case 1 : return new SelesaiUjian();
		}
		return null;
	}
	
}
