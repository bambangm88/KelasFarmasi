package com.kelasFarmasi.video;
import android.os.*;
import android.view.*;
import com.kelasFarmasi.R;

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

public class AddVideo extends AppCompatActivity
{
	Toolbar toolbar;
	ImageView logo;
	EditText title,url,desk;
	Spinner ktg;
	Button btn;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_video);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Add Video");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		title=findViewById(R.id.judulVideo);
		url=findViewById(R.id.urlVideo);
		desk=findViewById(R.id.deskVideo);
		ktg=findViewById(R.id.ktgVideo);
		btn=findViewById(R.id.btnTambahVideo);
		AndroidNetworking.initialize(this);
		btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(title.getText().toString().equals("")||
					   url.getText().toString().equals("")||
					   ktg.getSelectedItemPosition()==0||
					   desk.getText().toString().equals("")){
						Toast.makeText(AddVideo.this,"Lengkapi Form Video", Toast.LENGTH_SHORT).show();
					}else{
						add();
					}
				}
			});
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
	}
	
	private void add(){
		pd.setMessage("Add Video..");
		pd.show();
		JSONObject data=new JSONObject();
		try{
			data.put("judul",title.getText().toString());
			data.put("url",url.getText().toString());
			data.put("kelas",ktg.getSelectedItem().toString());
			data.put("deskripsi",desk.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/add_video.php")
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
							url.setText("");
							ktg.setSelection(0);
							desk.setText("");
							Toast.makeText(getApplicationContext(),"Tambah Video Berhasil",Toast.LENGTH_SHORT).show();
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
		getMenuInflater().inflate(R.menu.menu_video,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case R.id.allVideo:
				Intent i=new Intent(getApplicationContext(),Video.class);
				i.putExtra("kelas","");
				startActivity(i);
				return true;
			case android.R.id.home:
				finish();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}
	
}
