package com.kelasFarmasi.ujian;
import android.os.*;
import com.kelasFarmasi.R;
import android.webkit.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.kelasFarmasi.*;

public class DetailUjian extends AppCompatActivity
{
	WebView web;
	WebSettings set;
	M_Selesai data;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_ujian);
		data=getIntent().getParcelableExtra("data");
		web=findViewById(R.id.wb);
		set=web.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(false);
		web.setWebChromeClient(new WebChromeClient());
		web.setWebViewClient(new WebViewClient(){
			
			public void onReceivedError(WebView webview, int i, String s, String s1){
				Toast.makeText(getApplicationContext(),"Error woi",Toast.LENGTH_LONG).show();
			}
		});
		web.loadUrl("https://www.kelasfarmasi.com/users/api_v1/detailUjian.php?users="+new Sesi(this).get()+"&uu="+data.getUuid()+"&tsu="+data.getSoal()+"&bpu="+data.getBenar()+"&spu="+data.getSalah()+"&rrpu="+data.getRagu()+"&tjpu="+data.getTidak_terjawab()+"&npu="+data.getNilai());
	}
	
}
