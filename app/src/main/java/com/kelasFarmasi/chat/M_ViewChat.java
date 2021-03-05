package com.kelasFarmasi.chat;

public class M_ViewChat
{
	String from,msg,jam;
	public M_ViewChat(String from,String msg,String jam){
		this.from=from;
		this.msg=msg;
		this.jam=jam;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getFrom()
	{
		return from;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getMsg()
	{
		return msg;
	}
	
	public void setJam(String jam){
		this.jam=jam;
	}
	
	public String getJam(){
		return jam;
	}

}
