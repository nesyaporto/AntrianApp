package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.antrianapp.models.Adapter_ListBim;
import com.example.antrianapp.models.Adapter_ListBimDos;
import com.example.antrianapp.models.Adapter_LocJarak;
import com.example.antrianapp.models.Adapter_Location;
import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.HelperRiwayat;
import com.example.antrianapp.models.Helper_AccBim;
import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_LocJarak;
import com.example.antrianapp.models.Helper_Location;
import com.example.antrianapp.models.Helper_LokasiTerkini;
import com.example.antrianapp.models.Helper_ReqBim;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;

import static java.lang.String.valueOf;

public class ListGeoMhs extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db, db2;
    Adapter_LocJarak myAdapter;
    ArrayList<Helper_LocJarak> list;

    LinearLayoutManager linearLayoutManager;
    String a, b, c, e, f,h,i;
    int d, g, j;
    String item_a, item_b, item_c, item_d, item_e, item_f, item_g, item_h, item_i;
public String kehadiran;

    String tanggal = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


    PriorityQueue<Helper_LocJarak> pq = new PriorityQueue<Helper_LocJarak>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_geo_mhs);

        recyclerView = findViewById(R.id.geoList);
        db = FirebaseDatabase.getInstance().getReference("UsersLoc");

        //urutin dari yang terbaru ke yang lama (newest), sebaliknya pakai kondisi false
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new Adapter_LocJarak(this, list);
        recyclerView.setAdapter(myAdapter);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        Query q = db.orderByChild("iddos");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    a = dataSnapshot.child("id").getValue(String.class);
                    b = dataSnapshot.child("iddos").getValue(String.class);
                    c = dataSnapshot.child("jam").getValue(String.class);
                    d = Integer.valueOf(valueOf(dataSnapshot.child("jarak").getValue()));
                    e = dataSnapshot.child("nama").getValue(String.class);
                    f = dataSnapshot.child("namados").getValue(String.class);
                    g = Integer.valueOf(valueOf(dataSnapshot.child("nomor").getValue()));
                    h = dataSnapshot.child("status").getValue(String.class);
                    i = dataSnapshot.child("tgl").getValue(String.class);

                    Helper_LocJarak acc = new Helper_LocJarak(a,e,h,i,c,b,f,g,d);

                    if (b.equals(nomornya) & i.equals(tanggal)) {
                        pq.add(acc);
                    }

                    //Melakukan iterasi pada elemen priority queue
                    Iterator iterator = pq.iterator();
                    //jika memiliki elemen setelahnya, maka akan ditampilkan lewat log.info
                    while (iterator.hasNext()) {
                        Log.i("antri", "Elemen ke " + j + " adalah " + iterator.next());
                        j++;
                    }
                }
                //Menambahkan seluruh elemen priority queue kedalam list bertipe array list
                list.addAll(pq); //22

                //Melakukan update data pada recyclerview
                myAdapter.notifyDataSetChanged(); //23
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       }

}



