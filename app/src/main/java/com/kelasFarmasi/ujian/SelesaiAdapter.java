package com.kelasFarmasi.ujian;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

public class SelesaiAdapter extends RecyclerView.Adapter<SelesaiAdapter.SelesaiHolder>
{
	Context c;
	ArrayList<M_Selesai> data;
	OnKlik klik;
	public SelesaiAdapter(Context c,ArrayList<M_Selesai> data,OnKlik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public SelesaiHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_ujian,parent,false);
		return new SelesaiHolder(v);
	}

	@Override
	public void onBindViewHolder(SelesaiHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.judul.setText(data.get(posisi).getJudul());
		holder.txt.setText("Total Soal : "+data.get(posisi).getSoal()+
		"\nBenar : "+data.get(posisi).getBenar()+
		"\nSalah : "+data.get(posisi).getSalah());
		/*holder.tv.setText("•Tipe : "+data.get(posisi).getTipe()+
						  "\n•Judul : "+data.get(posisi).getJudul()+
						  "\n•Total Soal : "+data.get(posisi).getSoal()+
						  "\n•Total Waktu Pengerjaan : "+data.get(posisi).getWaktu()+
						  "\n•Benar : "+data.get(posisi).getBenar()+
						  "\n•Salah : "+data.get(posisi).getSalah()+
						  "\n•Ragu-Ragu : "+data.get(posisi).getRagu()+
						  "\n•Tidak Terjawab : "+data.get(posisi).getTidak_terjawab()+
						  "\n•Nilai : "+data.get(posisi).getNilai());*/
		holder.btn.setText("Lihat Detail");
		holder.btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.toPreview(data.get(posisi));
				}
			});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}

	public class SelesaiHolder extends RecyclerView.ViewHolder{
		TextView judul,txt,btn;
		public SelesaiHolder(View v){
			super(v);
			judul=v.findViewById(R.id.judulUjian);
			txt=v.findViewById(R.id.txtUjian);
			btn=v.findViewById(R.id.btnUjian);
		}
	}

	public interface OnKlik{
		void toPreview(M_Selesai data);
	}

}
