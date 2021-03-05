package com.kelasFarmasi.chat;
import android.os.*;
import android.view.*;
import com.kelasFarmasi.R;
import android.widget.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import java.util.*;
import android.content.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.*;

public class Chatting extends Fragment implements ChatAdapter.OnKlik
{
	RecyclerView rvChatting;
	TextView ksgChatting;
	ArrayList<M_Chat> data;
	ChatAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.chatting,container,false);
		rvChatting=v.findViewById(R.id.rvChatting);
		rvChatting.setLayoutManager(new LinearLayoutManager(getActivity()));
		ksgChatting=v.findViewById(R.id.ksgChatting);
		data=new ArrayList<>();
		AndroidNetworking.initialize(getActivity());
		return v;
	}
	
	private void get(){
		ksgChatting.setText("Loading...");
		ksgChatting.setVisibility(View.VISIBLE);
		rvChatting.setVisibility(View.GONE);
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/chat.php?id="+new Sesi(getActivity()).get())
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
							rvChatting.setVisibility(View.VISIBLE);
							ksgChatting.setVisibility(View.GONE);
							JSONArray ja=json.getJSONArray("data");
							data.clear();
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Chat(jo.getString("uuid_to"),
								jo.getString("nama_lengkap"),
								jo.getString("msg"),
								jo.getString("notif")));
							}
							adapter=new ChatAdapter(getActivity(),data,Chatting.this);
							adapter.notifyDataSetChanged();
							rvChatting.setAdapter(adapter);
						}else{
							ksgChatting.setText("Chatting Kosong");
							ksgChatting.setVisibility(View.VISIBLE);
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
					ksgChatting.setText(p1.toString());
					ksgChatting.setVisibility(View.VISIBLE);
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
