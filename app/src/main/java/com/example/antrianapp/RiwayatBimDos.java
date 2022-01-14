package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.antrianapp.models.AdapterRiwayat;
import com.example.antrianapp.models.HelperRiwayat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class RiwayatBimDos extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    AdapterRiwayat myAdapter;
    ArrayList<HelperRiwayat> list;
    LinearLayoutManager linearLayoutManager;
    String a, b, c, d, e, f, g, h, i, j,k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_bim_dos);
        recyclerView = findViewById(R.id.bimbinganList);
        db = FirebaseDatabase.getInstance().getReference("ProsesBim");

        //urutin dari yang terbaru ke yang lama (newest), sebaliknya pakai kondisi false
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new AdapterRiwayat(this, list);
        recyclerView.setAdapter(myAdapter);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        Query cekUser = db.orderByChild("id_dos").equalTo(nomornya);
        cekUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    a = dataSnapshot.child("bimb").getValue(String.class);
                    b = dataSnapshot.getKey();
                    c = dataSnapshot.child("id_dos").getValue(String.class);
                    d = dataSnapshot.child("id_mhs").getValue(String.class);
                    e = dataSnapshot.child("nama_mhs").getValue(String.class);
                    f = dataSnapshot.child("status").getValue(String.class);
                    g = dataSnapshot.child("tanggal").getValue(String.class);
                    h = dataSnapshot.child("to_dosen").getValue(String.class);
                    j = dataSnapshot.child("pesan").getValue(String.class);
                    k = dataSnapshot.child("prodi").getValue(String.class);

                    if (f.equals("Ditolak") | f.equals("Batal") | f.equals("Selesai")) {
                        HelperRiwayat listBim = dataSnapshot.getValue(HelperRiwayat.class);
                        list.add(listBim);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}