package com.kelasFarmasi.home;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder>
{
	Context c;
	ArrayList<M_Menu> data;
	Klik klik;
	public MenuAdapter(Context c,ArrayList<M_Menu> data,Klik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public MenuHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_menu,parent,false);
		return new MenuHolder(v);
	}

	@Override
	public void onBindViewHolder(MenuHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.img.setImageResource(data.get(posisi).getImg());
		holder.txt.setText(data.get(posisi).getNama());
		holder.ln.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.toMenu(posisi);
				}
			});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class MenuHolder extends RecyclerView.ViewHolder{
		ImageView img;
		TextView txt;
		LinearLayout ln;
		public MenuHolder(View v){
			super(v);
			img=v.findViewById(R.id.imgMenu);
			txt=v.findViewById(R.id.txtMenu);
			ln=v.findViewById(R.id.lnMenu);
		}
	}
	
	public interface Klik{
		void toMenu(int posisi);
	}
}
