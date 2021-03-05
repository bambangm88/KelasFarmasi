package com.kelasFarmasi.home;

public class M_Menu
{
	int img;
	String nama;
	public M_Menu(int img,String nama){
		this.img=img;
		this.nama=nama;
	}

	public void setImg(int img)
	{
		this.img = img;
	}

	public int getImg()
	{
		return img;
	}

	public void setNama(String nama)
	{
		this.nama = nama;
	}

	public String getNama()
	{
		return nama;
	}
}
