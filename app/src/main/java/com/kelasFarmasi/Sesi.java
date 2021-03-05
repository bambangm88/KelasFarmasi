package com.kelasFarmasi;
import android.content.*;

public class Sesi
{
	SharedPreferences sp;
	SharedPreferences.Editor set;
	public Sesi(Context c){
		sp=c.getSharedPreferences("login",c.MODE_PRIVATE);
	}
	
	public void set(String id,
	String nama,
	String kelas,
	String pendidikan,
	String level){
		
		set=sp.edit();
		set.putString("id",id);
		set.putString("nama",nama);
		set.putString("kelas",kelas);
		set.putString("pendidikan",pendidikan);
		set.putString("level",level);
		set.commit();
	}
	
	public String get(){
		return sp.getString("id",null);
	}
	
	public String getNama(){
		return sp.getString("nama",null);
	}
	
	public String getKelas(){
		return sp.getString("kelas",null);
	}
	
	public String getPendidikan(){
		return sp.getString("pendidikan",null);
	}
	
	public String getLevel(){
		return sp.getString("level",null);
	}
	
	public boolean valid(){
		return sp.getString("id",null)!=null?true:false;
	}
	
	public void out(){
		set=sp.edit();
		set.remove("id");
		set.remove("nama");
		set.remove("kelas");
		set.remove("pendidikan");
		set.remove("level");
		set.commit();
	}
}
