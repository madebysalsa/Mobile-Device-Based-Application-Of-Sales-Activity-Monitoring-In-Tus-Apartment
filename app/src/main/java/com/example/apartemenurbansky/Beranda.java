package com.example.apartemenurbansky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;

import java.util.Objects;

public class Beranda extends AppCompatActivity {
    CardView logout, input_konsumen, absenSales, input_canvas;
    private TextView username;
    private PreferenceHelper preferenceHelper;
    private String username_sales;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Beranda");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceHelper = new PreferenceHelper(this);
        username = findViewById(R.id.text_nama);

        username.setText(preferenceHelper.getUsername());
        input_konsumen = findViewById(R.id.input_konsumen);
        absenSales = findViewById(R.id.absen_sales);
        input_canvas = findViewById(R.id.input_canvas);

        input_konsumen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(Beranda.this, ReadPameran.class);
                startActivity(intent);
            }
        }
        );

        absenSales.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(Beranda.this, Input.class);
                startActivity(intent);
            }
        }
        );

        input_canvas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                username_sales = username.getText().toString();

                Intent intent = new Intent(Beranda.this, Canvasing.class);
                intent.putExtra("username_sales", username_sales);
                startActivity(intent);
                                            }
                                        }
        );


        //final SharedPreferences sharedPreferences = getSharedPreferences( "UserInfo", MODE_PRIVATE);

       logout = findViewById(R.id.logout);
        //sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(Beranda.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
