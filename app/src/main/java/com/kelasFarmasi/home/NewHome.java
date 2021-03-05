package com.kelasFarmasi.home;
import android.view.*;
import android.os.*;
import com.kelasFarmasi.R;

import java.util.*;
import android.util.*;
import com.kelasFarmasi.*;
import android.content.*;
import com.kelasFarmasi.video.*;
import com.kelasFarmasi.ujian.*;
import com.kelasFarmasi.forum.*;
import android.widget.*;
import com.kelasFarmasi.kelas.*;
import android.view.View.*;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kelasFarmasi.profile.*;
import com.androidnetworking.*;
import com.androidnetworking.common.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.error.*;

public class NewHome extends Fragment implements MenuAdapter.Klik,OnClickListener
{
	int[] imgBanner={R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
	ViewPager banner;
	ArrayList<M_Menu> mMenu;
	RecyclerView menu;
	Sesi sesi;
	TextView sapaan1,sapaan2,nama;
	RelativeLayout rltvToProfile;
	//Ujian
	CardView cardUjian;
	TextView tglUjian,selesaiUjian;
	ProgressBar pbUjian;
	Button btnUjian;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.newhome,container,false);
		getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getActivity().getWindow().setStatusBarColor(getActivity().getColor(android.R.color.white));
		
		sesi=new Sesi(getActivity());
		banner=v.findViewById(R.id.banner);
		banner.setAdapter(new BannerPage(getActivity(),imgBanner));
		banner.setPageMargin((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics()));
		mMenu=new ArrayList<>();
		menu=v.findViewById(R.id.menu);
		menu.setLayoutManager(new GridLayoutManager(getActivity(),2));
		menu.setHasFixedSize(true);
		menu.setAdapter(new MenuAdapter(getActivity(),dataMenu(),NewHome.this));
		//Sapa
		nama=v.findViewById(R.id.nama);
		sapaan1=v.findViewById(R.id.sapaan1);
		sapaan2=v.findViewById(R.id.sapaan2);
		rltvToProfile=v.findViewById(R.id.rltvToProfile);
		rltvToProfile.setOnClickListener(this);
		//Ujian
		cardUjian=v.findViewById(R.id.cardUjian);
		tglUjian=v.findViewById(R.id.tglUjian);
		selesaiUjian=v.findViewById(R.id.selesaiUjian);
		pbUjian=v.findViewById(R.id.progressUjian);
		btnUjian=v.findViewById(R.id.lihatUjian);
		btnUjian.setOnClickListener(this);
		if(sesi.valid()){
			sapaan1.setText("Hi");
			nama.setText(sesi.getNama());
			nama.setVisibility(View.VISIBLE);
			sapaan2.setVisibility(View.VISIBLE);
			getUjian();
		}else{
			sapaan1.setText("Silahkan Login");
			nama.setVisibility(View.GONE);
			sapaan2.setVisibility(View.GONE);
			cardUjian.setVisibility(View.GONE);
		}
		return v;
	}
	
	private ArrayList<M_Menu> dataMenu(){
		mMenu.add(new M_Menu(R.drawable.kelas,"Kelas"));
		mMenu.add(new M_Menu(R.drawable.ujian_cpd,"Data Ujian"));
		mMenu.add(new M_Menu(R.drawable.video_belajar,"Materi Video"));
		mMenu.add(new M_Menu(R.drawable.forum,"Forum"));
		return mMenu;
	}

	@Override
	public void toMenu(int posisi)
	{
		// TODO: Implement this method
		switch(posisi){
			case 0:
				if(sesi.valid()){
					if(sesi.getLevel().toString().equals("admin")||
					sesi.getLevel().toString().equals("mentor")){
						startActivity(new Intent(getActivity(),AddSub.class));
					}else{
						startActivity(new Intent(getActivity(),Kelas.class));
					}
				}else{
					startActivity(new Intent(getActivity(),Kelas.class));
				}		
				break;
			case 1:
				if(sesi.valid()){
					if(sesi.getLevel().toString().equals("admin")||
					   sesi.getLevel().toString().equals("mentor")){
						//Menu Untuk Admin
					}else{
						startActivity(new Intent(getActivity(),MainUjian.class));
					}
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
			case 2:
				if(sesi.valid()){
					if(sesi.getLevel().toString().equals("admin")||
					sesi.getLevel().toString().equals("mentor")){
						Intent i=new Intent(getActivity(),AddVideo.class);
						startActivity(i);
					}else{
						Intent i=new Intent(getActivity(),Video.class);
						i.putExtra("kelas",sesi.getKelas());
						startActivity(i);
					}
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
			case 3:
				if(sesi.valid()){
					startActivity(new Intent(getActivity(),Forum.class));
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
		}
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.rltvToProfile:
				if(sesi.valid()){
					getFragmentManager()
					.beginTransaction()
					.replace(R.id.frame,new Profile())
					.commit();
					MainActivity.kontek.toProfile();
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}
				break;
			case R.id.lihatUjian:
				startActivity(new Intent(getActivity(),MainUjian.class));
			break;
		}
	}
	
	private void getUjian(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/notifUjian.php?id="+sesi.get())
		.setPriority(Priority.LOW)
		.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						if(json.getString("status").equals("200")){
							tglUjian.setText(json.getString("tanggal"));
							selesaiUjian.setText("selesai : "+json.getString("selesai")+"/"+json.getString("belum"));
							pbUjian.setMax(Integer.parseInt(json.getString("belum")));
							pbUjian.setProgress(Integer.parseInt(json.getString("selesai")));
							cardUjian.setVisibility(View.VISIBLE);
						}else{
							cardUjian.setVisibility(View.GONE);
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
					cardUjian.setVisibility(View.GONE);
				}
			});
	}

}
