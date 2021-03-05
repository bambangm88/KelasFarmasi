package com.kelasFarmasi.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasFarmasi.Auth;
import com.kelasFarmasi.R;
import com.kelasFarmasi.Sesi;
import com.kelasFarmasi.kelas.Kelas;
import com.kelasFarmasi.video.Video;

import java.util.ArrayList;

public class Admin extends Fragment implements MenuAdapter.Klik
{
	RecyclerView menu;
	ArrayList<M_Menu> mMenu;
	Sesi sesi;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.admin,container,false);
		menu=v.findViewById(R.id.admin);
		menu.setLayoutManager(new LinearLayoutManager(getActivity()));
		menu.setHasFixedSize(true);
		menu.setAdapter(new MenuAdapter(getActivity(),dataMenu(),Admin.this));
		sesi=new Sesi(getActivity());
		return v;
	}

	private ArrayList<M_Menu> dataMenu(){
		mMenu.add(new M_Menu(R.drawable.kelas,"Kelas"));
		mMenu.add(new M_Menu(R.drawable.ujian_cpd,"Ujian CPD"));
		mMenu.add(new M_Menu(R.drawable.video_belajar,"Video Belajar"));
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
					startActivity(new Intent(getActivity(), Kelas.class));
				}else{
					startActivity(new Intent(getActivity(), Auth.class));
				}		
				break;
			case 1:
				if(sesi.valid()){
					startActivity(new Intent(getActivity(), Video.class));
				}else{
					startActivity(new Intent(getActivity(),Auth.class));
				}		
				break;
		}
	}

}
