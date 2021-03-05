package com.kelasFarmasi.chat;
import android.os.*;
import android.view.*;
import com.kelasFarmasi.R;
import android.widget.*;
import java.util.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import android.content.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Kontak extends Fragment implements ChatAdapter.OnKlik
{
	RecyclerView rvKontak;
	TextView ksgKontak;
	ArrayList<M_Chat> data;
	ChatAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.kontak,container,false);
		rvKontak=v.findViewById(R.id.rvKontak);
		rvKontak.setLayoutManager(new LinearLayoutManager(getActivity()));
		ksgKontak=v.findViewById(R.id.ksgKontak);
		data=new ArrayList<>();
		AndroidNetworking.initialize(getActivity());
		return v;
	}
	
	private void get(){
		ksgKontak.setText("Loading...");
		ksgKontak.setVisibility(View.VISIBLE);
		rvKontak.setVisibility(View.GONE);
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/kontak.php")
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
							rvKontak.setVisibility(View.VISIBLE);
							ksgKontak.setVisibility(View.GONE);
							JSONArray ja=json.getJSONArray("data");
							data.clear();
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Chat(jo.getString("uuid"),
								jo.getString("nama_lengkap"),
								"Ketuk Untuk Memulai Percakapan",
								"0"));
							}
							adapter=new ChatAdapter(getActivity(),data,Kontak.this);
							adapter.notifyDataSetChanged();
							rvKontak.setAdapter(adapter);
						}else{
							ksgKontak.setText("Kontak Kosong");
							ksgKontak.setVisibility(View.VISIBLE);
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
					ksgKontak.setText(p1.toString());
					ksgKontak.setVisibility(View.VISIBLE);
				}
			});
	}

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}

	@Override
	public void ViewChat(String id,String nama)
	{
		// TODO: Implement this method
		Intent i=new Intent(getActivity(),ViewChat.class);
		i.putExtra("id_to",id);
		i.putExtra("nama",nama);
		startActivity(i);
	}
	
}
