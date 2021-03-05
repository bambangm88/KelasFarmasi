package com.kelasFarmasi.profile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kelasFarmasi.Auth;
import com.kelasFarmasi.MainActivity;
import com.kelasFarmasi.R;
import com.kelasFarmasi.Sesi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;

public class Profile extends Fragment implements View.OnClickListener
{
	EditText nama,tgl,email,hp;
	Spinner kelamin,univ,kota;
	Button btn,out;
	String[] sKelamin={"--Pilih Jenis Kelamin--","Laki-Laki","Perempuan"};
	String[] sUniv={"--Pilih Universitas--",
	"Universitas Sumatera Utara",
	"Universitas Tjut Nyak Dhien",
	"Universitas Muslim Nusantara  Alwashliyah",
	"Institut Kesehatan Deli Husada Deli Tua",
	"Institut Kesehatan  Medistra Lubuk Pakam",
	"Institut Teknologi Bandung",
	"Institut Sains dan Teknologi Nasional",
	"Sekolah Tinggi Farmasi Bandung",
	"Sekolah Tinggi Farmasi Semarang",
	"Sekolah Tinggi Farmasi Riau",
	"Sekolah Tinggi Farmasi Cirebon",
	"STFI Bandung",
	"STIFI YP Padang",
	"Universitas 17 Agustus 1945",
	"Universitas Ahmad Dahlan",
	"Universitas Airlangga",
	"Universitas Andalas",
	"Universitas Brawijaya",
	"Universitas Gadjah Mada",
	"Universitas Garut",
	"Universitas Hasanudin",
	"Universitas Indonesia",
	"Universitas Islam Indonesia",
	"Universitas Islam Negeri Syarif Hidayatullah",
	"Universitas Islam Sultan Agung",
	"Universitas Jember",
	"Universitas Jendral Ahmad Yani",
	"Universitas Jendral Sudirman",
	"Universitas Pekalongan",
	"Universitas Kristen Widya Mandala",
	"Universitas Lambung Mangkurat",
	"Universitas Muhammadiyah Malang",
	"Universitas Muhammadiyah Purwokerto",
	"Universitas Muhammadiyah Surakarta",
	"Universitas Muhammadiyah Yogyakarta",
	"Universitas Mulawarman",
	"Universitas Muslim Indonesia",
	"Universitas Padjadjaran",
	"Universitas Pakuan",
	"Universitas Pancasila",
	"Universitas Prof. Hamka",
	"Universitas Sanata Dharma",
	"Universitas Setiabudi",
	"Universitas Sriwijaya",
	"Universitas Sumatera Utara",
	"Universitas Surabaya",
	"Universitas Udayana",
	"Universitas Wahid Hasyim",
	"Universitas Haluoleo Kendari",
	"Universitas Tanjungpura",
	"Lainya"};
	String[] sKota={"--Pilih Kota--",
	"Ambon",
	"Balikpapan",
	"Banda Aceh",
	"Bandar Lampung",
	"Bandung",
	"Banjar",
	"Banjarbaru",
	"Banjarmasin",
	"Batam",
	"Batu",
	"Bau-Bau",
	"Bekasi",
	"Bengkulu",
	"Bima",
	"Binjai",
	"Bitung",
	"Blitar",
	"Bogor",
	"Bontang",
	"Bukittinggi",
	"Cilegon",
	"Cimahi",
	"Cirebon",
	"Denpasar",
	"Depok",
	"Dumai",
	"Gorontalo",
	"Jakarta",
	"Jambi",
	"Jayapura",
	"Kediri",
	"Kendari",
	"Kotamobagu",
	"Kupang",
	"Langsa",
	"Lhokseumawe",
	"Lubuklinggau",
	"Lubuk Pakam",
	"Madiun",
	"Magelang",
	"Makassar",
	"Malang",
	"Manado",
	"Mataram",
	"Medan",
	"Metro",
	"Meulaboh",
	"Mojokerto",
	"Padang",
	"Padang Sidempuan",
	"Padangpanjang",
	"Pagaralam",
	"Palangkaraya",
	"Palembang",
	"Palopo",
	"Palu",
	"Pangkalpinang",
	"Parepare",
	"Pariaman",
	"Pasuruan",
	"Payakumbuh",
	"Pekalongan",
	"Pekanbaru",
	"Pematangsiantar",
	"Pontianak",
	"Prabumulih",
	"Probolinggo",
	"Purwokerto",
	"Sabang",
	"Salatiga",
	"Samarinda",
	"Sawahlunto",
	"Semarang",
	"Serang",
	"Sibolga",
	"Singkawang",
	"Solok",
	"Sorong",
	"Subulussalam",
	"Sukabumi",
	"Sungai Penuh",
	"Surabaya",
	"Surakarta",
	"Tangerang",
	"Tangerang Selatan",
	"Tanjung Balai",
	"Tanjung Pinang",
	"Tarakan",
	"Tasikmalaya",
	"Tebing Tinggi",
	"Tegal",
	"Ternate",
	"Tidore Kepulauan",
	"Tomohon",
	"Tual",
	"Yogyakarta"};
	AlertDialog.Builder logot;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.profile,container,false);
		getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getActivity().getWindow().setStatusBarColor(getActivity().getColor(android.R.color.white));
		
		nama=v.findViewById(R.id.namaUpdate);
		tgl=v.findViewById(R.id.tglUpdate);
		tgl.setOnClickListener(this);
		email=v.findViewById(R.id.emailUpdate);
		hp=v.findViewById(R.id.hpUpdate);
		kelamin=v.findViewById(R.id.kelaminUpdate);
		univ=v.findViewById(R.id.univUpdate);
		kota=v.findViewById(R.id.kotaUpdate);
		btn=v.findViewById(R.id.btnUpdate);
		btn.setOnClickListener(this);
		out=v.findViewById(R.id.btnOut);
		out.setOnClickListener(this);
		logot=new AlertDialog.Builder(getActivity());
		return v;
	}
	
	private void get(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/upProfile.php?id="+new Sesi(getActivity()).get())
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
							JSONObject data=json.getJSONArray("data").getJSONObject(0);
							nama.setText(data.getString("nama_lengkap"));
							tgl.setText(data.getString("tanggal_lahir"));
							email.setText(data.getString("email"));
							hp.setText(data.getString("no_handphone"));
							int pKelamin= Arrays.asList(sKelamin).indexOf(data.getString("jenis_kelamin"));
							kelamin.setSelection(pKelamin<0 ? 0 : pKelamin);
							int pUniv=Arrays.asList(sUniv).indexOf(data.getString("pendidikan_apoteker"));
							univ.setSelection(pUniv<0 ? 0 : pUniv);
							int pKota=Arrays.asList(sKota).indexOf(data.getString("domisili"));
							kota.setSelection(pKota<0 ? 0 : pKota);
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
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}
	
	private void update(){
		JSONObject data=new JSONObject();
		try
		{
			data.put("nama_lengkap", nama.getText().toString());
			data.put("jenis_kelamin",kelamin.getSelectedItem().toString());
			data.put("tanggal_lahir",tgl.getText().toString());
			data.put("email",email.getText().toString());
			data.put("no_handphone",hp.getText().toString());
			data.put("pendidikan_apoteker",univ.getSelectedItem().toString());
			data.put("domisili",kota.getSelectedItem().toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url) + "api_v1/upProfile.php?id=" + new Sesi(getActivity()).get())
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
							Toast.makeText(getActivity(),"Update Berhasil",Toast.LENGTH_SHORT).show();
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
					Toast.makeText(getActivity(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.btnUpdate:
				if(nama.getText().toString().equals("")||
				kelamin.getSelectedItemPosition()==0||
				tgl.getText().toString().equals("")||
				email.getText().toString().equals("")||
			    hp.getText().toString().equals("")||
				univ.getSelectedItemPosition()==0||
				kota.getSelectedItemPosition()==0){
					Toast.makeText(getActivity(),"Lengkapi Formulir",Toast.LENGTH_SHORT).show();
				}else{
					update();
				}
				break;
			case R.id.btnOut:
				logot.setMessage("Apakah anda yakin ingin log out?")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							p1.cancel();
						}
					})
					.setPositiveButton("Log Out", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							new Sesi(getActivity()).out();
							MainActivity.kontek.finish();
							startActivity(new Intent(getActivity(), Auth.class));
							getActivity().finish();
							p1.cancel();												
						}
					}).show();
				break;
			case R.id.tglUpdate:
				Calendar cal= Calendar.getInstance();
				int thn=cal.get(Calendar.YEAR);
				int bln=cal.get(Calendar.MONTH);
				int hari=cal.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog date=new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){

						@Override
						public void onDateSet(DatePicker view, int year, int month, int day)
						{
							// TODO: Implement this method
							tgl.setText(year+"-"+(month+1)+"-"+day);
						}
					},thn,bln,hari);
				date.show();
				break;
		}
	}
	
	
	
}
