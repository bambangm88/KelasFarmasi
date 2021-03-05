package com.kelasFarmasi.chat;
import android.os.*;
import com.kelasFarmasi.R;
import java.util.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import com.androidnetworking.error.*;
import org.json.*;
import com.kelasFarmasi.*;

import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.*;

public class ViewChat extends AppCompatActivity implements OnClickListener
{
	Toolbar toolbar;
	ImageView logo;
	RecyclerView rvViewChat;
	EditText inputChat;
	ImageView sendChat;
	ArrayList<M_ViewChat> data;
	ViewChatAdapter adapter;
	String key_chat;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_chat);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(getIntent().getStringExtra("nama"));
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		rvViewChat=findViewById(R.id.rvViewChat);
		rvViewChat.setLayoutManager(new LinearLayoutManager(this));
		inputChat=findViewById(R.id.inputChat);
		sendChat=findViewById(R.id.sendChat);
		sendChat.setOnClickListener(this);
		data=new ArrayList<>();
		AndroidNetworking.initialize(this);
	}
	
	private void get(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/view_chat.php?from="+new Sesi(this).get()+"&to="+getIntent().getStringExtra("id_to"))
		.setPriority(Priority.LOW)
		.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						key_chat=json.getString("key_chat");
						if (json.getString("status").equals("200"))
						{
							JSONArray ja=json.getJSONArray("data");
							data.clear();
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_ViewChat(jo.getString("uuid_from"),
								jo.getString("msg"),
								jo.getString("create_at")));
							}
							adapter=new ViewChatAdapter(getApplicationContext(),data);
							rvViewChat.setAdapter(adapter);
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
					Toast.makeText(getApplicationContext(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}
	
	private void post(){
		JSONObject datas=new JSONObject();
		try{
			datas.put("key_chat",key_chat);
			datas.put("uuid_from",new Sesi(this).get());
			datas.put("uuid_to",getIntent().getStringExtra("id_to"));
			datas.put("msg",inputChat.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/addChat.php")
		.addJSONObjectBody(datas)
		.setPriority(Priority.MEDIUM)
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
							data.add(new M_ViewChat(new Sesi(getApplicationContext()).get(),
							inputChat.getText().toString(),
							getDatetime()));
							inputChat.setText("");
							if(data.size()>1){
								adapter.notifyDataSetChanged();
								rvViewChat.smoothScrollToPosition(data.size()-1);
							}else{
								adapter=new ViewChatAdapter(getApplicationContext(),data);
								rvViewChat.setAdapter(adapter);
							}
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
					Toast.makeText(getApplicationContext(),p1.toString(), Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		if(inputChat.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(),"Input Your Message",Toast.LENGTH_SHORT).show();
		}else{
			post();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: finish(); return true;
			default: return super.onOptionsItemSelected(item);
		}
	}

	
	public String getDatetime() {
		Calendar c = Calendar .getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	
}
