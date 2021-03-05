package com.kelasFarmasi.home;
import android.os.*;
import android.view.*;
import com.kelasFarmasi.R;
import java.util.*;
import android.view.View.*;
import android.content.*;
import com.kelasFarmasi.forum.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.ujian.*;
import com.kelasFarmasi.*;
import com.kelasFarmasi.video.*;
import com.kelasFarmasi.kelas.*;

public class Home extends Fragment implements OnClickListener
{
	RecyclerView rvKelas;
	ArrayList<M_Kelas> mKelas;
	LinearLayout mKelass,mUjian,mVideo,mForum;
	Sesi sesi;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.home,container,false);
		mKelass=v.findViewById(R.id.mKelas);
		mKelass.setOnClickListener(this);
		mUjian=v.findViewById(R.id.mUjian);
		mUjian.setOnClickListener(this);
		mVideo=v.findViewById(R.id.mVideo);
		mVideo.setOnClickListener(this);
		mForum=v.findViewById(R.id.mForum);
		mForum.setOnClickListener(this);
		rvKelas=v.findViewById(R.id.rvKelas);
		rvKelas.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
		rvKelas.setHasFixedSize(true);
		//rvKelas.setAdapter(new KelasAdapter(getActivity(),dataKelas()));
		sesi=new Sesi(getActivity());
		return v;
	}

	private ArrayList<M_Kelas> dataKelas(){
		mKelas=new ArrayList<>();
		mKelas.add(new M_Kelas("KELAS TATAP MUKA UKAI","Kelas Bimbingan Intensif Tatap Muka dilaksanakan di Gedung.Anda akan diberikan bimbingan Secara Langsung oleh Mentor Terbaik kami. Anda akan mendapatkan pembelajaran Materi UKAI dan Soal UKAI sesuai dengan blueprint UKAI terbaru serta berbagai tips dan trik dalam menjawab soal ukai."));
		mKelas.add(new M_Kelas("KELAS ONLINE UKAI","Kelas online ini diikuti secara mandiri dengan proses belajar yang dilakukan melalui video conference dan live chat dan Anda akan mengerjakan berbagai paket soal melalui CBT Online, yang terdiri dari soal latihan dan soal Try Out Prediksi UKAI Sumatif."));
		mKelas.add(new M_Kelas("TRYOUT SIMULASI UKAI CBT ONLINE","Fasilitas : Pengerjaan Tryout Online melalui website kelasfarmasi.com 200 Soal dalam 200 Menit Real CBT UKAI Sumatif"));
		return mKelas;
	}
	

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.mKelas:
				break;
			case R.id.mUjian:
				if(sesi.valid()){
					startActivity(new Intent(getActivity(),MainUjian.class));
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
			case R.id.mVideo:
				if(sesi.valid()){
					Intent i=new Intent(getActivity(),Video.class);
					i.putExtra("kelas",sesi.getKelas());
					startActivity(i);
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
			case R.id.mForum:
				if(sesi.valid()){
					startActivity(new Intent(getActivity(),Forum.class));
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
		}
	}
	
}
