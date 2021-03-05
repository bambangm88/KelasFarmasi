package com.kelasFarmasi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadSimpanan extends AppCompatActivity {


    private LinearLayout simpananSukarela ;

    private ImageView iv_buktitrf ;
    PreLoad preload;
    private RelativeLayout rlprogress , rlprogressLoading;
    public static String chooseUpload = "";

    public static String chooseUpload_set = "";

    public static Bitmap bitmapUpload ;

    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    ProgressDialog pDialog;
    private int GALLERY = 1, CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpanan_sukarela);



        AndroidNetworking.initialize(this);
        final EditText et_jumlah = findViewById(R.id.et_jumlah) ;
        final EditText et_nama = findViewById(R.id.et_nama_pengirim) ;
        final EditText et_trf = findViewById(R.id.et_trf) ;
        final Spinner sp_bank = findViewById(R.id.bankPenerima) ;
        iv_buktitrf = findViewById(R.id.iv_bukti_trf) ;
        Button btn_simpan = findViewById(R.id.btn_simpan) ;
        //Button cara_pembayaran = findViewById(R.id.cara_pembayaran) ;
        final Bundle bundle=getIntent().getExtras();
//        String isKodePromo = bundle.getString("isKodePromo");
        preload=new PreLoad((TextView)findViewById(R.id.preload));


        et_trf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takePhotoFromCamera();
                showDateDialog(UploadSimpanan.this,et_trf,"yyyy-MM-dd hh:mm:ss");
            }
        });

        iv_buktitrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseUpload = "4" ;
                showPictureDialog();
              //  startActivity(new Intent(UploadSimpanan.this, Foto_Bukti_Transfer.class));
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imageString = "" ;
                if (iv_buktitrf.getTag() == "ada"  ){
                    imageString = getStringImage(imageView2Bitmap(iv_buktitrf));
                }

                String _et_jumlah = et_jumlah.getText().toString() ;
                String _et_nama_pengirim = et_nama.getText().toString() ;
                String _sp_bank= sp_bank.getSelectedItem().toString();


                if(_sp_bank.equals("--Bank Penerima--")){
                    Toast.makeText(UploadSimpanan.this,"Silahkan Pilih Bank Penerima", Toast.LENGTH_LONG).show();
                    return;
                }

                if(_et_nama_pengirim.equals("")){
                    Toast.makeText(UploadSimpanan.this,"Silahkan Masukan Nama Pengirim", Toast.LENGTH_LONG).show();
                    return;
                }


                if(et_jumlah.getText().toString().equals("")){
                    _et_jumlah = "0" ;
                }

                if(Integer.parseInt(_et_jumlah) == 0){
                    Toast.makeText(UploadSimpanan.this,"Silahkan Masukan Jumlah Transfer", Toast.LENGTH_LONG).show();
                    return;
                }

                if(et_trf.getText().toString().equals("")){
                    Toast.makeText(UploadSimpanan.this,"Silahkan Masukan Tanggal", Toast.LENGTH_LONG).show();
                    return;
                }


                upload(imageString,_et_jumlah,et_trf.getText().toString(),_et_nama_pengirim,_sp_bank);

            }
        });








    }

    /*

    public void DoSimpanaan(JsonSimpananSukarela json, Context context){

        showProgress(true);
        Call<ResponseRegister> call = API.addSimpananSukarela(json);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.isSuccessful()) {
                    showProgress(false);

                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage();
                        String status = response.body().getMetadata().getCode();

                        if (status.equals(Constant.ERR_200)) {
                            showProgress(false);

                            new KAlertDialog(UploadSimpanan.this, KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Notification")
                                    .setContentText("Upload Bukti Simpanan Berhasil")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                        @Override
                                        public void onClick(KAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                            startActivity(new Intent(UploadSimpanan.this, Login.class));
                                        }
                                    })
                                    .show();


                        }else{
                            showProgress(false);
                            // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            Helper.notifikasi_warning(message,context);

                        }

                    }else{
                        showProgress(false);
                        Helper.notifikasi_warning("Terjadi Gangguan",context);
                    }

                }else{
                    showProgress(false);
                    Helper.notifikasi_warning("Terjadi Gangguan",context);
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                showProgress(false);
                Helper.notifikasi_warning("Terjadi Gangguan Pada Server",context);
            }
        });
    }

    */

    private void upload(String image,String jumlah , String tgl , String nama , String bank){
        preload.loading("Mengupload Bukti Pembayaran...");
        JSONObject data=new JSONObject();
        try{
            data.put("image",image);
            data.put("jumlah",jumlah);
            data.put("tgl_trf",tgl);
            data.put("nama_pengirim",nama);
            data.put("bank_penerima",bank);
        }catch(JSONException e){
            e.printStackTrace();
        }
        AndroidNetworking.post(getString(R.string.base_url)+"api_v1/upload_pembayaran.php")
                .addJSONObjectBody(data)
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
                                preload.success("Upload Pembayaran Berhasil");
                            }else {
                                preload.failed("Upload Gagal Terkirim, Silahkan Hubungi Admin");
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
                        preload.failed(p1.toString());
                    }
                });
    }






    @Override
    protected void onResume() {
        super.onResume();
        iv_buktitrf = findViewById(R.id.iv_bukti_trf) ;

        if (chooseUpload.equals("4")) {
            if (chooseUpload_set.equals("4")) {
                iv_buktitrf.setImageBitmap(bitmapUpload);
                chooseUpload = "";
                chooseUpload_set = "";
                iv_buktitrf.setTag("ada");
            }
        }

    }

    public String getStringImage(Bitmap bmp) {
        int bitmap_size = 60; // range 1 - 100
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    //homeback
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public static Bitmap imageView2Bitmap(ImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }

    public static void showDateDialog(Context mContext , final TextView text , String Format){

        DatePickerDialog datePickerDialog;

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(Format, Locale.US);

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(mContext , new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                text.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }



    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select berkas from gallery",
                "Capture berkas from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {



                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmapUpload = bitmap ;
                    chooseUpload_set = "4";

                    //finish();


                    //fotoIdCard.setImageBitmap(bitmap);


//                    if(chooseUpload.equals("1")){
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        fotoIdCard.setImageBitmap(bitmap);
//                    }
//
//                    if(chooseUpload.equals("2")){
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        fotoPribadi.setImageBitmap(bitmap);
//                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UploadSimpanan.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {



            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            bitmapUpload = thumbnail ;
            chooseUpload_set = "4";



            // fotoIdCard.setImageBitmap(thumbnail);
           // finish();



            // imageView2Bitmap(berkas1);

            // Toast.makeText(UploadBerkas.this, "encode"+getStringImage(imageView2Bitmap(berkas1)), Toast.LENGTH_SHORT).show();
        }
    }




}