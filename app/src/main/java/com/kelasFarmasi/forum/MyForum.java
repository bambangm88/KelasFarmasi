package com.kelasFarmasi.forum;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kelasFarmasi.R;
import com.kelasFarmasi.Sesi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MyForum extends Fragment implements ForumAdapter.OnKlik, View.OnClickListener
{
	String[] dataSp={"Share","Question","Info"};
	Spinner sp;
	EditText input;
	Button post;
	RecyclerView rv;
	ArrayList<M_Forum> data;
	ForumAdapter adapter;
	AlertDialog update;
	AlertDialog.Builder hapus,tanya;
	//Update
	String idUpdate,indexUpdate;
	Spinner spUpdate;
	EditText etUpdate;
	ProgressDialog pd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v=inflater.inflate(R.layout.my_forum,container,false);
		sp=v.findViewById(R.id.spMyForum);
		ArrayAdapter<String> adapterSp=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,dataSp);
		adapterSp.setDropDownViewResource(android.R.layout.simple_list_item_1);
		sp.setAdapter(adapterSp);
		input=v.findViewById(R.id.inputMyForum);
		post=v.findViewById(R.id.postMyForum);
		post.setOnClickListener(this);
		rv=v.findViewById(R.id.rvMyForum);
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		data=new ArrayList<>();
		AndroidNetworking.initialize(getActivity());
		setdialog();
		pd=new ProgressDialog(getActivity());
		pd.setCancelable(false);
		return v;
	}

	private void get(){
		AndroidNetworking.get(getString(R.string.base_url)+"api_v1/forum.php?id="+new Sesi(getActivity()).get())
			.setPriority(Priority.LOW)
			.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						if (json.getString("status").equals("200"))
						{
							JSONArray ja=json.getJSONArray("data");
							data.clear();
							for(int i=0;i<ja.length();i++){
								JSONObject jo=ja.getJSONObject(i);
								data.add(new M_Forum(jo.getString("id_forum"),
								                     jo.getString("uuid_users"),
													 jo.getString("nama_lengkap"),
													 jo.getString("ask"),
													 jo.getString("create_at"),
													 jo.getString("count_coment"),
													 jo.getString("type")));
							}
							adapter=new ForumAdapter(getActivity(),data,MyForum.this);
							rv.setAdapter(adapter);
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
					Toast.makeText(getActivity(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	public void ViewForum(M_Forum data)
	{
		// TODO: Implement this method
		Intent i=new Intent(getActivity(),ViewForum.class);
		i.putExtra("data",data);
		startActivity(i);
	}
	
	@Override
	public void DialogView(M_Forum data, int index)
	{
		// TODO: Implement this method
		idUpdate=data.getId();
		indexUpdate=""+index;
		etUpdate.setText(data.getAsk());
		int posisiSp= Arrays.asList(dataSp).indexOf(data.getType().toString());
		spUpdate.setSelection(posisiSp);
		tanya.show();
	}
	
	private void post(){
		JSONObject dt=new JSONObject();
		try{
			dt.put("uuid_users",new Sesi(getActivity()).get());
			dt.put("ask",input.getText().toString());
			dt.put("type",sp.getSelectedItem().toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url)+"api_v1/addForum.php")
		.addJSONObjectBody(dt)
		.setPriority(Priority.MEDIUM)
		.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					try
					{
						if (json.getString("status").equals("200"))
						{				
							data.add(0,new M_Forum(json.getString("id_forum"),
							                     new Sesi(getActivity()).get(),
												 json.getString("nama_lengkap"),
												 input.getText().toString(),
												 "baru saja",
												 "0",
												 sp.getSelectedItem().toString()));
							if(data.size()>1){
								adapter.notifyDataSetChanged();
							}else{
								adapter=new ForumAdapter(getActivity(),data,MyForum.this);
								rv.setAdapter(adapter);
							}
							input.setText("");
							sp.setSelection(0);
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
				}
			});
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		post();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		// TODO: Implement this method
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			getFragmentManager()
			.beginTransaction()
			.detach(this)
			.attach(this)
			.commit();
		}
	}
	
	

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		get();
	}
	
	private void setdialog(){
		tanya=new AlertDialog.Builder(getActivity());
		tanya.setItems(new CharSequence[]{"Update","Delete"}, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int posisi)
				{
					// TODO: Implement this method
					switch(posisi){
						case 0:
							update.show();
							break;
						case 1:
							hapus.show();
							break;
					}
					p1.cancel();
				}
			});
		//update
		update=new AlertDialog.Builder(getActivity()).create();
		update.setCancelable(false);
		update.setTitle("Update");
		View v=getLayoutInflater().inflate(R.layout.dialog_forum,null);
		spUpdate=v.findViewById(R.id.spAsk);
		ArrayAdapter<String> adapterSpU=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,dataSp);
		adapterSpU.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spUpdate.setAdapter(adapterSpU);
		etUpdate=v.findViewById(R.id.etAsk);
		Button btn=v.findViewById(R.id.btnUpAsk);
		btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(etUpdate.getText().toString().equals("")){
						Toast.makeText(getActivity(),"Kosong",Toast.LENGTH_SHORT).show();
					}else{
						up();
						update.cancel();
					}
				}
			});
		Button cancel=v.findViewById(R.id.btnCancelAsk);
		cancel.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					update.cancel();
				}
			});
		update.setView(v);
		//hapus
		hapus=new AlertDialog.Builder(getActivity());
		hapus.setMessage("Apakah Anda Ingin Menghapus Ini?")
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
				}
			})
			.setPositiveButton("Hapus", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
					dlt();
				}
			});
	}

	private void up(){
		pd.setMessage("Update...");
		pd.show();
		JSONObject datas=new JSONObject();
		try
		{
			datas.put("ask", etUpdate.getText().toString());
			datas.put("type",spUpdate.getSelectedItem().toString());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		AndroidNetworking.post(getString(R.string.base_url) + "api_v1/upForum.php?id="+idUpdate)
		.addJSONObjectBody(datas)
		.setPriority(Priority.MEDIUM)
		.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					pd.cancel();
					try
					{
						if (json.getString("status").equals("200"))
						{
							data.get(Integer.parseInt(indexUpdate)).setAsk(etUpdate.getText().toString());
							data.get(Integer.parseInt(indexUpdate)).setType(spUpdate.getSelectedItem().toString());
							adapter.notifyDataSetChanged();
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
					pd.cancel();
					Toast.makeText(getActivity(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	private void dlt(){
		pd.setMessage("Delete...");
		pd.show();
		AndroidNetworking.get(getString(R.string.base_url) + "api_v1/dltForum.php?id="+idUpdate)
			.setPriority(Priority.LOW)
			.build()
			.getAsJSONObject(new JSONObjectRequestListener(){

				@Override
				public void onResponse(JSONObject json)
				{
					// TODO: Implement this method
					pd.cancel();
					try
					{
						if (json.getString("status").equals("200"))
						{
							data.remove(indexUpdate);
							adapter.notifyDataSetChanged();
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onError(ANError p1)
				{
					// TODO: Implement this method
					pd.cancel();
					Toast.makeText(getActivity(),p1.toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}
}
