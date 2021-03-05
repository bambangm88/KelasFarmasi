package com.kelasFarmasi.chat;
import android.content.*;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelasFarmasi.R;
import java.util.*;;

import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.*;

public class ViewChatAdapter extends RecyclerView.Adapter<ViewChatAdapter.ViewChatHolder>
{
	Context c;
	ArrayList<M_ViewChat> data;
	public ViewChatAdapter(Context c,ArrayList<M_ViewChat> data){
		this.c=c;
		this.data=data;
	}

	@Override
	public ViewChatHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_view_chat,parent,false);
		return new ViewChatHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewChatHolder holder, int posisi)
	{
		// TODO: Implement this method
		if(data.get(posisi).getFrom().equals(new Sesi(c).get())){
			holder.lnChatLeft.setVisibility(View.GONE);
			holder.lnChatRight.setVisibility(View.VISIBLE);
		}else{
			holder.lnChatLeft.setVisibility(View.VISIBLE);
			holder.lnChatRight.setVisibility(View.GONE);
		}
		holder.chatLeft.setText(data.get(posisi).getMsg());
		holder.chatRight.setText(data.get(posisi).getMsg());
		String[] jam=data.get(posisi).getJam().split(" ")[1].split(":");
		holder.jamChatLeft.setText(jam[0]+":"+jam[1]);
		holder.jamChatRight.setText(jam[0]+":"+jam[1]);
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class ViewChatHolder extends RecyclerView.ViewHolder{
		TextView chatLeft,chatRight;
		LinearLayout lnChatLeft,lnChatRight;
		TextView jamChatLeft,jamChatRight;
		public ViewChatHolder(View v){
			super(v);
			chatLeft=v.findViewById(R.id.chatLeft);
			chatRight=v.findViewById(R.id.chatRight);
			lnChatLeft=v.findViewById(R.id.lnChatLeft);
			lnChatRight=v.findViewById(R.id.lnChatRight);
			jamChatLeft=v.findViewById(R.id.jamChatLeft);
			jamChatRight=v.findViewById(R.id.jamChatRight);
		}
	}
	
}
