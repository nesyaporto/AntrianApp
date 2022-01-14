package com.example.antrianapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianapp.models.Helper_EventCalender;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EventCalender extends AppCompatActivity {

    DatabaseReference dbRef;
    String iddos, namados, tglpilihan, id;
    TextView tv_nama, tv_tgl;
    EditText et_info;
    Button simpan_info;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calender);

        tv_nama = findViewById(R.id.nama);
        tv_tgl = findViewById(R.id.tgl);
        et_info = (EditText) findViewById(R.id.info_jadkos);
        simpan_info = findViewById(R.id.bt_simpaninfo);

        dbRef = FirebaseDatabase.getInstance().getReference("JadwalKosong");
        Intent incomingIntent = getIntent();
        tglpilihan = incomingIntent.getStringExtra("date");
        tv_tgl.setText(tglpilihan);

        sessionManager = new SessionManager(this);
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();
        iddos = userDetails.get(SessionManager.KEY_NOMOR);
        namados = userDetails.get(SessionManager.KEY_NAMA);
        tv_nama.setText(namados);

        String info = et_info.getText().toString();
        id = dbRef.child("JadwalKosong").push().getKey();

        simpan_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tglpilihan !=null && namados != null && info != null){
                    Helper_EventCalender evCal = new Helper_EventCalender(id, iddos, namados, tglpilihan, et_info.getText().toString());
                    dbRef.child(id).setValue(evCal);
                    Toast.makeText(EventCalender.this, "Berhasil Menyimpan Info", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EventCalender.this, MainCalender.class));
                }
            }
        });




    }
}