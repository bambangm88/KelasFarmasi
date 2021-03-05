package com.kelasFarmasi.ujian;

public class M_Urut
{
	String nmr,jwb,ragu;
	public M_Urut(String nmr,String jwb,String ragu){
		this.nmr=nmr;
		this.jwb=jwb;
		this.ragu=ragu;
	}

	public void setNmr(String nmr)
	{
		this.nmr = nmr;
	}

	public String getNmr()
	{
		return nmr;
	}

	public void setJwb(String jwb)
	{
		this.jwb = jwb;
	}

	public String getJwb()
	{
		return jwb;
	}

	public void setRagu(String ragu)
	{
		this.ragu = ragu;
	}

	public String getRagu()
	{
		return ragu;
	}
}
