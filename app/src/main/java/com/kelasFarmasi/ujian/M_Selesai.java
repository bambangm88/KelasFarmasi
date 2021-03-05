package com.kelasFarmasi.ujian;
import android.os.*;

public class M_Selesai implements Parcelable
{
	String uuid,tipe,judul,soal,waktu,benar,salah,ragu,tidak_terjawab,nilai;
	public M_Selesai(String uuid,String tipe,String judul,String soal,String waktu,String benar,String salah,String ragu,String tidak_terjawab,String nilai){
		this.uuid=uuid;
		this.tipe=tipe;
		this.judul=judul;
		this.soal=soal;
		this.waktu=waktu;
		this.benar=benar;
		this.salah=salah;
		this.ragu=ragu;
		this.tidak_terjawab=tidak_terjawab;
		this.nilai=nilai;
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

	public void setBenar(String benar)
	{
		this.benar = benar;
	}

	public String getBenar()
	{
		return benar;
	}

	public void setSalah(String salah)
	{
		this.salah = salah;
	}

	public String getSalah()
	{
		return salah;
	}

	public void setRagu(String ragu)
	{
		this.ragu = ragu;
	}

	public String getRagu()
	{
		return ragu;
	}

	public void setTidak_terjawab(String tidak_terjawab)
	{
		this.tidak_terjawab = tidak_terjawab;
	}

	public String getTidak_terjawab()
	{
		return tidak_terjawab;
	}

	public void setNilai(String nilai)
	{
		this.nilai = nilai;
	}

	public String getNilai()
	{
		return nilai;
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
		p.writeString(benar);
		p.writeString(salah);
		p.writeString(ragu);
		p.writeString(tidak_terjawab);
		p.writeString(nilai);
	}
	
	protected M_Selesai(Parcel p){
		uuid=p.readString();
		tipe=p.readString();
		judul=p.readString();
		soal=p.readString();
		waktu=p.readString();
		benar=p.readString();
		salah=p.readString();
		ragu=p.readString();
		tidak_terjawab=p.readString();
		nilai=p.readString();
	}
	
	public static final Creator<M_Selesai> CREATOR=new Creator<M_Selesai>(){

		@Override
		public M_Selesai createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new M_Selesai(p1);
		}

		@Override
		public M_Selesai[] newArray(int p1)
		{
			// TODO: Implement this method
			return new M_Selesai[p1];
		}	
	};
}
