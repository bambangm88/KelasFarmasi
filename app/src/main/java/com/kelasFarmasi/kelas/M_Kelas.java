package com.kelasFarmasi.kelas;

public class M_Kelas
{
	String nama,desk;
	public M_Kelas(String nama,String desk){
		this.nama=nama;
		this.desk=desk;
	}

	public void setNama(String nama)
	{
		this.nama = nama;
	}

	public String getNama()
	{
		return nama;
	}

	public void setDesk(String desk)
	{
		this.desk = desk;
	}

	public String getDesk()
	{
		return desk;
	}
	
}
