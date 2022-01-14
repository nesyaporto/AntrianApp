package com.example.antrianapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.antrianapp.models.Adapter_ListBim;
import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.ReqBimDosen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//implements dari class adapternya
public class JadwalBimMhs extends AppCompatActivity {

    Button btBuatJad;
    RecyclerView recyclerView;
    DatabaseReference db;
    Adapter_ListBim myAdapter;
    ArrayList<Helper_ListBim> list;
    String nomornya;
    private int currentList = 0;
    LinearLayoutManager linearLayoutManager;
    String a, b, c, d, e, f, g, h, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_bim_mhs);

        btBuatJad = findViewById(R.id.bt_buat_req);

        recyclerView = findViewById(R.id.bimbinganList);
        db = FirebaseDatabase.getInstance().getReference("ProsesBim");

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        myAdapter = new Adapter_ListBim(this, list);
        recyclerView.setAdapter(myAdapter);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        Query cekUser = db.orderByChild("id_mhs").equalTo(nomornya);
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
                    i = dataSnapshot.child("waktu").getValue(String.class);

                    if (f.equals("Terima")) {
                        Helper_ListBim listBim = dataSnapshot.getValue(Helper_ListBim.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.riwayat) {
            Intent i = new Intent(JadwalBimMhs.this, RiwayatBimMhs.class);
            startActivity(i);
            return true;
        }

        return false;
    }
}


