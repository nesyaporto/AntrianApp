package com.example.antrianapp;

import androidx.annotation.NonNull;
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

import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.Helper_AccBim;
import com.example.antrianapp.models.Helper_BatalSelesai;
import com.example.antrianapp.models.Helper_ListBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.KirimEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class DetailsAntrian extends AppCompatActivity {

    DatabaseReference dbRef, db;
    TextView tvNamaMhs, tvBim, etTanggal, tvStatus;
//    EditText etWaktu;
    Button selesai;
    String item_prodi,str_id, waktu, email, item_mhs, item_tgl, newpesan, item_b, id_bim, item_s,
            item_namados, item_iddos, newstat;
    String item_idmhs,item_waktu;

    int currentHour, currentMinute, mDay, mMonth, mYear;
    Calendar calendar;

    private BroadcastManager broadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_antrian);

        etTanggal = findViewById(R.id.cari_req_tanggal);
        tvNamaMhs = findViewById(R.id.tv_nmmhs);
        tvBim = findViewById(R.id.tv_bim);
        tvStatus = findViewById(R.id.tv_status);
        selesai = findViewById(R.id.dos_selesai);

        Bundle intent = getIntent().getExtras();
        item_mhs = intent.getString("nama_mhs");
        item_tgl = intent.getString("tanggal");
        item_b = intent.getString("bimb");
        item_s = intent.getString("status");
        id_bim = intent.getString("id_bim");
        item_idmhs = intent.getString("id_mhs");
        item_namados = intent.getString("to_dosen");
        item_iddos = intent.getString("id_dos");
        item_prodi = intent.getString("prodi");
        item_waktu = intent.getString("waktu");

        tvNamaMhs.setText(item_mhs);
        etTanggal.setText(item_tgl);
        tvBim.setText(item_b);
        tvStatus.setText(item_s);

        db = FirebaseDatabase.getInstance().getReference("Users");
        Query mhs = db.orderByChild("nama").equalTo(item_mhs);

        mhs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    email = dataSnapshot.child("email").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //SELESAI = TERIMA BIM
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newstat = "Terima";
                newpesan = "Bimbingan sudah diterima, Silakan hadir pada tanggal yang sudah diatur";

                Toast.makeText(DetailsAntrian.this, "Id bim "+id_bim, Toast.LENGTH_SHORT).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                dbRef = database.getReference("ProsesBim");
                db = database.getReference("ReqBimbingan");

                if (!item_mhs.isEmpty() | !item_b.isEmpty() | !item_iddos.isEmpty() | !item_tgl.isEmpty() |
                        !item_s.isEmpty() | !id_bim.isEmpty() | !item_namados.isEmpty()) {

        Helper_ReqBim req = new Helper_ReqBim(item_tgl, item_waktu, item_namados, item_b, item_idmhs,
                            item_mhs, item_iddos, id_bim, newstat, newpesan, item_prodi);

                    dbRef.child(id_bim).setValue(req);
                    db.child(item_idmhs).removeValue();

                }

                Intent intent1 = new Intent(DetailsAntrian.this, KirimEmail.class);
                intent1.putExtra("nama_mhs", item_mhs);
                intent1.putExtra("tanggal", item_tgl);
                intent1.putExtra("waktu", item_waktu);
                intent1.putExtra("bimb", item_b);
                intent1.putExtra("status", newstat);
                intent1.putExtra("id_bim", id_bim);
                intent1.putExtra("id_mhs", str_id);
                intent1.putExtra("id_dos", item_iddos);
                intent1.putExtra("to_dosen", item_namados);
                intent1.putExtra("pesan", newpesan);
                intent1.putExtra("prodi", item_prodi);
                intent1.putExtra("email", email);

                startActivity(intent1);
            }
        });


    }

    private void updateBim(String id) {

        DatabaseReference dBim = FirebaseDatabase.getInstance().getReference("JadwalBim");

        newstat = "Terima";
        newpesan = "Bimbingan sudah diterima, Silakan hadir pada tanggal yang sudah diatur";


        if (!item_mhs.isEmpty() | !item_tgl.isEmpty() | !waktu.isEmpty()) {

            Helper_ReqBim req = new Helper_ReqBim(item_tgl, item_waktu, item_namados, item_b, item_idmhs,
                    item_mhs, item_iddos, id, newstat, newpesan, item_prodi);

            dBim.child(item_idmhs).setValue(req);

        }
    }
}
