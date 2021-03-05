package com.kelasFarmasi.forum;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

public class ViewForumAdapter extends RecyclerView.Adapter<ViewForumAdapter.ViewForumHolder>
{
	Context c;
	ArrayList<M_Komentar> data;
	public ViewForumAdapter(Context c,ArrayList<M_Komentar> data){
		this.c=c;
		this.data=data;
	}

	@Override
	public ViewForumHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_komentar,parent,false);
		return new ViewForumHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewForumHolder holder, int posisi)
	{
		// TODO: Implement this method
		holder.nama.setText(data.get(posisi).getNama());
		holder.jam.setText(data.get(posisi).getJam());
		holder.komen.setText(data.get(posisi).getKomentar());
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class ViewForumHolder extends RecyclerView.ViewHolder{
		TextView nama,jam,komen;
		public ViewForumHolder(View v){
			super(v);
			nama=v.findViewById(R.id.namaKomen);
			jam=v.findViewById(R.id.jamKomen);
			komen=v.findViewById(R.id.komenKomen);
		}
	}
}
