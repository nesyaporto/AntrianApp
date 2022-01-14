package com.example.antrianapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.antrianapp.models.AdapterRiwayat;
import com.example.antrianapp.models.Adapter_ListBim;
import com.example.antrianapp.models.HelperRiwayat;
import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.ReqBimDosen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//implements dari class adapternya
@RequiresApi(api = Build.VERSION_CODES.O)
public class LihatTanggal extends AppCompatActivity {

    TextView judul;
    RecyclerView recyclerView;
    DatabaseReference db;
    AdapterRiwayat myAdapter;
    ArrayList<HelperRiwayat> list;
    String nomornya;
    private int currentList = 0;
    LinearLayoutManager linearLayoutManager;
    String a, b, c, d, e, f, g, h, i, dos;
    String item1, item2, item3, item4, item5;
    SimpleDateFormat sdf;
    String parsedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_tanggal);

        judul = findViewById(R.id.judul);
        recyclerView = findViewById(R.id.bimbinganList);
        db = FirebaseDatabase.getInstance().getReference("ReqBimbingan");

        Bundle intent = getIntent().getExtras();
        item1 = intent.getString("id");
        item2 = intent.getString("iddos");
        item3 = intent.getString("namados");
        item4 = intent.getString("tgl");
        item5 = intent.getString("info");

        //urutin dari yang terbaru ke yang lama (newest), sebaliknya pakai kondisi false
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        //list = new ArrayList<Helper_ReqBim>();
        list = new ArrayList<>();

        myAdapter = new AdapterRiwayat(this, list);
        recyclerView.setAdapter(myAdapter);

        Query cekUser = db.orderByChild("tanggal");
        cekUser.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

                    if (g.equals(intent.getString("tgl"))) {
                        if(f.equals("Terima")){
                            if(c.equals(intent.getString("iddos"))){
                        HelperRiwayat listBim = dataSnapshot.getValue(HelperRiwayat.class);
                        list.add(listBim);

                    }}}
                    myAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


