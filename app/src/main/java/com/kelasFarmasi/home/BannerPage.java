package com.kelasFarmasi.home;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import android.widget.*;

import androidx.viewpager.widget.PagerAdapter;

public class BannerPage extends PagerAdapter
{
	Context c;
	int[] gambar;
	public BannerPage(Context c,int[] gambar){
		this.c=c;
		this.gambar=gambar;
	}

	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return gambar.length;
	}

	@Override
	public boolean isViewFromObject(View p1, Object p2)
	{
		// TODO: Implement this method
		return p1==p2;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_banner,container,false);
		ImageView img=v.findViewById(R.id.imgBanner);
		img.setImageResource(gambar[position]);
		container.addView(v);
		return v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		// TODO: Implement this method
		container.removeView((View)object);
	}
	
	
}
