package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.KirimEmail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class DetailsSetWaktuBim extends AppCompatActivity {

    TextView tvNamaMhs, tvBim, tvTanggal, tvStatus;
    EditText etWaktu;
    Button aturwaktu;

    String item_namamhs, item_tgl, item_iddos, item_namados, id_bim, waktu, newpesan, str_id_mhs;
    Integer item_idmhs;

    int currentHour, currentMinute, mDay, mMonth, mYear;
    Calendar calendar;

    private BroadcastManager broadcastManager;

    DatabaseReference dbRef, dbUsersLoc, db;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String a,b,c,d,e,f,g,h,i,j,k,l;
    String emailnya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_set_waktu_bim);

        tvTanggal = findViewById(R.id.cari_req_tanggal);
        etWaktu = findViewById(R.id.cari_req_waktu);
        tvNamaMhs = findViewById(R.id.tv_nmmhs);
        tvBim = findViewById(R.id.tv_bim);
        tvStatus = findViewById(R.id.tv_status);
        aturwaktu = findViewById(R.id.dos_selesai);

        Bundle intent = getIntent().getExtras();
        item_namamhs = intent.getString("namamhs");
        item_tgl = intent.getString("tgl");
        item_iddos = intent.getString("iddos");
        item_namados = intent.getString("namados");
        id_bim = intent.getString("id");
        item_idmhs = getIntent().getIntExtra("idmhs", 0);

        str_id_mhs = String.valueOf(item_idmhs);

        db = FirebaseDatabase.getInstance().getReference("Users");
        Query mhs = db.orderByChild("nama").equalTo(item_namamhs);

        mhs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    emailnya = dataSnapshot.child("email").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference("ProsesBim");
        Query detail_prosesbim = dbRef.orderByChild("id_mhs").equalTo(str_id_mhs);

        detail_prosesbim.addValueEventListener(new ValueEventListener() {
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
                    j = dataSnapshot.child("prodi").getValue(String.class);
                    l = dataSnapshot.child("pesan").getValue(String.class);

                    tvBim.setText(a);
                    tvStatus.setText(j);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvNamaMhs.setText(item_namamhs);
        tvTanggal.setText(item_tgl);
        etWaktu.setText(waktu);

        broadcastManager = new BroadcastManager();
        calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        etWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        currentHour = hourOfDay;
                        currentMinute = minute;
                        etWaktu.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                        waktu = etWaktu.getText().toString();
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(DetailsSetWaktuBim.this, onTimeSetListener,
                        currentHour, currentMinute, true);
                timePickerDialog.setTitle("Pilih Waktu");
                timePickerDialog.show();
            }
        });

        aturwaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                newpesan = "Waktu bimbingan sudah diatur, bimbingan akan segera dimulai";

                String wkt = etWaktu.getText().toString();

                if (wkt.isEmpty()) {
                    etWaktu.setError("Waktu Tidak Boleh Kosong");
                    return;
                }

//                db = database.getReference("ReqBimbingan");

                if (!str_id_mhs.isEmpty() | !item_tgl.isEmpty() | !wkt.isEmpty() | !emailnya.isEmpty() | !item_namados.isEmpty()){
                            Helper_ReqBim req = new Helper_ReqBim(item_tgl, wkt, item_namados, a, str_id_mhs, item_namamhs,
                            item_iddos, b, f, newpesan, j);

                    dbRef.child(b).setValue(req);

                    broadcastManager.setOneTimeAlarm(DetailsSetWaktuBim.this, broadcastManager.TYPE_ONE_TIME, item_tgl, wkt,
                            "Sekarang waktunya Bimbingan "+a+" "+item_namamhs+"\ndengan Dosen "+item_namados);

                    dbUsersLoc = FirebaseDatabase.getInstance().getReference("UsersLoc");
                    dbUsersLoc.child(str_id_mhs).removeValue();

                }

                Intent intent1 = new Intent(DetailsSetWaktuBim.this, KirimEmail.class);
                intent1.putExtra("nama_mhs", item_namamhs);
                intent1.putExtra("tanggal", item_tgl);
                intent1.putExtra("waktu", wkt);
                intent1.putExtra("bimb", a);
                intent1.putExtra("status", f);
                intent1.putExtra("id_bim", id_bim);
                intent1.putExtra("id_mhs", str_id_mhs);
                intent1.putExtra("id_dos", item_iddos);
                intent1.putExtra("to_dosen", item_namados);
                intent1.putExtra("pesan", newpesan);
                intent1.putExtra("email", emailnya);
                intent1.putExtra("prodi", j);

                startActivity(intent1);
            }
        });


    }
}