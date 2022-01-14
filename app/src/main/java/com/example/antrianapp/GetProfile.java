package com.example.antrianapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GetProfile  extends AppCompatActivity {

    EditText p_nama, p_email, p_nohp;
    TextView p_nomor, p_level;
    String nomornya;
    String g_email, g_level,g_nama, g_nohp, g_nomor;
    Button edit;
    DatabaseReference ref;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profil);
        super.onCreate(savedInstanceState);

        p_nama = findViewById(R.id.nama);
        p_nomor = findViewById(R.id.nomorinduk);
        p_nohp = findViewById(R.id.nohp);
        p_email = findViewById(R.id.email);
        p_level = findViewById(R.id.level);

        edit = findViewById(R.id.bt_edit_profil);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        p_nama.setText("Halo");

        Query cekUser = ref.child("nomor").equalTo(nomornya);
        cekUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    g_email = snapshot.child(nomornya).child("email").getValue().toString();
                    g_level = snapshot.child(nomornya).child("level").getValue(String.class);
                    g_nama = snapshot.child(nomornya).child("nama").getValue(String.class);
                    g_nohp = snapshot.child(nomornya).child("nohp").getValue(String.class);
                    g_nomor = snapshot.child(nomornya).child("nomor").getValue(String.class);

                    p_nama.setText("Halo");
                    p_nomor.setText(g_nomor);
                    p_nohp.setText(g_nohp);
                    p_email.setText(g_email);
                    p_level.setText(g_level);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
