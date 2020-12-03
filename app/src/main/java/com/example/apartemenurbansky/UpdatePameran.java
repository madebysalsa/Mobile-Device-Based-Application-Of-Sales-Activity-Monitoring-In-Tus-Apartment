package com.example.apartemenurbansky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePameran extends AppCompatActivity {

    private EditText mNama, mNotelp, mAlamat, mWaktu, mKeterangan;
    private Spinner mJenisevent, mStatus;
    private Button mUpdate;
    private String nama, no_telp, alamat, waktu, keterangan, jenis_event, status;
    private int id;
    private int ambilEvent, ambilStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pameran);


        mNama = findViewById(R.id.et_nama_pelanggan);
        mNotelp = findViewById(R.id.et_no_telp);
        mAlamat = findViewById(R.id.et_alamat);
        mWaktu = findViewById(R.id.et_tanggal);
        mKeterangan = findViewById(R.id.et_keterangan);
        mStatus = findViewById(R.id.sp_status);
        mJenisevent = findViewById(R.id.sp_event);
        mUpdate = findViewById(R.id.btn_tambah);

        mWaktu.setFocusableInTouchMode(false);
        mNama.setFocusableInTouchMode(false);
        mNotelp.setFocusableInTouchMode(false);
        mAlamat.setFocusableInTouchMode(false);
        mJenisevent.setFocusableInTouchMode(false);

        Intent intent = getIntent();
        mNama.setText(intent.getStringExtra("name_pameran"));
        mNotelp.setText(intent.getStringExtra("no_telp"));
        mAlamat.setText(intent.getStringExtra("alamat"));
        mWaktu.setText(intent.getStringExtra("waktu"));
        mKeterangan.setText(intent.getStringExtra("keterangan"));
        jenis_event = intent.getStringExtra("jenis_event");
        status = intent.getStringExtra("status");
        id = intent.getIntExtra("id", 0);
        setSpinnerStatus();
        setSpinnerEvent();

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = mNama.getText().toString();
                alamat = mAlamat.getText().toString();
                no_telp = mNotelp.getText().toString();
                waktu = mWaktu.getText().toString();
                keterangan = mKeterangan.getText().toString();


                if (nama.trim().equals("")) {
                    mNama.setError("Nama Harus Diisi");
                } else {
                    getSpinnerStatus();
                    getSpinnerEvent();
                    updateData();
                }
            }
        });
    }


    private void updateData() {
        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseData> simpanData = ardData.updatePameran("update", id, jenis_event, nama, no_telp, alamat, waktu, keterangan, status);

        simpanData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                Toast.makeText(UpdatePameran.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(UpdatePameran.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpinnerStatus() {
        ambilStatus = mStatus.getSelectedItemPosition();
        if (ambilStatus == 1) {
            status = "Database";
        } if (ambilStatus == 2) {
            status = "Prospect";
        } if (ambilStatus == 3) {
            status = "Hot Prospect";
        } if (ambilStatus == 4) {
            status = "Tidak Minat";
        } if (ambilStatus == 0){
            Toast.makeText(UpdatePameran.this, "Pilih Status Terlebih Dahulu", Toast.LENGTH_SHORT).show();

        }
    }

    private void getSpinnerEvent(){
        ambilEvent = mJenisevent.getSelectedItemPosition();
        if (ambilEvent == 1) {
            jenis_event = "Pameran";
        } if (ambilEvent == 2) {
            jenis_event = "Event Kawasan";
        } if (ambilEvent == 3) {
            jenis_event = "Canvasing";
        } if (ambilEvent == 4) {
            jenis_event = "Flyring";
        } if (ambilEvent == 5) {
            jenis_event = "Follow Up Database";
        } if (ambilEvent == 6) {
            jenis_event = "Digital";
        } if (ambilEvent == 7) {
            jenis_event = "Call In";
        } if (ambilEvent == 8) {
            jenis_event = "Guest In";
        } if (ambilEvent == 0) {
            Toast.makeText(UpdatePameran.this, "Pilih Event Terlebih Dahulu", Toast.LENGTH_SHORT).show();

        }
    }
        private void setSpinnerStatus() {

            if (status.equals("Database")) {
                mStatus.setSelection(1);
            } else if (status.equals("Prospect")) {
                mStatus.setSelection(2);
            } else if (status.equals("Hot Prospect")) {
                mStatus.setSelection(3);
            } else if (status.equals("Tidak Minat")) {
                mStatus.setSelection(4);
            } else {
                mStatus.setSelection(0);
            }
        }

        private void setSpinnerEvent() {
            if (jenis_event.equals("Pameran")) {
                mJenisevent.setSelection(1);
            } else if (jenis_event.equals("Event Kawasan")) {
                mJenisevent.setSelection(2);
            } else if (jenis_event.equals("Canvasing")) {
                mJenisevent.setSelection(3);
            } else if (jenis_event.equals("Flyring")) {
                mJenisevent.setSelection(4);
            } else if (jenis_event.equals("Follow Up Database")) {
                mJenisevent.setSelection(5);
            } else if (jenis_event.equals("Digital")) {
                mJenisevent.setSelection(6);
            } else if (jenis_event.equals("Call In")) {
                mJenisevent.setSelection(7);
            } else if (jenis_event.equals("Guest In")) {
                mJenisevent.setSelection(8);
            } else {
                mJenisevent.setSelection(0);
            }
        }
    }

