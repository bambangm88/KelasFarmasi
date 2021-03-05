package com.kelasFarmasi.chat;

public class M_Chat
{
	String id,nama,msg,notif;
	public M_Chat(String id,String nama,String msg,String notif){
		this.id=id;
		this.nama=nama;
		this.msg=msg;
		this.notif=notif;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setNama(String nama)
	{
		this.nama = nama;
	}

	public String getNama()
	{
		return nama;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setNotif(String notif)
	{
		this.notif = notif;
	}

	public String getNotif()
	{
		return notif;
	}

}
