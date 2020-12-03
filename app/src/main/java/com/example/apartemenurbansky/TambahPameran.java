package com.example.apartemenurbansky;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPameran extends AppCompatActivity {

    private EditText name_pel, no_tlp_pel, alamat_pel, tanggal_daftar, keterangan_pel;
    private Spinner spStatus, spEvent;
    private Button btnTambah;
    String jenis_event, name, no_tlp, alamat, tanggal, keterangan, status, nama_sales;
    private PreferenceHelper preferenceHelper;
    int daftarStatus, daftarEvent;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pameran);

        name_pel = findViewById(R.id.et_nama_pelanggan);
        no_tlp_pel = findViewById(R.id.et_no_telp);
        alamat_pel = findViewById(R.id.et_alamat);
        tanggal_daftar = findViewById(R.id.et_tanggal);
        keterangan_pel = findViewById(R.id.et_keterangan);
        spStatus = findViewById(R.id.sp_status);
        spEvent = findViewById(R.id.sp_event);
        btnTambah = findViewById(R.id.btn_tambah);
        setTanggal();
        tanggal_daftar.setFocusableInTouchMode(false);

        preferenceHelper = new PreferenceHelper(this);

        nama_sales = preferenceHelper.getUsername();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_pel.getText().toString();
                no_tlp = no_tlp_pel.getText().toString();
                alamat = alamat_pel.getText().toString();
                tanggal = tanggal_daftar.getText().toString();
                keterangan = keterangan_pel.getText().toString();
                daftarStatus = spStatus.getSelectedItemPosition();
                daftarEvent = spEvent.getSelectedItemPosition();

                //Spinner();

                if(name.trim().equals("")){
                    name_pel.setError("Nama Harus Dipilih");
                } else if(no_tlp.trim().equals("")){
                    no_tlp_pel.setError("No. Telepon Harus Diisi");
                } else if(alamat.trim().equals("")){
                    alamat_pel.setError("Alamat Harus Diisi");
                } else if(tanggal.trim().equals("")){
                    tanggal_daftar.setError("Tanggal Daftar Harus Diisi");
                } else if(keterangan.trim().equals("")){
                    keterangan_pel.setError("Keterangan Harus Diisi");
                } else{
                    //createData();
                    Spinner();
                }
            }
        });
    }

    private void createData(){

        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseData> call = ardData.addPameran("insert", nama_sales, jenis_event, name, no_tlp, alamat, tanggal, keterangan, status);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = response.body().getValue();
                String pesan = response.body().getMessage();

                Toast.makeText(TambahPameran.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(TambahPameran.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Spinner(){
        if (daftarStatus == 0){
            Toast.makeText(TambahPameran.this, "Pilih Status Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }else if(daftarEvent == 0){
            Toast.makeText(TambahPameran.this, "Pilih Event Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }else{
            status = spStatus.getSelectedItem().toString();
            jenis_event = spEvent.getSelectedItem().toString();
            createData();
        }
    }


    private void setTanggal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        tanggal_daftar.setText(sdf.format(myCalendar.getTime()));
    }
}
