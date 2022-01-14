package com.example.antrianapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class AturKalenderLokasi extends AppCompatActivity {

    private Button bt_a, bt_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_kalender_lokasi);


        bt_a = findViewById(R.id.bt_kalender);
        bt_b = findViewById(R.id.bt_aturlok);

        bt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AturKalenderLokasi.this, MainCalender.class));
           }
        });

        bt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AturKalenderLokasi.this, ListGeoMhs.class));

            }
        });
    }
}