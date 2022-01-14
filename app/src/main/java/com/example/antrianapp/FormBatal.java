package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.HelperTolak;
import com.example.antrianapp.models.Helper_BatalSelesai;
import com.example.antrianapp.models.KirimEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FormBatal extends AppCompatActivity {

    DatabaseReference dbRef, dbRef2;
    String email, ini_pesan, namamhs, namados, idbim, iddos, jenisbim, tglbim, waktubim, stat_baru, str_idmhs,prodi;
    int idmhs;
    TextView tv_nama, tgl_bim, waktu_bim, newstatus;
    EditText pesan;
    Button kirim_batal;
    DatabaseReference db, dbUsersLoc;

    private static final int ID_ONETIME = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_batal);

        dbRef = FirebaseDatabase.getInstance().getReference("ReqBimbingan");
        dbRef2 = FirebaseDatabase.getInstance().getReference("ProsesBim");
        tv_nama = findViewById(R.id.nama_mhs);
        tgl_bim = findViewById(R.id.tgl_bimbingan);
        waktu_bim = findViewById(R.id.waktu_bimbingan);
        pesan = findViewById(R.id.pesan_batal);
        kirim_batal = findViewById(R.id.bt_kirim_batal);
        newstatus = findViewById(R.id.newstatus);


        //Mendapatkan value dari activity sebelumnya
        Bundle b = getIntent().getExtras();
        namamhs = b.getString("nama_mhs");
        tglbim = b.getString("tanggal");
        waktubim = b.getString("waktu");
        jenisbim = b.getString("bimb");
        idbim = b.getString("id_bim");
        str_idmhs = b.getString("id_mhs");
        iddos = b.getString("id_dos");
        namados = b.getString("to_dosen");
        prodi = b.getString("prodi");

        stat_baru = "Batal";

        tv_nama.setText(namamhs);
        tgl_bim.setText(tglbim);
        waktu_bim.setText(waktubim);
        newstatus.setText(stat_baru);


        db = FirebaseDatabase.getInstance().getReference("Users");
        Query mhs = db.orderByChild("nama").equalTo(namamhs);

        //mendapatkan email dari nama_mhs
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

        kirim_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Membatalkan alarm
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), BroadcastManager.class);
                PendingIntent pi2 = PendingIntent.getBroadcast(getApplicationContext(), ID_ONETIME, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pi2);


                ini_pesan = pesan.getText().toString();

                if (ini_pesan.isEmpty()) {
                    pesan.setError("Pesan Wajib Diisi");
                    return;
                }


                if (!namamhs.isEmpty() | !tglbim.isEmpty() | !waktubim.isEmpty() | !ini_pesan.isEmpty()) {

                    HelperTolak z = new HelperTolak(tglbim, waktubim, namados, jenisbim, str_idmhs, namamhs,
                            iddos, idbim, stat_baru, ini_pesan,prodi);
                    dbRef.child(str_idmhs).removeValue();

                    dbRef2.child(idbim).setValue(z);

                    dbUsersLoc = FirebaseDatabase.getInstance().getReference("UsersLoc");
                    dbUsersLoc.child(str_idmhs).removeValue();
                }



                Intent intent1 = new Intent(FormBatal.this, KirimEmail.class);

                intent1.putExtra("nama_mhs", namamhs);
                intent1.putExtra("tanggal", tglbim);
                intent1.putExtra("waktu", waktubim);
                intent1.putExtra("bimb", jenisbim);
                intent1.putExtra("status", stat_baru);
                intent1.putExtra("id_bim", idbim);
                intent1.putExtra("id_mhs", str_idmhs);
                intent1.putExtra("id_dos", iddos);
                intent1.putExtra("to_dosen", namados);
                intent1.putExtra("pesan", ini_pesan);
                intent1.putExtra("prodi", prodi);
                intent1.putExtra("email", email);

                startActivity(intent1);


            }
        });

    }


}