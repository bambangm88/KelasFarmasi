package com.kelasFarmasi.ujian;
import android.os.*;
import com.kelasFarmasi.R;
import com.androidnetworking.*;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.*;
import com.androidnetworking.error.*;
import org.json.*;
import com.kelasFarmasi.*;
import android.app.AlertDialog;
import android.view.*;
import java.util.*;
import java.util.concurrent.*;
import android.view.View.*;
import android.app.*;
import android.content.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.*;

public class ViewUjian extends AppCompatActivity implements UrutAdapter.OnKlik,OnClickListener
{
	Toolbar toolbar;
	ImageView logo;
	RecyclerView rv;
	ArrayList<M_Urut> dataUrut;
	TextView nmr,txtSoal;
	ImageView imgSoal;
	RadioGroup group_jawab;
	RadioButton a,b,c,d,e;
	CheckBox ragu;
	M_Belum data;
	Sesi sesi;
	ProgressDialog pd;
	String nmr_pengerjaan;
	UrutAdapter adapter;
	CountDownTimer countDown;
	Button btnSimpan,btnBatal;
	AlertDialog.Builder selesaiUjian;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_ujian);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		data=getIntent().getParcelableExtra("data");
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		rv=findViewById(R.id.urutUjian);
		rv.setLayoutManager(new GridLayoutManager(this,5));
		rv.setHasFixedSize(true);
		rv.setNestedScrollingEnabled(false);
		dataUrut=new ArrayList<>();
		nmr=findViewById(R.id.nmrUjian);
		txtSoal=findViewById(R.id.txtSoalUjian);
		imgSoal=findViewById(R.id.imgSoalUjian);
		group_jawab=findViewById(R.id.groupJawab);
		a=findViewById(R.id.a);
		b=findViewById(R.id.b);
		c=findViewById(R.id.c);
		d=findViewById(R.id.d);
		e=findViewById(R.id.e);
		ragu=findViewById(R.id.ragu);
		btnSimpan=findViewById(R.id.btnSimpan);
		btnSimpan.setOnClickListener(this);
		btnBatal=findViewById(R.id.btnBatal);
		btnBatal.setOnClickListener(this);
		sesi=new Sesi(this);
		pd=new ProgressDialog(this);
		pd.setCancelable(false);
		get("1");
		start(data.getWaktu());
		selesaiUjian=new AlertDialog.Builder(this);
	}
	
	private void get(String urut){
		pd.setMessage("Loading...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/mulaiUjian.php?users="+sesi.get()+"&ujian="+data.getUuid()+"&soal="+data.getSoal()+"&nomor="+urut+"&random="+data.getRandom())
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
						//Awal Soal
						JSONObject dataSoal=json.getJSONArray("soal").getJSONObject(0);
						nmr.setText(dataSoal.getString("nomor_pengerjaan")+".)");
						txtSoal.setText(dataSoal.getString("soal"));
						nmr_pengerjaan=dataSoal.getString("nomor_pengerjaan");
						if(dataSoal.getString("gambar").toString().equals("null")||dataSoal.getString("gambar").toString().equals("")){
							imgSoal.setVisibility(View.GONE);
						}else{
							imgSoal.setVisibility(View.VISIBLE);
							Glide.with(getApplicationContext())
							.load(getString(R.string.base_url)+"data/soalujian/"+dataSoal.getString("gambar").toString())
							.into(imgSoal);
							//Idpslay img
						}		
						a.setText("(A.) "+dataSoal.getString("a"));
						b.setText("(B.) "+dataSoal.getString("b"));
						c.setText("(C.) "+dataSoal.getString("c"));
						d.setText("(D.) "+dataSoal.getString("d"));
						e.setText("(E.) "+dataSoal.getString("e"));
						switch(dataSoal.getString("jawab").toString()){
							case "g": group_jawab.clearCheck(); break;
							case "a": group_jawab.clearCheck();  a.setChecked(true); break;
							case "b": group_jawab.clearCheck();  b.setChecked(true); break;
							case "c": group_jawab.clearCheck();  c.setChecked(true); break;
							case "d": group_jawab.clearCheck();  d.setChecked(true); break;
							case "e": group_jawab.clearCheck();  e.setChecked(true); break;
						}
						if(dataSoal.getString("is_raguragu").toString().equals("0")){
							ragu.setChecked(false);
						}else{
							ragu.setChecked(true);
						}
						//Akhir Soal
						
						//Nomor Urut
						JSONArray jaUrur=json.getJSONArray("nomor");
						dataUrut.clear();
						for(int i=0;i<jaUrur.length();i++){
							JSONObject joUrut=jaUrur.getJSONObject(i);
							dataUrut.add(new M_Urut(joUrut.getString("nomor_pengerjaan"),
							joUrut.getString("jawab"),
							joUrut.getString("is_raguragu")));
						}
						adapter=new UrutAdapter(getApplicationContext(),dataUrut,nmr_pengerjaan,ViewUjian.this);
						rv.setAdapter(adapter);
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
				}
			});
	}

	@Override
	public void next(String urut)
	{
		// TODO: Implement this method
		get(urut);
	}
	
	void start(String m){
		int menit=Integer.parseInt(m)*60*1000; //menit to millisecond
		countDown = new CountDownTimer(menit,1000){

			@Override
			public void onTick(long p1)
			{
				// TODO: Implement this method
				String data=String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(p1),
										  TimeUnit.MILLISECONDS.toMinutes(p1)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(p1)),
										  TimeUnit.MILLISECONDS.toSeconds(p1)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(p1)));
				setTitle(data);
			}

			@Override
			public void onFinish()
			{
				// TODO: Implement this method
				setTitle("Waktu Habis!");
				countDown=null;
			}
		}.start();
	}

	void stop(){
		if(countDown!=null){
			countDown.cancel();
			countDown=null;
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.menu_ujian,menu);
		return true;
	}*/


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: 
				finish();
		    	return true;
			default : return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		stop();
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.btnBatal:
				group_jawab.clearCheck();
				ragu.setChecked(false);
				break;
			case R.id.btnSimpan:
				if(Integer.parseInt(data.getSoal())==Integer.parseInt(nmr_pengerjaan)){
					selesaiUjian.setMessage("Apakah Anda Yakin Ingin Menyelesaikan Ujian Ini?")
					.setCancelable(false)
						.setPositiveButton("Ya!", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								save();
								p1.cancel();
							}
						})
						.setNegativeButton("Tidak!", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								p1.cancel();
							}
						})
						.show();
				}else{
					save();
				}
				break;
		}
	}
	
	private String jawaban(){
		String jwb="";
		switch(group_jawab.getCheckedRadioButtonId()){
			case R.id.a:
				jwb="a";
				break;
			case R.id.b:
				jwb="b";
				break;
			case R.id.c:
				jwb="c";
				break;
			case R.id.d:
				jwb="d";
				break;
			case R.id.e:
				jwb="e";
				break;
			case -1:
				jwb="g";
				break;
		}
		return jwb;
	}

	private String jwbRagu(){
		String jwbRagu="";
		if(ragu.isChecked()){
			jwbRagu="1";
		}else{
			jwbRagu="0";
		}
		return jwbRagu;
	}
	
	private void save(){
		pd.setMessage("Simpan Jawaban...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/simpanUjian.php?users="+sesi.get()+"&ujian="+data.getUuid()+"&nomor="+nmr_pengerjaan+"&jawab="+jawaban()+"&ragu="+jwbRagu())
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
							if(Integer.parseInt(data.getSoal())==Integer.parseInt(nmr_pengerjaan)){
								selesai();
							}else{
								get(""+(Integer.parseInt(nmr_pengerjaan)+1));
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
					pd.cancel();
					Toast.makeText(getApplicationContext(),p1.toString(), Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	private void selesai(){
		pd.setMessage("Akhiri Ujian...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/akhirUjian.php?users="+sesi.get()+"&ujian="+data.getUuid())
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
							Toast.makeText(getApplicationContext(),"Data Tersimpan",Toast.LENGTH_SHORT).show();
							finish();
						}else{
							Toast.makeText(getApplicationContext(),"Data Ujian Tidak Ada",Toast.LENGTH_SHORT).show();
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
