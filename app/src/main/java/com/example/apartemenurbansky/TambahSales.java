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

public class TambahSales extends AppCompatActivity {

    private EditText et_nama_sales, et_jabatan, et_jkel, tanggal_daftar;
    private Spinner spJkeg;
    private Button btnTambah;
    private ImageButton btnKalender;
    String  nama_sales, jabatan, jkel, tanggal, jenis_kegiatan;
    int daftarKegiatan;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sales);

        et_nama_sales = findViewById(R.id.et_nama_sales);
        et_jabatan = findViewById(R.id.et_jabatan);
        et_jkel = findViewById(R.id.et_jkel);
        tanggal_daftar = findViewById(R.id.et_tanggal);
        spJkeg = findViewById(R.id.i_jkeg);

        btnKalender = findViewById(R.id.kalender);
        btnTambah = findViewById(R.id.btn_tambah);

        btnKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahSales.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama_sales = et_nama_sales.getText().toString();
                jabatan = et_jabatan.getText().toString();
                jkel= et_jkel.getText().toString();
                tanggal = tanggal_daftar.getText().toString();
                daftarKegiatan = spJkeg.getSelectedItemPosition();

                Spinner();

                if(nama_sales.trim().equals("")){
                    et_nama_sales.setError("Nama Harus Dipilih");
                } if(jabatan.trim().equals("")){
                    et_jabatan.setError("No. Telepon Harus Diisi");
                } if(jkel.trim().equals("")){
                    et_jkel.setError("Alamat Harus Diisi");
                } if(tanggal.trim().equals("")){
                    tanggal_daftar.setError("Tanggal Daftar Harus Diisi");
                }  else{
                    createData();
                }
            }
        });
    }

    private void createData(){

        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DataSales> call = ardData.addSales("insert", nama_sales, jabatan, jkel, jenis_kegiatan, tanggal);

        call.enqueue(new Callback<DataSales>() {
            @Override
            public void onResponse(Call<DataSales> call, Response<DataSales> response) {
                String kode = response.body().getValue();
                String pesan = response.body().getMassage();

                Toast.makeText(TambahSales.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<DataSales> call, Throwable t) {
                Toast.makeText(TambahSales.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Spinner(){
        if (daftarKegiatan == 0){
            Toast.makeText(TambahSales.this, "Masukan Jenis Kegiatan", Toast.LENGTH_SHORT).show();
        } if(daftarKegiatan == 1){
            jenis_kegiatan = "Pameran";
        } if(daftarKegiatan == 2){
            jenis_kegiatan = "Event Kawasan";
        } if(daftarKegiatan == 3){
            jenis_kegiatan = "Open Table";
        } if(daftarKegiatan == 4){
            jenis_kegiatan = "Canvasing";
        } if(daftarKegiatan == 5){
            jenis_kegiatan = "Flyring";
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTanggal();
        }

    };

    private void setTanggal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        tanggal_daftar.setText(sdf.format(myCalendar.getTime()));
    }
}
