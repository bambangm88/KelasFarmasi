package com.kelasFarmasi;
import android.os.*;
import android.util.Log;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.widget.AdapterView.*;

import java.io.Serializable;
import java.util.Calendar;
import android.app.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;
import android.text.*;
import android.content.*;
import android.icu.text.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class Auth extends AppCompatActivity implements OnClickListener
{
	//login
	EditText emailLogin,pwLogin;
	Button btnLogin;
	TextView toDaftar,toReset;
	LinearLayout lnLogin;
	//daftar
	EditText namaDaftar,tglDaftar,emailDaftar,hpDaftar,pwDaftar,pw2Daftar,kodePromo;
	Spinner kelaminDaftar,univDfatar,kotaDaftar,programDaftar,kelasDaftar,subKelasDaftar,ukaiDaftar;
	Button btnDaftar;
	TextView toLogin,pwSalah;
	LinearLayout lnDaftar;
	//Reset
	EditText emailReset;
	Button btnReset;
	TextView resetLogin;
	LinearLayout lnReset;
	
	String[] toUkai={"--Pilih Kelas UKAI--","KELAS TATAP MUKA UKAI","KELAS ONLINE UKAI","TRYOUT ONLINE UKAI"};
	String[] toUkttk={"--Pilih Kelas UKTTK--","KELAS TATAP MUKA UKTTK","KELAS ONLINE UKTTK","TRYOUT ONLINE UKTTK"};
	String[] idSub,isiSub,hargaSub;
	
	PreLoad preload;
	Sesi sesi;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		AndroidNetworking.initialize(this);
		init();
		kelas();
		rePassword(); 
		sesi=new Sesi(this);
	}
	
	private void init(){
		emailLogin=findViewById(R.id.emailLogin);
		pwLogin=findViewById(R.id.pwLogin);
		namaDaftar=findViewById(R.id.namaDaftar);
		tglDaftar=findViewById(R.id.tglDaftar);
		tglDaftar.setOnClickListener(this);
		emailDaftar=findViewById(R.id.emailDaftar);
		hpDaftar=findViewById(R.id.hpDaftar);
		pwDaftar=findViewById(R.id.pwDaftar);
		pw2Daftar=findViewById(R.id.pw2Daftar);
		kodePromo=findViewById(R.id.kodePromo);
		pwSalah=findViewById(R.id.pwSalah);
		kelaminDaftar=findViewById(R.id.kelaminDaftar);
		univDfatar=findViewById(R.id.univDaftar);
		kotaDaftar=findViewById(R.id.kotaDaftar);
		programDaftar=findViewById(R.id.programDaftar);
		kelasDaftar=findViewById(R.id.kelasDaftar);
		subKelasDaftar=findViewById(R.id.subKelasDaftar);
		ukaiDaftar=findViewById(R.id.ukaiDaftar);
		emailReset=findViewById(R.id.emailReset);
		btnLogin=findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnDaftar=findViewById(R.id.btnDaftar);
		btnDaftar.setOnClickListener(this);
		btnReset=findViewById(R.id.btnReset);
		btnReset.setOnClickListener(this);
		toDaftar=findViewById(R.id.toDaftar);
		toDaftar.setOnClickListener(this);
		toLogin=findViewById(R.id.toLogin);
		toLogin.setOnClickListener(this);
		toReset=findViewById(R.id.toReset);
		toReset.setOnClickListener(this);
		resetLogin=findViewById(R.id.resetLogin);
		resetLogin.setOnClickListener(this);
		lnDaftar=findViewById(R.id.lnDaftar);
		lnLogin=findViewById(R.id.lnLogin);
		lnReset=findViewById(R.id.lnReset);
		preload=new PreLoad((TextView)findViewById(R.id.preload));
	}
	
	private void kelas(){
		programDaftar.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
					if(programDaftar.getSelectedItem().toString().equals("BIMBEL UKAI")){
						ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,toUkai);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						kelasDaftar.setAdapter(adapter);
						kelasDaftar.setVisibility(View.VISIBLE);
					}else if(programDaftar.getSelectedItem().toString().equals("BIMBEL UKTTK")){
						ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,toUkttk);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						kelasDaftar.setAdapter(adapter);
						kelasDaftar.setVisibility(View.VISIBLE);
					}else{
						kelasDaftar.setVisibility(View.GONE);
						subKelasDaftar.setVisibility(View.GONE);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}
			});
		kelasDaftar.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int posisi, long p4)
				{
					// TODO: Implement this method
					if(posisi==0){
						subKelasDaftar.setVisibility(View.GONE);
					}else{
						subKelas();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}
			});
	}
	
	private void subKelas(){
		JSONObject data=new JSONObject();
		try{
			Log.e("TAG", "subKelas: "+ kelasDaftar.getSelectedItem().toString() );

			data.put("kelas",kelasDaftar.getSelectedItem().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}

		AndroidNetworking.post(getString(R.string.base_url)+"api_v2/sub_kelas_v2.php?kelas="+kelasDaftar.getSelectedItem().toString())
				.addJSONObjectBody(data)
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
							JSONArray ja=json.getJSONArray("data");
							idSub=new String[ja.length()+1];
							idSub[0]="0";
							isiSub=new String[ja.length()+1];		
							isiSub[0]="--Pilih Sub Kelas--";
							hargaSub=new String[ja.length()+1];
							hargaSub[0]="0";
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								idSub[i+1]=jo.getString("id_subKelas");
								isiSub[i+1]=jo.getString("fasilitas")+" ("+rupiah(jo.getString("harga"))+")";
								hargaSub[i+1]=jo.getString("harga");
							}
							ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,isiSub);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							subKelasDaftar.setAdapter(adapter);
							subKelasDaftar.setVisibility(View.VISIBLE);
						}else{
							subKelasDaftar.setVisibility(View.GONE);
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
					subKelasDaftar.setVisibility(View.GONE);
					Toast.makeText(getApplicationContext(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	private void rePassword(){
		pw2Daftar.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					
				}

				@Override
				public void onTextChanged(CharSequence query, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					if(query.length()>0&&pw2Daftar.getText().toString().equals(pwDaftar.getText().toString())){
						pwSalah.setVisibility(View.GONE);
					}else if(query.length()<=0){
						pwSalah.setVisibility(View.GONE);
					}else if(query.length()>0&&!pw2Daftar.getText().toString().equals(pwDaftar.getText().toString())){
						pwSalah.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}
			});
	}
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.btnDaftar:
				if(namaDaftar.getText().toString().equals("")||
				tglDaftar.getText().toString().equals("")||
				emailDaftar.getText().toString().equals("")||
				hpDaftar.getText().toString().equals("")||
				pwDaftar.getText().toString().equals("")||
				(pw2Daftar.getText().toString().equals("")||
				!pw2Daftar.getText().toString().equals(pwDaftar.getText().toString()))||
				kelaminDaftar.getSelectedItemPosition()==0||
				univDfatar.getSelectedItemPosition()==0||
				kotaDaftar.getSelectedItemPosition()==0||
				programDaftar.getSelectedItemPosition()==0||
				(kelasDaftar.getVisibility()==View.VISIBLE&&
				kelasDaftar.getSelectedItemPosition()==0)||
				(subKelasDaftar.getVisibility()==View.VISIBLE&&
				subKelasDaftar.getSelectedItemPosition()==0)||
				ukaiDaftar.getSelectedItemPosition()==0){
					Toast.makeText(getApplicationContext(),"Lengkapi Form Pendaftaran",Toast.LENGTH_SHORT).show();
				}else{
					Daftar();
				}
				break;
			case R.id.btnLogin:
				if(emailLogin.getText().toString().equals("")||pwLogin.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(),"Lengkapi Form Login",Toast.LENGTH_SHORT).show();			
				}else{
					Login();
				}
				break;
			case R.id.btnReset:
				if(emailReset.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(),"Lengkapi Form Reset Password",Toast.LENGTH_SHORT).show();
				}else{
					Reset();
				}
				break;
			case R.id.toDaftar:
				lnDaftar.setVisibility(View.VISIBLE);
				lnLogin.setVisibility(View.GONE);
				lnReset.setVisibility(View.GONE);
				preload.gone();
				break;
			case R.id.toLogin:
				lnDaftar.setVisibility(View.GONE);
				lnLogin.setVisibility(View.VISIBLE);
				lnReset.setVisibility(View.GONE);
				preload.gone();
				break;
			case R.id.toReset:
				lnDaftar.setVisibility(View.GONE);
				lnLogin.setVisibility(View.GONE);
				lnReset.setVisibility(View.VISIBLE);
				preload.gone();
				break;
			case R.id.resetLogin:
				lnDaftar.setVisibility(View.GONE);
				lnLogin.setVisibility(View.VISIBLE);
				lnReset.setVisibility(View.GONE);
				preload.gone();
				break;
			case R.id.tglDaftar:
				Calendar cal=Calendar.getInstance();
				int thn=cal.get(Calendar.YEAR);
				int bln=cal.get(Calendar.MONTH);
				int hari=cal.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog date=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){

						@Override
						public void onDateSet(DatePicker view, int year, int month, int day)
						{
							// TODO: Implement this method
							tglDaftar.setText(year+"-"+(month+1)+"-"+day);
						}
					},thn,bln,hari);
				date.show();
				break;
		}
	}
	
	private void Login(){
		preload.loading("Login...");
		JSONObject data=new JSONObject();
		try{
			data.put("email",emailLogin.getText().toString());
			data.put("password",pwLogin.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v2/login.php")
		.addJSONObjectBody(data)
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
							JSONObject jo=json.getJSONArray("data").getJSONObject(0);
							    preload.success("Login Berhasil");
								sesi.set(jo.getString("uuid"),
								jo.getString("nama_lengkap"),
								jo.getString("kelas_bimbingan"),
							    jo.getString("pendidikan_apoteker"),
								jo.getString("level"));
								MainActivity.kontek.finish();
								startActivity(new Intent(getApplicationContext(),MainActivity.class));
								finish();
						}else if(json.getString("status").equals("504")){

							//preload.failed(json.getString("data").toString());

							//preload.success(json.getString("msg").toString());

							Log.e("TAG", "onResponse: "+json.toString() );

							String isKodePromo = json.getString("isKodePromo").toString();

							if (isKodePromo.equals("Y")){

								String total_pembayaran = json.getString("total_pembayaran").toString();
								String jumlah_pembayaran = json.getString("jumlah_pembayaran").toString();
								String kode_unik = json.getString("kode_unik").toString();
								String diskon = json.getString("diskon_pembayaran").toString();
								String kode_promo = json.getString("kode_promo").toString();


								Toast.makeText(Auth.this,"Akun Anda Belum Aktif, Silahkan Lakukan Pembayaran",Toast.LENGTH_LONG).show();
								Intent i = new Intent(Auth.this,DetailPembayaran.class);
								i.putExtra("isKodePromo", "Y");
								i.putExtra("total_pembayaran", total_pembayaran);
								i.putExtra("jumlah_pembayaran", jumlah_pembayaran);
								i.putExtra("kode_unik", kode_unik);
								i.putExtra("diskon_pembayaran", diskon);

								i.putExtra("kode_promo", kode_promo);
								startActivity(i);

							}else{

								String total_pembayaran = json.getString("total_pembayaran").toString();
								String jumlah_pembayaran = json.getString("jumlah_pembayaran").toString();
								String kode_unik = json.getString("kode_unik").toString();


								Toast.makeText(Auth.this,"Akun Anda Belum Aktif, Silahkan Lakukan Pembayaran",Toast.LENGTH_LONG).show();
								Intent i = new Intent(Auth.this,DetailPembayaran.class);
								i.putExtra("isKodePromo", "N");
								i.putExtra("total_pembayaran", total_pembayaran);
								i.putExtra("jumlah_pembayaran", jumlah_pembayaran);
								i.putExtra("kode_unik", kode_unik);
								startActivity(i);

							}




						}else{
							preload.failed("Email & Password Salah");
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
					preload.failed(p1.toString());
				}
			});
	}
	
	private void Daftar(){
		preload.loading("Daftar...");
		JSONObject data=new JSONObject();
		try{
			data.put("nama_lengkap",namaDaftar.getText().toString());
			data.put("kodePromo",kodePromo.getText().toString());



			Random rand = new Random();
			// Generate random integers in range 0 to 999
			int kode_unik = rand.nextInt(1000);
			data.put("kode_unik",kode_unik);
			data.put("jenis_kelamin",kelaminDaftar.getSelectedItem().toString());
			data.put("tanggal_lahir",tglDaftar.getText().toString());
			data.put("email",emailDaftar.getText().toString());
			data.put("no_handphone",hpDaftar.getText().toString());
			data.put("pendidikan_apoteker",univDfatar.getSelectedItem().toString());
			data.put("domisili",kotaDaftar.getSelectedItem().toString());
			data.put("program_bimbingan",programDaftar.getSelectedItem().toString());
			if(kelasDaftar.getVisibility()==View.VISIBLE){
				data.put("kelas_bimbingan",kelasDaftar.getSelectedItem().toString());
			}else{
				data.put("kelas_bimbingan","NULL");
			}
			
			if(subKelasDaftar.getVisibility()==View.VISIBLE){
				data.put("subkelas",idSub[subKelasDaftar.getSelectedItemPosition()]);
				data.put("harga_subkelas",hargaSub[subKelasDaftar.getSelectedItemPosition()]);
			}else{
				data.put("subkelas","0");
				data.put("harga_subkelas","0");
			}
			data.put("ikut_ukai",ukaiDaftar.getSelectedItem().toString());
			data.put("password",pwDaftar.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		AndroidNetworking.post(getString(R.string.base_url)+"api_v2/register.php")
			.addJSONObjectBody(data)
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
							//preload.success(json.getString("msg").toString());

							Log.e("TAG", "onResponse: "+json.toString() );

							String isKodePromo = json.getString("isKodePromo").toString();

							if (isKodePromo.equals("Y")){

								String total_pembayaran = json.getString("total_pembayaran").toString();
								String jumlah_pembayaran = json.getString("jumlah_pembayaran").toString();
								String kode_unik = json.getString("kode_unik").toString();
								String diskon = json.getString("diskon_pembayaran").toString();
								String kode_promo = json.getString("kode_promo").toString();


								Toast.makeText(Auth.this,"Pendaftaran Berhasil, Silahkan Lakukan Pembayaran",Toast.LENGTH_LONG).show();
								Intent i = new Intent(Auth.this,DetailPembayaran.class);
								i.putExtra("isKodePromo", "Y");
								i.putExtra("total_pembayaran", total_pembayaran);
								i.putExtra("jumlah_pembayaran", jumlah_pembayaran);
								i.putExtra("kode_unik", kode_unik);
								i.putExtra("diskon_pembayaran", diskon);

								i.putExtra("kode_promo", kode_promo);

								startActivity(i);

							}else{

								String total_pembayaran = json.getString("total_pembayaran").toString();
								String jumlah_pembayaran = json.getString("jumlah_pembayaran").toString();
								String kode_unik = json.getString("kode_unik").toString();


								Toast.makeText(Auth.this,"Pendaftaran Berhasil, Silahkan Lakukan Pembayaran",Toast.LENGTH_LONG).show();
								Intent i = new Intent(Auth.this,DetailPembayaran.class);
								i.putExtra("isKodePromo", "N");
								i.putExtra("total_pembayaran", total_pembayaran);
								i.putExtra("jumlah_pembayaran", jumlah_pembayaran);
								i.putExtra("kode_unik", kode_unik);

								startActivity(i);

							}




						}else if(json.getString("status").equals("404")){
							preload.failed("Email Yang Sama Sudah Terdaftar");
							Toast.makeText(Auth.this,"Email Yang Sama Sudah Terdaftar",Toast.LENGTH_LONG).show();
						}else if(json.getString("status").equals("500")){
							preload.failed("Promo tidak tersedia");
							Toast.makeText(Auth.this,"Promo tidak tersedia",Toast.LENGTH_LONG).show();
							kodePromo.setText("");

						}else{
							preload.failed("Daftar Gagal");
						}
					}
					catch (JSONException e)
					{
						Toast.makeText(Auth.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
						Log.e("TAG", "onResponse: "+e.getLocalizedMessage() );
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
					preload.failed(p1.toString());
					Toast.makeText(Auth.this,p1.getMessage(),Toast.LENGTH_LONG).show();
					Log.e("TAG", "onResponse: "+p1.getErrorDetail() );
				}
			});
	}
	
	private void Reset(){
		preload.loading("Reset...");
		JSONObject data=new JSONObject();
		try{
			data.put("email",emailReset.getText().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/reset_password.php")
			.addJSONObjectBody(data)
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
							preload.success("Password Telah Terkirim ke Email Anda, Silahkan Cek");
						}else if(json.getString("status").equals("403")){
							preload.failed("Password Gagal Terkirim, Silahkan Hubungi Admin");
						}else if(json.getString("status").equals("504")){
							preload.failed("Email Tidak diTemukan Atau Belum Terdaftar");
						}else{
							preload.failed("Password Gagal Terkirim, Silahkan Hubungi Admin");
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
					preload.failed(p1.toString());
				}
			});
	}
	
	private String rupiah(String harga){
		NumberFormat rupiah=NumberFormat.getCurrencyInstance(new Locale("in","ID"));
		return "Rp "+rupiah.format((double)Integer.parseInt(harga)).replace("Rp","");
	}
}
