package com.kelasFarmasi.forum;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.*;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumHolder>
{
	Context c;
	ArrayList<M_Forum> data;
	OnKlik klik;
	public ForumAdapter(Context c,ArrayList<M_Forum> data,OnKlik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}


	@Override
	public ForumHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_forum,parent,false);
		return new ForumHolder(v);
	}

	@Override
	public void onBindViewHolder(ForumHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.nama.setText(data.get(posisi).getNama());
		holder.jam.setText(data.get(posisi).getJam());
		holder.type.setText("#"+data.get(posisi).getType());
		holder.ask.setText(data.get(posisi).getAsk());
		holder.komen.setText(data.get(posisi).getKomen()+" comments");
		holder.ln.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.ViewForum(data.get(posisi));
				}
			});
		if(data.get(posisi).getIdUser().equals(new Sesi(c).get())){
			holder.ln.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View p1)
					{
						// TODO: Implement this method
						klik.DialogView(data.get(posisi),posisi);
						return true;
					}
				});
		}
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	
	public class ForumHolder extends RecyclerView.ViewHolder{
		TextView nama,jam,type,ask,komen;
		LinearLayout ln;
		public ForumHolder(View v){
			super(v);
			nama=v.findViewById(R.id.namaForum);
			jam=v.findViewById(R.id.jamForum);
			type=v.findViewById(R.id.typeForum);
			ask=v.findViewById(R.id.askForum);
			komen=v.findViewById(R.id.komenForum);
			ln=v.findViewById(R.id.lnForum);
		}
	}
	
	public interface OnKlik{
		void ViewForum(M_Forum data);
		void DialogView(M_Forum data, int index);
	}
}
