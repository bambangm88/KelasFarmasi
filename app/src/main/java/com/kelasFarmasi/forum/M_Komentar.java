package com.kelasFarmasi.forum;

public class M_Komentar
{
	String nama,komentar,jam;
	public M_Komentar(String nama,String komentar,String jam){
		this.nama=nama;
		this.komentar=komentar;
		this.jam=jam;
	}

	public void setNama(String nama)
	{
		this.nama = nama;
	}

	public String getNama()
	{
		return nama;
	}

	public void setKomentar(String komentar)
	{
		this.komentar = komentar;
	}

	public String getKomentar()
	{
		return komentar;
	}

	public void setJam(String jam)
	{
		this.jam = jam;
	}

	public String getJam()
	{
		return jam;
	}
}
