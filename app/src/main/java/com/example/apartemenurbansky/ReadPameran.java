package com.example.apartemenurbansky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPameran extends AppCompatActivity {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataPameran> listData = new ArrayList<>();
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private FloatingActionButton fabTambah;
    private TextView username_sales;
    private String username;
    private PreferenceHelper preferenceHelper;
    private String nama_sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pameran);

        rvData = findViewById(R.id.rv_data);
        srlData = findViewById(R.id.srl_data);
        pbData = findViewById(R.id.pb_data);
        fabTambah = findViewById(R.id.fab_tambah);
        //username_sales = findViewById(R.id.text_nama);

        preferenceHelper = new PreferenceHelper(this);

        nama_sales = preferenceHelper.getUsername();


        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrieveData();
                srlData.setRefreshing(false);
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //username = username_sales.getText().toString();

                Intent intent = new Intent(ReadPameran.this, TambahPameran.class);
                //intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbData.setVisibility(View.VISIBLE);

        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseData> tampilData = ardData.getPameran("get", nama_sales);

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if(value.equals("1")) {
                    Toast.makeText(ReadPameran.this, "Value : " + value + " | Message : " + message, Toast.LENGTH_SHORT).show();
                    listData = response.body().getData();

                    adData = new Adapter_Pameran(ReadPameran.this, listData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

                    pbData.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(ReadPameran.this, "Value : " + value + " | Message : " + message, Toast.LENGTH_SHORT).show();
                    pbData.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ReadPameran.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}
