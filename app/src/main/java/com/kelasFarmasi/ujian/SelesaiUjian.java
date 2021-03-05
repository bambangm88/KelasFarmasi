package com.kelasFarmasi.ujian;
import android.os.*;
import android.view.*;
import com.kelasFarmasi.R;
import android.widget.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import com.kelasFarmasi.*;
import java.util.*;
import android.content.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelesaiUjian extends Fragment implements SelesaiAdapter.OnKlik
{
	RecyclerView rv;
	TextView tv;
	Sesi sesi;
	ArrayList<M_Selesai> data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.selesai_ujian,container,false);
		rv=v.findViewById(R.id.rvSelesaiUjian);
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		tv=v.findViewById(R.id.tvSelesaiUjian);
		tv.setText("Loading...");
		tv.setVisibility(View.VISIBLE);
		sesi=new Sesi(getActivity());
		AndroidNetworking.initialize(getActivity());
		data=new ArrayList<>();
		return v;
	}

	private void get(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/selesai_ujian.php?id="+sesi.get())
			.setPriority(Priority.LOW)
			.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						if (json.getString("status").equals("200"))
						{
							tv.setVisibility(View.GONE);
							rv.setVisibility(View.VISIBLE);
							JSONArray ja=json.getJSONArray("data");
							data.clear();
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Selesai(jo.getString("uuid"),
													 jo.getString("tipe"),
													 jo.getString("judul"),
													 jo.getString("total_soal"),
													 jo.getString("total_waktu_pengerjaan"),
													 jo.getString("benar"),
													 jo.getString("salah"),
													 jo.getString("ragu_ragu"),
													 jo.getString("tidak_terjawab"),
													 jo.getString("nilai")));
							}
							rv.setAdapter(new SelesaiAdapter(getActivity(),data,SelesaiUjian.this));
						}
						else
						{
							tv.setVisibility(View.VISIBLE);
							tv.setText("Ujian Kosong");
							rv.setVisibility(View.GONE);
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
					Toast.makeText(getActivity(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	public void toPreview(M_Selesai data)
	{
		// TODO: Implement this method
		Intent i=new Intent(getActivity(),DetailUjian.class);
		i.putExtra("data",data);
		startActivity(i);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		// TODO: Implement this method
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			getFragmentManager()
				.beginTransaction()
				.detach(this)
				.attach(this)
				.commit();
		}
	}

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}

}
