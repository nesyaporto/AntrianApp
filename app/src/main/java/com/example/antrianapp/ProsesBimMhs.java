package com.example.antrianapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antrianapp.models.Adapter_ProsesBimMhs;
import com.example.antrianapp.models.Adapter_ReqBim;
import com.example.antrianapp.models.Helper_AccBim;
import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import static java.lang.String.valueOf;

// class lihat bimbingan oleh MHS
// menampilkan bimbingan mhs dengan id dosen yg dipilih

public class ProsesBimMhs extends AppCompatActivity {

    String a, b, c, e, f, g, h, i, d,iddos, k, l,m;
    int j;
    ArrayList<Helper_ReqBim> list;
    RecyclerView recyclerView;
    Adapter_ReqBim myAdapter;
    LinearLayoutManager linearLayoutManager;
    Button btBuatJad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_bim_mhs);

        recyclerView = findViewById(R.id.antri_mhs);
        btBuatJad = findViewById(R.id.bt_buat_req);

        btBuatJad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProsesBimMhs.this, ReqJadwalBimbingan.class));
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new Adapter_ReqBim(this, list);
        recyclerView.setAdapter(myAdapter);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("ReqBimbingan");


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);


        //Query untuk mengambil seluruh id dosen
        Query dos = db.orderByChild("id_mhs").equalTo(nomornya);


        //mendapatkan seluruh data request bimbingan mhs yang bimbingan dengan id dosen
        dos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    a = dataSnapshot.child("bimb").getValue(String.class);
                    b = dataSnapshot.getKey(); //11
                    c = dataSnapshot.child("id_dos").getValue(String.class);
                    d = dataSnapshot.child("id_mhs").getValue(String.class);
                    e = dataSnapshot.child("nama_mhs").getValue(String.class);
                    f = dataSnapshot.child("status").getValue(String.class);
                    g = dataSnapshot.child("tanggal").getValue(String.class);
                    h = dataSnapshot.child("to_dosen").getValue(String.class);
                    k = dataSnapshot.child("pesan").getValue(String.class);
                    l = dataSnapshot.child("prodi").getValue(String.class);
                    m = dataSnapshot.child("waktu").getValue(String.class);

                    if (f.equals("Pending")) {
                        Helper_ReqBim acc = new Helper_ReqBim(g, m, h, a, d, e, c, b, f, k, l);

                        list.add(acc);

                    }
                }

                    myAdapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
