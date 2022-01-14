package com.example.antrianapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class LihatDosen extends AppCompatActivity {

    private Button bt_a, bt_b, bt_c;
    String nomor,nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_dosen);

        bt_a = findViewById(R.id.bt_kalender);
        bt_b = findViewById(R.id.bt_profildos);
        bt_c = findViewById(R.id.bt_aturlokasi);

        Bundle b = getIntent().getExtras();
        nomor = b.getString("nomor");
        nama = b.getString("nama");

        bt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LihatDosen.this, MainCalenderMhs.class));
            }
        });

        bt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LihatDosen.this, ProfilDos.class));
            }
        });

        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LihatDosen.this, LokasiDosen.class));
                Intent intent1 = new Intent(LihatDosen.this, LokasiDosen.class);

                intent1.putExtra("nomor", nomor);
                intent1.putExtra("nama", nama);
                startActivity(intent1);
            }
        });
    }
}