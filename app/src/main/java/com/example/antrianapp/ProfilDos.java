package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Button;

import com.example.antrianapp.models.Adapter_DataUser;
import com.example.antrianapp.models.Adapter_ListBim;
import com.example.antrianapp.models.Adapter_ListBimDos;
import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.Helper_ListBim;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
//isinya daftar req bimbingan dari mahasiswa untuk dosen
public class ProfilDos extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    Adapter_DataUser myAdapter;
    ArrayList<DataUser> list;
    LinearLayoutManager linearLayoutManager;
    private int currentList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_dos);

        recyclerView = findViewById(R.id.dosenList);
        db = FirebaseDatabase.getInstance().getReference("Users");

        //urutin dari yang terbaru ke yang lama (newest), sebaliknya pakai kondisi false
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new Adapter_DataUser(this,list);
        recyclerView.setAdapter(myAdapter);

        Query cekUser = db.orderByChild("level").equalTo("Dosen");
        cekUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataUser listBim = dataSnapshot.getValue(DataUser.class);
                        list.add(listBim);

                    }
                    myAdapter.notifyDataSetChanged();

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}