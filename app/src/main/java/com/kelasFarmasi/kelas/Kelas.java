package com.kelasFarmasi.kelas;
import android.os.*;
import com.kelasFarmasi.R;
import java.util.*;
import com.kelasFarmasi.home.*;
import android.view.*;
import android.content.*;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Kelas extends AppCompatActivity implements KelasAdapter.Klik
{
	Toolbar toolbar;
	ImageView logo;
	RecyclerView kelas;
	ArrayList<M_Kelas> mKelas;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kelas);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
	
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		kelas=findViewById(R.id.kelas);
		kelas.setLayoutManager(new LinearLayoutManager(this));
		kelas.setHasFixedSize(true);
		kelas.setAdapter(new KelasAdapter(this,dataKelas(),Kelas.this));
	}
	
	private ArrayList<M_Kelas> dataKelas(){
		mKelas=new ArrayList<>();
		mKelas.add(new M_Kelas("KELAS TATAP MUKA UKAI","Kelas Bimbingan Intensif Tatap Muka dilaksanakan di Gedung.Anda akan diberikan bimbingan Secara Langsung oleh Mentor Terbaik kami. Anda akan mendapatkan pembelajaran Materi UKAI dan Soal UKAI sesuai dengan blueprint UKAI terbaru serta berbagai tips dan trik dalam menjawab soal ukai."));
		mKelas.add(new M_Kelas("KELAS ONLINE UKAI","Kelas online ini diikuti secara mandiri dengan proses belajar yang dilakukan melalui video conference dan live chat dan Anda akan mengerjakan berbagai paket soal melalui CBT Online, yang terdiri dari soal latihan dan soal Try Out Prediksi UKAI Sumatif."));
		mKelas.add(new M_Kelas("TRYOUT SIMULASI UKAI CBT ONLINE","Fasilitas : Pengerjaan Tryout Online melalui website kelasfarmasi.com 200 Soal dalam 200 Menit Real CBT UKAI Sumatif"));
		return mKelas;
	}

	@Override
	public void openSub(String kelas)
	{
		// TODO: Implement this method
		Intent i=new Intent(getApplicationContext(),SubKelas.class);
		i.putExtra("kelas",kelas);
		startActivity(i);
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
