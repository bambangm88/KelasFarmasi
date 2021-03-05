package com.kelasFarmasi.forum;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kelasFarmasi.R;
import com.kelasFarmasi.Sesi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewForum extends AppCompatActivity implements View.OnClickListener
{
	Toolbar toolbar;
	ImageView logo;
	TextView nama,jam,type,ask,totalKomen;
	M_Forum mForum;
	RecyclerView rvKomen;
	ArrayList<M_Komentar> data;
	ViewForumAdapter adapter;
	EditText inputKomen;
	ImageView sendKomen;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_forum);
		mForum=getIntent().getParcelableExtra("data");
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(mForum.getNama());
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		nama=findViewById(R.id.namaForum);
		nama.setText(mForum.getNama());
		jam=findViewById(R.id.jamForum);
		jam.setText(mForum.getJam());
		type=findViewById(R.id.typeForum);
		type.setText(mForum.getType().toUpperCase());
		ask=findViewById(R.id.askForum);
		ask.setText(mForum.getAsk());
		totalKomen=findViewById(R.id.komenForum);
		totalKomen.setText(mForum.getKomen()+" comments");
		rvKomen=findViewById(R.id.komentarForum);
		rvKomen.setLayoutManager(new LinearLayoutManager(this));
		data=new ArrayList<>();
		inputKomen=findViewById(R.id.inputKomen);
		sendKomen=findViewById(R.id.sendKomen);
		sendKomen.setOnClickListener(this);
		AndroidNetworking.initialize(this);
	}
	
	private void get(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/komentar.php?id="+mForum.getId())
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
							data.clear();
							JSONArray ja=json.getJSONArray("data");
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Komentar(jo.getString("nama_lengkap"),
								jo.getString("komentar"),
								jo.getString("create_at")));
							}
							adapter=new ViewForumAdapter(getApplicationContext(),data);
							rvKomen.setAdapter(adapter);
							rvKomen.smoothScrollToPosition(data.size()-1);
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
		JSONObject dt=new JSONObject();
		try{
			dt.put("uuid_users",new Sesi(this).get());
			dt.put("id_forum",mForum.getId());
			dt.put("komentar",inputKomen.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/addKomentar.php")
		.addJSONObjectBody(dt)
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
							data.add(new M_Komentar(new Sesi(getApplicationContext()).getNama(),
							inputKomen.getText().toString(),
							"baru saja"));
							if(data.size()>1){
								adapter.notifyDataSetChanged();
								rvKomen.smoothScrollToPosition(data.size()-1);
							}else{
								adapter=new ViewForumAdapter(getApplicationContext(),data);
								rvKomen.setAdapter(adapter);
								rvKomen.smoothScrollToPosition(data.size()-1);
							}
							inputKomen.setText("");
							totalKomen.setText((Integer.parseInt(totalKomen.getText().toString().replace(" comments",""))+1)+" comments");
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
	public void onClick(View p1)
	{
		// TODO: Implement this method
		if(inputKomen.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(),"wirte a message, please",Toast.LENGTH_SHORT).show();
		}else{
			post();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: 
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
}
