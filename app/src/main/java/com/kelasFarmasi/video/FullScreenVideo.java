package com.kelasFarmasi.video;
import android.os.*;
import com.kelasFarmasi.R;
import android.webkit.*;
import android.view.*;
import android.content.pm.*;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenVideo extends AppCompatActivity
{
	WebView web;
	WebSettings set;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_video);
		web=findViewById(R.id.video);
		set=web.getSettings();
		set.setJavaScriptEnabled(true);
		web.setWebChromeClient(new WebChromeClient());
		web.setWebViewClient(new WebViewClient());
		web.loadData("<body style='margin : 0 auto; padding : 0;'><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+getIntent().getStringExtra("url").toString().split("v=")[1]+"\" frameborder=\"0\" allowfullscreen></iframe></body>",
					 "text/html",
					 "utf-8");
	}
	
}
