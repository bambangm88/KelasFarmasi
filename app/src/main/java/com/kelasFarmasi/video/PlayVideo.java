package com.kelasFarmasi.video;
import android.os.*;
import com.kelasFarmasi.R;
import android.webkit.*;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlayVideo extends AppCompatActivity
{
	WebView web;
	WebSettings set;
	TextView judul,desk;
	Toolbar toolbar;
	ImageView logo;
	Button full;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_video);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getColor(android.R.color.white));
		
		final M_Video data=getIntent().getParcelableExtra("data");
		toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Play Video");
		logo=findViewById(R.id.logo);
		logo.setVisibility(View.GONE);
		web=findViewById(R.id.video);
		set=web.getSettings();
		set.setJavaScriptEnabled(true);
		set.getJavaScriptEnabled();
		web.setWebChromeClient(new WebChromeClient());
		web.setWebViewClient(new WebViewClient()); 
		web.loadData("<body style='margin : 0 auto; padding : 0;'><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+data.getUrl().split("v=")[1]+"\" frameborder=\"0\" allowfullscreen></iframe></body>",
		"text/html",
		"utf-8");
		
		judul=findViewById(R.id.judul);
		desk=findViewById(R.id.deskripsi);
		judul.setText(data.getJudul());
		desk.setText(data.getDesk());
		
		full=findViewById(R.id.playFull);
		full.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent i=new Intent(getApplicationContext(),FullScreenVideo.class);
					i.putExtra("url",data.getUrl());
					startActivity(i);
				}
			});
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home: finish(); return true;
			default : return super.onOptionsItemSelected(item);
		}
	}
}
