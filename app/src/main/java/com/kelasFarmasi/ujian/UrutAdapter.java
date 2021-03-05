package com.kelasFarmasi.ujian;
import android.view.*;
import java.util.*;
import android.content.*;
import android.widget.*;
import com.kelasFarmasi.R;
import android.view.View.*;
import android.graphics.*;

import androidx.recyclerview.widget.RecyclerView;

public class UrutAdapter extends RecyclerView.Adapter<UrutAdapter.UrutHolder>
{
	Context c;
	ArrayList<M_Urut> data;
	String urutan;
	OnKlik klik;
	public UrutAdapter(Context c,ArrayList<M_Urut> data,String urutan,OnKlik klik){
		this.c=c;
		this.data=data;
		this.urutan=urutan;
		this.klik=klik;
	}

	@Override
	public UrutHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_urut,parent,false);
		return new UrutHolder(v);
	}

	@Override
	public void onBindViewHolder(UrutHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.urut.setText(data.get(posisi).getNmr());
		if(data.get(posisi).getNmr().toString().equals(urutan)){
			if(data.get(posisi).getRagu().toString().equals("1")){
				holder.urut.setBackgroundResource(R.drawable.urut_ragu_selected);
				holder.urut.setTextColor(Color.WHITE);
			}else if(data.get(posisi).getJwb().toString().equals("g")){
				holder.urut.setBackgroundResource(R.drawable.urut_selected);
				holder.urut.setTextColor(Color.BLACK);
			}else{
				holder.urut.setBackgroundResource(R.drawable.urut_jawab_selected);
				holder.urut.setTextColor(Color.WHITE);
			}
		}else{
			if(data.get(posisi).getRagu().toString().equals("1")){
				holder.urut.setBackgroundColor(Color.parseColor("#2D00FF"));
				holder.urut.setTextColor(Color.WHITE);
			}else if(data.get(posisi).getJwb().toString().equals("g")){
				holder.urut.setBackgroundResource(R.drawable.urut_no_select);
				holder.urut.setTextColor(Color.BLACK);
			}else{
				holder.urut.setBackgroundColor(Color.parseColor("#00AC00"));
				holder.urut.setTextColor(Color.WHITE);
			}
		}
		holder.urut.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(Integer.parseInt(data.get(posisi).getNmr())!=Integer.parseInt(urutan)){
						klik.next(data.get(posisi).getNmr());
					}
				}
			});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class UrutHolder extends RecyclerView.ViewHolder{
		TextView urut;
		public UrutHolder(View v){
			super(v);
			urut=v.findViewById(R.id.nmrUrut);
		}
	}
	
	public interface OnKlik{
		void next(String urut);
	}
}
