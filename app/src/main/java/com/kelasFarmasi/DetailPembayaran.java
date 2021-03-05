package com.kelasFarmasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kelasFarmasi.chat.ChatAdapter;
import com.kelasFarmasi.chat.Kontak;
import com.kelasFarmasi.chat.M_Chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class DetailPembayaran extends AppCompatActivity {
    PreLoad preload;
    String nowa = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);



        final Bundle bundle=getIntent().getExtras();


        Button btn_salin_nominal = findViewById(R.id.btn_salin_nominal);
        AndroidNetworking.initialize(this);
        preload=new PreLoad((TextView)findViewById(R.id.preload));

        String isKodePromo = bundle.getString("isKodePromo");
        final String noWa = bundle.getString("noWa");

        rekening();

         if (isKodePromo.equals("Y")){

                String total_pembayaran = bundle.getString("total_pembayaran");
                String jumlah_pembayaran = bundle.getString("jumlah_pembayaran");
                String kode_unik = bundle.getString("kode_unik");
                String diskon_pembayaran = bundle.getString("diskon_pembayaran");

                String kode_promo = bundle.getString("kode_promo");



                TextView _tv_nominal = findViewById(R.id.tv_nominaltransfer);
                TextView _tv_kode_unik = findViewById(R.id.tv_kodeunik);
                TextView _tv_kode_voucher = findViewById(R.id.tv_kodevoucher);
                TextView _tv_diskon = findViewById(R.id.tv_diskon);
                TextView _tv_total = findViewById(R.id.tv_total);


                _tv_nominal.setText(rupiah(jumlah_pembayaran));
                _tv_kode_unik.setText(kode_unik);
                _tv_kode_voucher.setText(kode_promo);
                _tv_total.setText(rupiah(total_pembayaran));
                _tv_diskon.setText(rupiah(diskon_pembayaran));


            }
            else{

             String total_pembayaran = bundle.getString("total_pembayaran");
             String jumlah_pembayaran = bundle.getString("jumlah_pembayaran");
             String kode_unik = bundle.getString("kode_unik");




             TextView _tv_nominal = findViewById(R.id.tv_nominaltransfer);
             TextView _tv_kode_unik = findViewById(R.id.tv_kodeunik);
             TextView _tv_kode_voucher = findViewById(R.id.tv_kodevoucher);
             TextView _tv_diskon = findViewById(R.id.tv_diskon);
             TextView _tv_total = findViewById(R.id.tv_total);


             _tv_nominal.setText(rupiah(jumlah_pembayaran));
             _tv_kode_unik.setText(kode_unik);
             _tv_kode_voucher.setText("-");
             _tv_total.setText(rupiah(total_pembayaran));
             _tv_diskon.setText("-");


         }


        Button btnBukti = findViewById(R.id.uploadBukti);

        btnBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailPembayaran.this,UploadSimpanan.class);
                i.putExtra("uuid", "N");
                startActivity(i);
            }
        });

        btn_salin_nominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String total_pembayaran = bundle.getString("total_pembayaran");
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    clipboardManager.setText(total_pembayaran);
                }
                Toast.makeText(getApplicationContext(), total_pembayaran + " Salin Berhasil", Toast.LENGTH_SHORT).show();
            }
        });







    }


    private String rupiah(String harga){
        NumberFormat rupiah=NumberFormat.getCurrencyInstance(new Locale("in","ID"));
        return "Rp "+rupiah.format((double)Integer.parseInt(harga)).replace("Rp","");
    }

    private void openWA(String noWA) {

        try {

            String number = noWA;
            String url = "https://api.whatsapp.com/send?phone="+number;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            startActivity(i);

        }catch (Exception ex){
            Toast.makeText(DetailPembayaran.this,"Whatsapp tidak ditemukan",Toast.LENGTH_LONG).show();
        }

    }


    private void rekening(){
        preload.loading("Tunggu...");
        JSONObject data=new JSONObject();

        AndroidNetworking.get(getString(R.string.base_url)+"api_v2/Wa.php")
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
                                preload.success("Success");
                                JSONArray ja=json.getJSONArray("data");

                                TextView _tv_no_rek = findViewById(R.id.tv_norek);
                                TextView _tv_nama_rek = findViewById(R.id.tv_namarek);
                                TextView _tv_bank_rek = findViewById(R.id.tv_bankrek);
                                JSONObject jo=ja.getJSONObject(0);
                                _tv_no_rek.setText(jo.getString("no_rekening"));
                                _tv_nama_rek.setText(jo.getString("nama_rekening"));
                                _tv_bank_rek.setText("BANK "+jo.getString("bank_rekening"));
                                nowa = jo.getString("nmr") ;
                                if(ja.length() > 1){
                                    for(int i=0;i<ja.length();i++){

                                        LinearLayout view = findViewById(R.id.view_rek_2);
                                        view.setVisibility(View.VISIBLE);

                                        JSONObject j2=ja.getJSONObject(1);
                                        TextView _tv_no_rek2 = findViewById(R.id.tv_norek2);
                                        TextView _tv_nama_rek2 = findViewById(R.id.tv_namarek2);
                                        TextView _tv_bank_rek2 = findViewById(R.id.tv_bankrek2);
                                        _tv_no_rek2.setText(j2.getString("no_rekening"));
                                        _tv_nama_rek2.setText(j2.getString("nama_rekening"));
                                        _tv_bank_rek2.setText("BANK "+j2.getString("bank_rekening"));

                                    }

                                    Button btn_salin_rekening = findViewById(R.id.btn_salin_rekening2);
                                    btn_salin_rekening.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            TextView _tv_no_rek3 = findViewById(R.id.tv_norek2);
                                            String no_rekening = _tv_no_rek3.getText().toString();
                                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                            if (clipboardManager != null) {
                                                clipboardManager.setText(no_rekening);
                                            }
                                            Toast.makeText(getApplicationContext(), no_rekening + " Salin Berhasil", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                                Button btn_salin_rekening = findViewById(R.id.btn_salin_rekening);
                                btn_salin_rekening.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        TextView _tv_no_rek1 = findViewById(R.id.tv_norek);
                                        String no_rekening = _tv_no_rek1.getText().toString() ;
                                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        if (clipboardManager != null) {
                                            clipboardManager.setText(no_rekening);
                                        }
                                        Toast.makeText(getApplicationContext(), no_rekening + " Salin Berhasil", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Button hubWa = findViewById(R.id.hubungi);
                                hubWa.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        openWA(nowa);
                                    }
                                });

                            }else{
                                finish();
                                preload.failed("Terjadi Gangguan");
                            }
                        }
                        catch (JSONException e)
                        {
                            finish();
                            preload.failed("Error "+e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError p1)
                    {
                        finish();
                        // TODO: Implement this method
                        preload.failed(p1.toString());
                    }
                });
    }

}