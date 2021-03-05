package com.kelasFarmasi.video;
import android.os.*;
import com.kelasFarmasi.R;
import android.view.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import com.kelasFarmasi.*;
import java.util.*;
import android.content.*;
import android.view.View.*;
import android.app.AlertDialog;
import android.app.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Video extends AppCompatActivity implements VideoAdapter.OnKlik
{
	Toolbar toolbar;
	ImageView logo;
	RecyclerView rv;
	TextView ksg;
	Sesi sesi;
	ArrayList<M_Video> data;
	VideoAdapter adapter;
	//Dialog
	AlertDialog update;
	AlertDialog.Builder hapus;
	EditText judulVideo,urlVideo,deskVideo;
	String idVideo;
	int indexVideo;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("All Videos");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		rv=findViewById(R.id.rvVideo);
		rv.setLayoutManager(new GridLayoutManager(this,2));
		rv.setHasFixedSize(true);
		ksg=findViewById(R.id.ksgVideo);
		data=new ArrayList<>();
		AndroidNetworking.initialize(this);
		sesi=new Sesi(this);
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
		setDialog();
	}
	
	private void get(){
		ksg.setVisibility(View.VISIBLE);
		ksg.setText("Get Videos...");
		rv.setVisibility(View.GONE);
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/video.php?kelas="+getIntent().getStringExtra("kelas"))
		.setPriority(Priority.LOW)
		.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						if (json.getString("status").equals("200")){
							ksg.setVisibility(View.GONE);
							rv.setVisibility(View.VISIBLE);
							data.clear();
							JSONArray ja=json.getJSONArray("data");
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Video(jo.getString("id_video"),
								jo.getString("judul"),
								jo.getString("kelas"),
								jo.getString("url"),
								jo.getString("deskripsi")));
							}
							adapter=new VideoAdapter(getApplicationContext(),data,Video.this);
							rv.setAdapter(adapter);
						}else{
							ksg.setVisibility(View.VISIBLE);
							ksg.setText("Videos Kosong");
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

	@Override
	public void playVideo(M_Video data)
	{
		// TODO: Implement this method
		Intent i=new Intent(getApplicationContext(),PlayVideo.class);
		i.putExtra("data",data);
		startActivity(i);
	}
	
	
	@Override
	public void toUpdate(M_Video data, int posisi)
	{
		// TODO: Implement this method
		idVideo=data.getId();
		judulVideo.setText(data.getJudul());
		urlVideo.setText(data.getUrl());
		deskVideo.setText(data.getDesk());
		indexVideo=posisi;
		update.show();
	}

	@Override
	public void toDelete(M_Video data, int posisi)
	{
		// TODO: Implement this method
		idVideo=data.getId();
		indexVideo=posisi;
		hapus.show();
	}


	private void setDialog(){
		//update
		update=new AlertDialog.Builder(this).create();
		update.setCancelable(false);
		update.setTitle("Update");
		View v=getLayoutInflater().inflate(R.layout.dialog_video,null);
		judulVideo=v.findViewById(R.id.judulVideo);
		urlVideo=v.findViewById(R.id.urlVideo);
		deskVideo=v.findViewById(R.id.deskVideo);
		Button btn=v.findViewById(R.id.btnUpdateVideo);
		btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(judulVideo.getText().toString().equals("")||
					   urlVideo.getText().toString().equals("")||
					   deskVideo.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(),"Kosong",Toast.LENGTH_SHORT).show();
					}else{
						up();
						update.cancel();
					}
				}
			});
		Button cancel=v.findViewById(R.id.btnCancelVideo);
		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					update.cancel();
				}
			});
		update.setView(v);
		//hapus
		hapus=new AlertDialog.Builder(this);
		hapus.setMessage("Apakah Anda Ingin Menghapus Ini?")
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
				}
			})
			.setPositiveButton("Hapus", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
					dlt();
				}
			});
	}

	private void up(){
		pd.setMessage("Update...");
		pd.show();
		JSONObject datas=new JSONObject();
		try
		{
			datas.put("judul", judulVideo.getText().toString());
			datas.put("url", urlVideo.getText().toString());
			datas.put("deskripsi", deskVideo.getText().toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url) + "api_v1/upVideo.php?id="+idVideo)
			.addJSONObjectBody(datas)
			.setPriority(Priority.MEDIUM)
			.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					pd.cancel();
					try
					{
						if (json.getString("status").equals("200"))
						{
							data.get(indexVideo).setJudul(judulVideo.getText().toString());
							data.get(indexVideo).setUrl(urlVideo.getText().toString());
							data.get(indexVideo).setDesk(deskVideo.getText().toString());
							adapter.notifyDataSetChanged();
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
					pd.cancel();
					Toast.makeText(getApplicationContext(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	private void dlt(){
		pd.setMessage("Delete...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url) + "api_v1/dltVideo.php?id="+idVideo)
			.setPriority(Priority.LOW)
			.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					pd.cancel();
					try
					{
						if (json.getString("status").equals("200"))
						{
							data.remove(indexVideo);
							adapter.notifyItemRemoved(indexVideo);
							adapter.notifyDataSetChanged();
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
					pd.cancel();
					Toast.makeText(getApplicationContext(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: finish(); return true;
			default : return super.onOptionsItemSelected(item);
		}
	}

}
