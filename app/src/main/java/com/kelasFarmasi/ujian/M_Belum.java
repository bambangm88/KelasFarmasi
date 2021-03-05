package com.kelasFarmasi.ujian;
import android.os.*;

public class M_Belum implements Parcelable
{
	String uuid,tipe,judul,soal,waktu,periode,random,status;
	public M_Belum(String uuid,String tipe,String judul,String soal,String waktu,String periode,String random,String status){
		this.uuid=uuid;
		this.tipe=tipe;
		this.judul=judul;
		this.soal=soal;
		this.waktu=waktu;
		this.periode=periode;
		this.random=random;
		this.status=status;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setTipe(String tipe)
	{
		this.tipe = tipe;
	}

	public String getTipe()
	{
		return tipe;
	}

	public void setJudul(String judul)
	{
		this.judul = judul;
	}

	public String getJudul()
	{
		return judul;
	}

	public void setSoal(String soal)
	{
		this.soal = soal;
	}

	public String getSoal()
	{
		return soal;
	}

	public void setWaktu(String waktu)
	{
		this.waktu = waktu;
	}

	public String getWaktu()
	{
		return waktu;
	}

	public void setPeriode(String periode)
	{
		this.periode = periode;
	}

	public String getPeriode()
	{
		return periode;
	}

	public void setRandom(String random)
	{
		this.random = random;
	}

	public String getRandom()
	{
		return random;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
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
		p.writeString(uuid);
		p.writeString(tipe);
		p.writeString(judul);
		p.writeString(soal);
		p.writeString(waktu);
		p.writeString(periode);
		p.writeString(random);
		p.writeString(status);
	}
	
	protected M_Belum(Parcel p){
		uuid=p.readString();
		tipe=p.readString();
		judul=p.readString();
		soal=p.readString();
		waktu=p.readString();
		periode=p.readString();
		random=p.readString();
		status=p.readString();
	}
	
	public static final Creator<M_Belum> CREATOR=new Creator<M_Belum>(){

		@Override
		public M_Belum createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new M_Belum(p1);
		}

		@Override
		public M_Belum[] newArray(int p1)
		{
			// TODO: Implement this method
			return new M_Belum[p1];
		}
	};
}
