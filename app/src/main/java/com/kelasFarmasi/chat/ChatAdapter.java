package com.kelasFarmasi.chat;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>
{
	Context c;
	ArrayList<M_Chat> data;
	OnKlik klik;
	public ChatAdapter(Context c,ArrayList<M_Chat> data,OnKlik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public ChatHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_chat,parent,false);
		return new ChatHolder(v);
	}

	@Override
	public void onBindViewHolder(ChatHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.nama.setText(data.get(posisi).getNama());
		holder.msg.setText(data.get(posisi).getMsg());
		holder.ln.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.ViewChat(data.get(posisi).getId(),data.get(posisi).getNama());
				}
			});
		if(Integer.parseInt(data.get(posisi).getNotif())>0){
			holder.notif.setVisibility(View.VISIBLE);
			holder.notif.setText(data.get(posisi).getNotif());
		}else{
			holder.notif.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class ChatHolder extends RecyclerView.ViewHolder{
		TextView nama,msg,notif;
		LinearLayout ln;
		public ChatHolder(View v){
			super(v);
			nama=v.findViewById(R.id.namaChat);
			msg=v.findViewById(R.id.msgChat);
			ln=v.findViewById(R.id.lnChat);
			notif=v.findViewById(R.id.notifChat);
		}
	}
	
	public interface OnKlik{
		void ViewChat(String id, String nama);
	}
}
