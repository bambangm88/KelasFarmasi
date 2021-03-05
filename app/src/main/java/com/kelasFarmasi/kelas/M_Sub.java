package com.kelasFarmasi.kelas;
import android.os.*;

public class M_Sub implements Parcelable
{
	String id,judul,kelas,harga,fasilitas;
	public M_Sub(String id,String judul,String kelas,String harga,String fasilitas){
		this.id=id;
		this.judul=judul;
		this.harga=harga;
		this.kelas=kelas;
		this.fasilitas=fasilitas;
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

	public void setHarga(String harga)
	{
		this.harga = harga;
	}

	public String getHarga()
	{
		return harga;
	}

	public void setFasilitas(String fasilitas)
	{
		this.fasilitas = fasilitas;
	}

	public String getFasilitas()
	{
		return fasilitas;
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
		p.writeString(id);
		p.writeString(judul);
		p.writeString(kelas);
		p.writeString(harga);
		p.writeString(fasilitas);
	}
	
	protected M_Sub(Parcel p){
		id=p.readString();
		judul=p.readString();
		kelas=p.readString();
		harga=p.readString();
		fasilitas=p.readString();
	}
	
	public static final Creator<M_Sub> CREATOR=new Creator<M_Sub>(){

		@Override
		public M_Sub createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new M_Sub(p1);
		}

		@Override
		public M_Sub[] newArray(int p1)
		{
			// TODO: Implement this method
			return new M_Sub[p1];
		}
	};
	
}
