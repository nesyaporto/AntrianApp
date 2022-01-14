package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.antrianapp.models.Helper_AccBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.KirimEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsBimDosen extends AppCompatActivity {

    DatabaseReference db;
    TextView tvNamados, tvBim, etTanggal, etWaktu, tvStatus;
    Button terima, tolak;
    String item_dos, item_tgl, item_wkt, item_b, id, item_s,
            item_namados, item_iddos, item_idmhs,
            item_pesan, email, item_prodi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_bim_dosen);

        etTanggal = findViewById(R.id.cari_req_tanggal);
        etWaktu = findViewById(R.id.cari_req_waktu);
        tvNamados = findViewById(R.id.tv_nmdos);
        tvBim = findViewById(R.id.tv_bim);
        tvStatus = findViewById(R.id.tv_status);
        terima = findViewById(R.id.dos_terima);
        tolak = findViewById(R.id.dos_tolak);

        Bundle intent = getIntent().getExtras();
        item_dos = intent.getString("nama_mhs");
        item_tgl = intent.getString("tanggal");
        item_wkt = intent.getString("waktu");
        item_b = intent.getString("bimb");
        item_s = intent.getString("status");
        id = intent.getString("id_bim");
        item_idmhs = intent.getString("id_mhs");
        item_namados= intent.getString("to_dosen");
        item_iddos= intent.getString("id_dos");
        item_pesan= intent.getString("pesan");
        item_prodi= intent.getString("prodi");

        tvNamados.setText(item_dos);
        etTanggal.setText(item_tgl);
        etWaktu.setText(item_wkt);
        tvBim.setText(item_b);
        tvStatus.setText(item_s);

        db = FirebaseDatabase.getInstance().getReference("Users");
        Query mhs = db.orderByChild("nama").equalTo(item_dos);

        mhs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    email = dataSnapshot.child("email").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //TOLAK = BATAL
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(DetailsBimDosen.this, FormBatal.class);
                intent1.putExtra("nama_mhs", item_dos);
                intent1.putExtra("tanggal", item_tgl);
                intent1.putExtra("waktu", item_wkt);
                intent1.putExtra("bimb", item_b);
                intent1.putExtra("status", item_s);
                intent1.putExtra("id_bim", id);
                intent1.putExtra("id_mhs", item_idmhs);
                intent1.putExtra("id_dos", item_iddos);
                intent1.putExtra("to_dosen", item_namados);
                intent1.putExtra("pesan", item_pesan);
                intent1.putExtra("email", email);
                intent1.putExtra("prodi", item_prodi);

                startActivity(intent1);
            }
        });

//TERIMA = SELESAI BIM
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailsBimDosen.this, SelesaiBim.class);
                i.putExtra("nama_mhs", item_dos);
                i.putExtra("tanggal", item_tgl);
                i.putExtra("waktu", item_wkt);
                i.putExtra("bimb", item_b);
                i.putExtra("status", item_s);
                i.putExtra("id_bim", id);
                i.putExtra("id_mhs", item_idmhs + "");
                i.putExtra("id_dos", item_iddos);
                i.putExtra("to_dosen", item_namados);
                i.putExtra("prodi", item_prodi);
                startActivity(i);
            }
        });
    }
}