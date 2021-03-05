package com.kelasFarmasi;

import android.os.Bundle;
import android.view.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kelasFarmasi.home.*;
import android.content.*;
import com.kelasFarmasi.chat.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.kelasFarmasi.profile.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	public static MainActivity kontek=null;
	Toolbar toolbar;
	BottomNavigationView navigasi;
	Sesi sesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		kontek=this;
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		navigasi=findViewById(R.id.navigasi);
		navigasi.setOnNavigationItemSelectedListener(this);	
		sesi=new Sesi(this);
		/*if(sesi.getLevel()!=null&&(sesi.getLevel().toString().equals("admin")||sesi.getLevel().toString().equals("mentor"))){
			setFragment(new Admin());
		}else{*/
			setFragment(new NewHome());
		//}
    }
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		Fragment fragment=null;
		switch(item.getItemId()){
			case R.id.home: 
				/*if(sesi.getLevel()!=null&&(sesi.getLevel().toString().equals("admin")||sesi.getLevel().toString().equals("mentor"))){
					fragment=new Admin();
				}else{*/
					fragment=new NewHome();
				//}
				break;
			case R.id.chat: 
				if(sesi.valid()){
					fragment=new MainChat(); 
				}else{
					startActivity(new Intent(getApplicationContext(),Auth.class));
				}		 
				break;
			case R.id.profile: 
				if(sesi.valid()){
					fragment=new Profile(); 
				}else{
					startActivity(new Intent(getApplicationContext(),Auth.class));
				}		; 
				break;
		}
		return setFragment(fragment);
	}
	
	private boolean setFragment(Fragment fragment){
		if(fragment!=null){
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.frame,fragment)
			.commit();
			return true;
		}
		return false;
	}
	
	public void toProfile(){
		navigasi.setSelectedItemId(R.id.profile);
	}
}
