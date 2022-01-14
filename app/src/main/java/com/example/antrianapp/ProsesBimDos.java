package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.antrianapp.models.Adapter_ProsesBimDos;
import com.example.antrianapp.models.Adapter_ReqBim;
import com.example.antrianapp.models.Adapter_ReqBimDos;
import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.Helper_AccBim;
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

// class untuk DOSEN, setelah memproses bimbingan langsung diurutkan dalam antrian
// yang ditampilkan hanya dengan id mahasiswa yang bimbingan dengan dosen login
public class ProsesBimDos extends AppCompatActivity {

    String a, b, c, e, f, g, h, i, k,d,l,m;
    int j;
    DatabaseReference db;
    ArrayList<Helper_ReqBim> list;
    RecyclerView recyclerView;
    Adapter_ReqBimDos myAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_bim);

        recyclerView = findViewById(R.id.antri_dos);

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<Helper_ReqBim>();

        myAdapter = new Adapter_ReqBimDos(this, list);
        recyclerView.setAdapter(myAdapter);

        db = FirebaseDatabase.getInstance().getReference("ReqBimbingan");

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        //Query untuk mendapatkan id dosen yang sedang login dari database
        Query cekUser = db.orderByChild("id_dos").equalTo(nomornya);

        //mendapatkan seluruh data request antrian yang dimiliki oleh id dosen
        //yang sedang login
        cekUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    a = dataSnapshot.child("bimb").getValue(String.class);
                    b = dataSnapshot.child("id_bim").getValue(String.class);
                    c = dataSnapshot.child("id_dos").getValue(String.class);
                    d = dataSnapshot.child("id_mhs").getValue(String.class);
                    e = dataSnapshot.child("nama_mhs").getValue(String.class);
                    f = dataSnapshot.child("status").getValue(String.class);
                    g = dataSnapshot.child("tanggal").getValue(String.class);
                    h = dataSnapshot.child("to_dosen").getValue(String.class);
                    k = dataSnapshot.child("pesan").getValue(String.class);
                    l = dataSnapshot.child("prodi").getValue(String.class);
                    m = dataSnapshot.child("waktu").getValue(String.class);

if(f.equals("Pending")) {
    Helper_ReqBim acc = new Helper_ReqBim(g, m, h, a, d, e, c, b, f, k, l);


    list.add(acc);
}
                }
                Toast.makeText(ProsesBimDos.this, "Id bim "+b, Toast.LENGTH_SHORT).show();

                    myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}