package com.kelasFarmasi.kelas;
import android.content.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.widget.*;
import java.text.*;
import android.view.View.*;

import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.*;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubHolder>
{
	Context c;
	ArrayList<M_Sub> data;
	Klik klik;
	public SubAdapter(Context c,ArrayList<M_Sub> data,Klik klik){
		this.c=c;
		this.data=data;
		this.klik=klik;
	}

	@Override
	public SubHolder onCreateViewHolder(ViewGroup parent, int p2)
	{
		// TODO: Implement this method
		View v=LayoutInflater.from(c).inflate(R.layout.item_sub,parent,false);
		return new SubHolder(v);
	}

	@Override
	public void onBindViewHolder(SubHolder holder, final int posisi)
	{
		// TODO: Implement this method
		holder.nama.setText(data.get(posisi).getJudul());
		holder.harga.setText(rupiah(data.get(posisi).getHarga()));
		holder.fasilitas.setText(data.get(posisi).getFasilitas());
		holder.btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					klik.toDetail(data.get(posisi));
				}
			});
		if(new Sesi(c).valid()){
			if(new Sesi(c).getLevel().toString().equals("admin")||
			   new Sesi(c).getLevel().toString().equals("mentor")){
				holder.lnTwoBtn.setVisibility(View.VISIBLE);
				holder.btn.setVisibility(View.GONE);
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
				holder.btn.setVisibility(View.VISIBLE);
			}
		}else{
			holder.lnTwoBtn.setVisibility(View.GONE);
			holder.btn.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	public class SubHolder extends RecyclerView.ViewHolder{
		TextView nama,harga,fasilitas;
		Button btn,update,delete;
		LinearLayout lnTwoBtn;
		public SubHolder(View v){
			super(v);
			nama=v.findViewById(R.id.namaSub);
			harga=v.findViewById(R.id.hargaSub);
			fasilitas=v.findViewById(R.id.fasilitasSub);
			btn=v.findViewById(R.id.btnSub);
			update=v.findViewById(R.id.btnUpdate);
			delete=v.findViewById(R.id.btnDelete);
			lnTwoBtn=v.findViewById(R.id.lnTwoBtn);
		}
	}
	
	private String rupiah(String rupiah){
		NumberFormat harga=NumberFormat.getCurrencyInstance(new Locale("in","ID"));
		return "Rp "+harga.format((double)Integer.parseInt(rupiah)).replace("Rp","");
	}
	
	public interface Klik{
		void toDetail(M_Sub data);
		void toUpdate(M_Sub data, int posisi);
		void toDelete(M_Sub data, int posisi);
	}
}
