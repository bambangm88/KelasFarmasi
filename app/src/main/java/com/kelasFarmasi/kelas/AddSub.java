package com.kelasFarmasi.kelas;
import android.os.*;
import com.kelasFarmasi.R;
import android.view.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import android.view.View.*;
import android.app.*;
import android.content.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddSub extends AppCompatActivity
{
	Toolbar toolbar;
	ImageView logo;
	EditText title,harga,fasilitas;
	Spinner ktg;
	Button btn;
	ProgressDialog pd;
	EditText nmr,norek;
	Button btnWa;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sub);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Add Sub Kelas");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		title=findViewById(R.id.judulSub);
		harga=findViewById(R.id.hargaSub);
		fasilitas=findViewById(R.id.fasilitasSub);
		ktg=findViewById(R.id.ktgSub);
		btn=findViewById(R.id.btnTambahSub);
		AndroidNetworking.initialize(this);
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
		btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(title.getText().toString().equals("")||
					harga.getText().toString().equals("")||
					ktg.getSelectedItemPosition()==0||
					fasilitas.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(),"Lengkapi Formulir",Toast.LENGTH_SHORT).show();
					}else{
						add();
					}
				}
			});
		nmr=findViewById(R.id.nmrWa);
		norek=findViewById(R.id.norek);
		btnWa=findViewById(R.id.btnTambahNomor);
		btnWa.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(nmr.getText().toString().equals("")||
					norek.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(),"Lengkapi Formulir",Toast.LENGTH_SHORT).show();
					}else{
						addWa();
					}
				}
			});
	}
	
	private void add(){
		JSONObject data=new JSONObject();
		try
		{
			data.put("judul",title.getText().toString());
			data.put("kelas",ktg.getSelectedItem().toString());
			data.put("harga",harga.getText().toString());
			data.put("fasilitas",fasilitas.getText().toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		pd.setMessage("Add Sub Kelas...");
		pd.show();
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/add_sub.php")
		.addJSONObjectBody(data)
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
							title.setText("");
							ktg.setSelection(0);
							harga.setText("");
							fasilitas.setText("");
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
	
	private void addWa(){
		pd.setMessage("Add Wa...");
		pd.show();
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/Wa.php?wa="+nmr.getText().toString()+"&rek="+norek.getText().toString())
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
							nmr.setText(nmr.getText().toString());
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
	
	
	private void getWa(){
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
							nmr.setText(json.getJSONArray("data").getJSONObject(0).getString("nmr"));
							norek.setText(json.getJSONArray("data").getJSONObject(0).getString("rek"));
						}else{
							nmr.setText("");
							norek.setText("");
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.menu_kelas,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case R.id.allKelas:
				startActivity(new Intent(getApplicationContext(),Kelas.class));
				return true;
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		getWa();
	}
	
}
