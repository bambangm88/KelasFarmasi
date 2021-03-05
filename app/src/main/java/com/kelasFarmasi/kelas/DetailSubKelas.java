package com.kelasFarmasi.kelas;
import android.os.*;
import com.kelasFarmasi.R;
import android.view.View.*;
import android.view.*;
import com.androidnetworking.*;
import com.androidnetworking.interfaces.*;
import com.androidnetworking.common.*;
import org.json.*;
import com.androidnetworking.error.*;
import android.app.*;
import android.icu.text.*;
import java.util.*;
import android.content.pm.*;
import android.content.*;
import android.net.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.*;

public class DetailSubKelas extends AppCompatActivity
{
	Toolbar toolbar;
	ImageView logo;
	TextView judul,harga,fasilitas;
	Button hubungi;
	ProgressDialog pd;
	String wa;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_sub_kelas);
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Detail Sub Kelas");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		M_Sub data=getIntent().getParcelableExtra("data");
		judul=findViewById(R.id.judul);
		judul.setText(data.getJudul());
		harga=findViewById(R.id.harga);
		harga.setText(rupiah(data.getHarga()));
		fasilitas=findViewById(R.id.fasilitas);
		fasilitas.setText(data.getFasilitas());
		hubungi=findViewById(R.id.hubungi);
		hubungi.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					sendMessageToWhatsAppContact("62"+wa.substring(1));
				}
			});
		AndroidNetworking.initialize(this);
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
		getWa();
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
	
	private String rupiah(String harga){
		NumberFormat rupiah=NumberFormat.getCurrencyInstance(new Locale("in","ID"));
		return "Rp "+rupiah.format((double)Integer.parseInt(harga)).replace("Rp","");
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

	private void sendMessageToWhatsAppContact(String number) {
		PackageManager packageManager = getPackageManager();
		Intent i = new Intent(Intent.ACTION_VIEW);
		try {
			String url = "https://api.whatsapp.com/send?phone="+number+"&text="+URLEncoder.encode("Hai admin", "UTF-8");
			i.setPackage("com.whatsapp");
			i.setData(Uri.parse(url));
			if (i.resolveActivity(packageManager) != null) {
				startActivity(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
