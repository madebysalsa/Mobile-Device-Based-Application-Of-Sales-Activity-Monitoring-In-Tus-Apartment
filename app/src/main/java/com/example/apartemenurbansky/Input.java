package com.example.apartemenurbansky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Input extends AppCompatActivity {

    CardView absenDatang, absenPulang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        absenDatang = findViewById(R.id.absen_datang);
        absenPulang = findViewById(R.id.absen_pulang);
        absenDatang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(Input.this, AbsenDatang.class);
                startActivity(intent);
            }
        }
        );

        absenPulang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Input.this, AbsenPulang.class);
                                             startActivity(intent);
                                         }
                                     }
        );
    }
}
