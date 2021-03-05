package com.kelasFarmasi.video;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;
import com.bumptech.glide.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.*;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder>
{
	Context c;
	ArrayList<M_Video> data;
	OnKlik klik;
	public VideoAdapter(Context c,ArrayList<M_Video> data,OnKlik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public VideoHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_video,parent,false);
		return new VideoHolder(v);
	}

	@Override
	public void onBindViewHolder(VideoHolder holder, final int posisi)
	{
		// TODO: Implement this method
		Glide.with(c)
			.load("https://img.youtube.com/vi/"+data.get(posisi).getUrl().split("v=")[1]+"/hqdefault.jpg")
		.into(holder.thumb);
		holder.judul.setText(data.get(posisi).getJudul());
		holder.ln.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.playVideo(data.get(posisi));
				}
			});
		if(new Sesi(c).valid()){
			if(new Sesi(c).getLevel().toString().equals("admin")||
			   new Sesi(c).getLevel().toString().equals("mentor")){
				holder.lnTwoBtn.setVisibility(View.VISIBLE);
				holder.update.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							klik.toUpdate(data.get(posisi),posisi);
						}
					});
				holder.delete.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							klik.toDelete(data.get(posisi),posisi);
						}
					});
			}else{
				holder.lnTwoBtn.setVisibility(View.GONE);
			}
		}else{
			holder.lnTwoBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class VideoHolder extends RecyclerView.ViewHolder{
		ImageView thumb;
		TextView judul;
		LinearLayout ln,lnTwoBtn;
		Button update,delete;
		public VideoHolder(View v){
			super(v);
			thumb=v.findViewById(R.id.thumbVideo);
			judul=v.findViewById(R.id.judulVideo);
			ln=v.findViewById(R.id.lnVideo);
			lnTwoBtn=v.findViewById(R.id.lnTwoBtn);
			update=v.findViewById(R.id.btnUpdate);
			delete=v.findViewById(R.id.btnDelete);
		}
	}
	
	public interface OnKlik{
		void playVideo(M_Video data);
		void toUpdate(M_Video data, int posisi);
		void toDelete(M_Video data, int posisi);
	}
}
