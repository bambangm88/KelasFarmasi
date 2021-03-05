package com.kelasFarmasi.kelas;
import android.os.*;
import com.kelasFarmasi.R;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import java.util.*;
import android.app.AlertDialog;
import android.view.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import java.net.*;
import android.app.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubKelas extends AppCompatActivity implements SubAdapter.Klik
{
	Toolbar toolbar;
	ImageView logo;
	RecyclerView subKelas;
	ArrayList<M_Sub> mSub;
	ProgressDialog pd;
	String wa;
	SubAdapter adapter;
	//Dialog
	AlertDialog update;
	AlertDialog.Builder hapus;
	EditText judulSub,hargaSub,fasilitasSub;
	String idSub;
	int indexSub;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_kelas);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(getIntent().getStringExtra("kelas"));
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		subKelas=findViewById(R.id.subkelas);
		subKelas.setLayoutManager(new LinearLayoutManager(this));
		subKelas.setHasFixedSize(true);
		mSub=new ArrayList<>();
		AndroidNetworking.initialize(this);
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
		getWa();
		setDialog();
	}
	
	private void get(){
		pd.setMessage("Get Sub Kelas...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/SubKelas.php?kelas="+getIntent().getStringExtra("kelas"))
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
							mSub.clear();
							JSONArray ja=json.getJSONArray("data");
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								mSub.add(new M_Sub(jo.getString("id_subKelas"),
								jo.getString("judul"),
								jo.getString("kelas"),
								jo.getString("harga"),
								jo.getString("fasilitas")));
							}
							adapter=new SubAdapter(getApplicationContext(),mSub,SubKelas.this);
							subKelas.setAdapter(adapter);
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
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}

	@Override
	public void toDetail(M_Sub data)
	{
		// TODO: Implement this method
		/*Intent i=new Intent(getApplicationContext(),DetailSubKelas.class);
		i.putExtra("data",data);
		startActivity(i);*/
		sendMessageToWhatsAppContact("62"+wa.substring(1),data.getKelas(),data.getJudul());
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
	
	private void getWa(){
		pd.setMessage("Get Data...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/Wa.php")
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
							wa=json.getJSONArray("data").getJSONObject(0).getString("nmr").toString();
						}else{
							wa="";
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
	private void sendMessageToWhatsAppContact(String number,String msg,String sub) {
		PackageManager packageManager = getPackageManager();
		Intent i = new Intent(Intent.ACTION_VIEW);
		try {
			String url = "https://api.whatsapp.com/send?phone="+number+"&text="+URLEncoder.encode("Hai admin,\nSaya ingin bertanya tentang\n*"+msg+"*\n_("+sub+")_", "UTF-8");
			i.setPackage("com.whatsapp");
			i.setData(Uri.parse(url));
			if (i.resolveActivity(packageManager) != null) {
				startActivity(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toUpdate(M_Sub data, int posisi)
	{
		// TODO: Implement this method
		idSub=data.getId();
		judulSub.setText(data.getJudul());
		hargaSub.setText(data.getHarga());
		fasilitasSub.setText(data.getFasilitas());
		indexSub=posisi;
		update.show();
	}

	@Override
	public void toDelete(M_Sub data, int posisi)
	{
		// TODO: Implement this method
		idSub=data.getId();
		indexSub=posisi;
		hapus.show();
	}

	
	private void setDialog(){
		//update
		update=new AlertDialog.Builder(this).create();
		update.setCancelable(false);
		update.setTitle("Update");
		View v=getLayoutInflater().inflate(R.layout.dialog_sub,null);
		judulSub=v.findViewById(R.id.judulSub);
		hargaSub=v.findViewById(R.id.hargaSub);
		fasilitasSub=v.findViewById(R.id.fasilitasSub);
		Button btn=v.findViewById(R.id.btnUpdateSub);
		btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(judulSub.getText().toString().equals("")||
					   hargaSub.getText().toString().equals("")||
					   fasilitasSub.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(),"Kosong",Toast.LENGTH_SHORT).show();
					}else{
						up();
						update.cancel();
					}
				}
			});
		Button cancel=v.findViewById(R.id.btnCancelSub);
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
			datas.put("judul", judulSub.getText().toString());
			datas.put("harga", hargaSub.getText().toString());
			datas.put("fasilitas", fasilitasSub.getText().toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url) + "api_v1/upKelas.php?id="+idSub)
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
							mSub.get(indexSub).setJudul(judulSub.getText().toString());
							mSub.get(indexSub).setHarga(hargaSub.getText().toString());
							mSub.get(indexSub).setFasilitas(fasilitasSub.getText().toString());
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
		AndroidNetworking.get(getString(R.string.base_url) + "api_v1/dltKelas.php?id="+idSub)
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
							mSub.remove(indexSub);
							adapter.notifyItemRemoved(indexSub);
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
	

}
