package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.antrianapp.models.Adapter_Calender;
import com.example.antrianapp.models.Adapter_ListBimDos;
import com.example.antrianapp.models.Helper_EventCalender;
import com.example.antrianapp.models.Helper_ListBim;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainCalenderMhs extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Calender myAdapter;
    ArrayList<Helper_EventCalender> list;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference db;
    String a,b,c,d,e, datestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calender_mhs);
        CalendarView calendarView = findViewById(R.id.Kalender);
        recyclerView = findViewById(R.id.calendarRecyclerView);


        db = FirebaseDatabase.getInstance().getReference("JadwalKosong");

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new Adapter_Calender(this, list);
        recyclerView.setAdapter(myAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                datestr = String.format("%02d/%02d/%04d", dayOfMonth ,month+1 ,year);
                list.clear();

                Query cekEvent = db.orderByChild("tglpilihan");
                cekEvent.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            a = dataSnapshot.child("id").getValue(String.class);
                            b = dataSnapshot.child("iddos").getValue(String.class);
                            c = dataSnapshot.child("info").getValue(String.class);
                            d = dataSnapshot.child("namados").getValue(String.class);
                            e = dataSnapshot.child("tglpilihan").getValue(String.class);

                            if(e.equals(datestr)){
                                Helper_EventCalender helper = dataSnapshot.getValue(Helper_EventCalender.class);
                                list.add(helper);
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }
}