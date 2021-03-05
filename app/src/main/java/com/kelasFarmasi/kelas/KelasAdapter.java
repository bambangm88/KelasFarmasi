package com.kelasFarmasi.kelas;
import android.view.*;
import android.content.*;
import java.util.*;
import com.kelasFarmasi.R;
import android.widget.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.KelasHolder>
{
	Context c;
	ArrayList<M_Kelas> data;
	Klik klik;
	public KelasAdapter(Context c,ArrayList<M_Kelas> data,Klik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public KelasHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_kelas,parent,false);
		return new KelasHolder(v);
	}

	@Override
	public void onBindViewHolder(KelasHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.nama.setText(data.get(posisi).getNama());
		holder.desk.setText(data.get(posisi).getDesk());
		holder.btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.openSub(data.get(posisi).getNama());
				}
			});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class KelasHolder extends RecyclerView.ViewHolder{
		TextView nama,desk;
		Button btn;
		public KelasHolder(View v){
			super(v);
			nama=v.findViewById(R.id.namaKelas);
			desk=v.findViewById(R.id.deskKelas);
			btn=v.findViewById(R.id.btnKelas);
		}
	}
	
	public interface Klik{
		void openSub(String kelas);
	}
}
