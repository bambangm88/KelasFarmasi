package com.kelasFarmasi.ujian;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

public class BelumAdapter extends RecyclerView.Adapter<BelumAdapter.BelumHolder>
{
	Context c;
	ArrayList<M_Belum> data;
	OnKlik klik;
	public BelumAdapter(Context c,ArrayList<M_Belum> data,OnKlik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}
	
	@Override
	public BelumHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_ujian,parent,false);
		return new BelumHolder(v);
	}

	@Override
	public void onBindViewHolder(BelumHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.judul.setText(data.get(posisi).getJudul());
		holder.txt.setText("Total Soal : "+data.get(posisi).getSoal()+
		"\nWaktu Pengerjaan : "+data.get(posisi).getWaktu()+
		"\nPeriode : "+data.get(posisi).getPeriode());
		/*holder.tv.setText("•Tipe : "+data.get(posisi).getTipe()+
		"\n•Judul : "+data.get(posisi).getJudul()+
		"\n•Total Soal : "+data.get(posisi).getSoal()+
		"\n•Total Waktu Pengerjaan : "+data.get(posisi).getWaktu()+
		"\n•Periode Waktu : "+data.get(posisi).getPeriode());*/
		holder.btn.setText("Mulai Ujian");
		holder.btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.toUjian(data.get(posisi));
				}
			});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class BelumHolder extends RecyclerView.ViewHolder{
		TextView judul,txt,btn;
		public BelumHolder(View v){
			super(v);
			judul=v.findViewById(R.id.judulUjian);
			txt=v.findViewById(R.id.txtUjian);
			btn=v.findViewById(R.id.btnUjian);
		}
	}
	
	public interface OnKlik{
		void toUjian(M_Belum data);
	}
	
}
