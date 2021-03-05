package com.kelasFarmasi.video;
import android.os.*;

public class M_Video implements Parcelable
{
	String id,judul,kelas,url,desk;
	public M_Video(String id,String judul,String kelas,String url,String desk){
		this.id=id;
		this.judul=judul;
		this.kelas=kelas;
		this.url=url;
		this.desk=desk;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
	

	public void setJudul(String judul)
	{
		this.judul = judul;
	}

	public String getJudul()
	{
		return judul;
	}

	public void setKelas(String kelas)
	{
		this.kelas = kelas;
	}

	public String getKelas()
	{
		return kelas;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setDesk(String desk)
	{
		this.desk = desk;
	}

	public String getDesk()
	{
		return desk;
	}

	@Override
	public int describeContents()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int p2)
	{
		// TODO: Implement this method
		p.writeString(judul);
		p.writeString(kelas);
		p.writeString(url);
		p.writeString(desk);
	}
	
	protected M_Video(Parcel p){
		judul=p.readString();
		kelas=p.readString();
		url=p.readString();
		desk=p.readString();
	}
	
	public static final Creator<M_Video> CREATOR=new Creator<M_Video>(){

		@Override
		public M_Video createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new M_Video(p1);
		}

		@Override
		public M_Video[] newArray(int p1)
		{
			// TODO: Implement this method
			return new M_Video[p1];
		}
	};
}
