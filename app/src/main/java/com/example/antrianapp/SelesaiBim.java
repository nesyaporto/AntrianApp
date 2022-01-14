package com.example.antrianapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.HelperTolak;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.KirimEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SelesaiBim extends AppCompatActivity {

    DatabaseReference dbRef, dbRef2, db, dbUsersLoc;
    String namamhs, namados, idbim, iddos, jenisbim, tglbim, waktubim, stat_baru,pesan_baru,item_prodi;
    String idmhs, email;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);

        dbRef = FirebaseDatabase.getInstance().getReference("ReqBimbingan");
        dbRef2 = FirebaseDatabase.getInstance().getReference("ProsesBim");

        Bundle b = getIntent().getExtras();
        namamhs = b.getString("nama_mhs");
        tglbim = b.getString("tanggal");
        waktubim = b.getString("waktu");
        jenisbim = b.getString("bimb");
        idbim = b.getString("id_bim");
        idmhs = b.getString("id_mhs");
        iddos = b.getString("id_dos");
        namados = b.getString("to_dosen");
        item_prodi = b.getString("prodi");

        stat_baru = "Selesai";
        pesan_baru = "Terimakasih telah menyelasaikan bimbingan";

        db = FirebaseDatabase.getInstance().getReference("Users");
        Query mhs = db.orderByChild("nomor").equalTo(idmhs);

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

        if (!namamhs.isEmpty() | !tglbim.isEmpty() |  !stat_baru.isEmpty()) {

            Helper_ReqBim z = new Helper_ReqBim(tglbim, waktubim, namados,
                    jenisbim, idmhs, namamhs, iddos, idbim, stat_baru, pesan_baru, item_prodi);

            dbRef.child(idmhs).removeValue();
            dbRef2.child(idbim).setValue(z);

            Toast.makeText(SelesaiBim.this, "Bimbingan Selesai", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SelesaiBim.this, MenuDosen.class);
            startActivity(i);

        }

    }
}
