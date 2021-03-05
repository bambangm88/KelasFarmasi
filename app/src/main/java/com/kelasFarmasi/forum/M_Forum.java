package com.kelasFarmasi.forum;
import android.os.*;

public class M_Forum implements Parcelable
{
	String id,id_user,nama,ask,jam,komen,type;
	public M_Forum(String id,String id_user,String nama,String ask,String jam,String komen,String type){
		this.id=id;
		this.id_user=id_user;
		this.nama=nama;
		this.ask=ask;
		this.jam=jam;
		this.komen=komen;
		this.type=type;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
	
	public void setIdUser(String id_user)
	{
		this.id_user= id_user;
	}

	public String getIdUser()
	{
		return id_user;
	}

	public void setNama(String nama)
	{
		this.nama = nama;
	}

	public String getNama()
	{
		return nama;
	}

	public void setAsk(String ask)
	{
		this.ask = ask;
	}

	public String getAsk()
	{
		return ask;
	}

	public void setJam(String jam)
	{
		this.jam = jam;
	}

	public String getJam()
	{
		return jam;
	}

	public void setKomen(String komen)
	{
		this.komen = komen;
	}

	public String getKomen()
	{
		return komen;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
	
	@Override
	public int describeContents()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int p2)
	{
		// TODO: Implement this method
		p1.writeString(id);
		p1.writeString(id_user);
		p1.writeString(nama);
		p1.writeString(ask);
		p1.writeString(jam);
		p1.writeString(komen);
		p1.writeString(type);
	}
	
	protected M_Forum(Parcel parcel){
		id=parcel.readString();
		id_user=parcel.readString();
		nama=parcel.readString();
		ask=parcel.readString();
		jam=parcel.readString();
		komen=parcel.readString();
		type=parcel.readString();
	}
	
	public static final Creator<M_Forum> CREATOR=new Creator<M_Forum>(){

		@Override
		public M_Forum createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new M_Forum(p1);
		}

		@Override
		public M_Forum[] newArray(int p1)
		{
			// TODO: Implement this method
			return new M_Forum[p1];
		}

		
	};
	
}
