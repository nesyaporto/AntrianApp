package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianapp.models.DataUser;
import com.example.antrianapp.models.Helper_ReqBim;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profil extends AppCompatActivity {

    EditText p_nama, p_email, p_nohp;
    TextView p_nomor, p_level, p_prodi;
    String namanya, nomornya, emailnya, nohpnya, prodinya;

    Button edit;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ref = FirebaseDatabase.getInstance().getReference("Users");

        p_nama = findViewById(R.id.nama);
        p_nomor = findViewById(R.id.nomorinduk);
        p_nohp = findViewById(R.id.nohp);
        p_email = findViewById(R.id.email);
        p_level = findViewById(R.id.level);
        p_prodi = findViewById(R.id.prodi);
        edit = findViewById(R.id.bt_edit_profil);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        nomornya = userDetails.get(SessionManager.KEY_NOMOR);
       namanya = userDetails.get(SessionManager.KEY_NAMA);
        nohpnya = userDetails.get(SessionManager.KEY_NOHP);
        emailnya = userDetails.get(SessionManager.KEY_EMAIL);


        ref.child(nomornya).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass user = snapshot.getValue(UserHelperClass.class);

                p_nama.setText(user.getNama());
                p_nomor.setText(user.getNomor());
                p_nohp.setText(user.getNohp());
                p_email.setText(user.getEmail());
                p_level.setText(user.getLevel());
                p_prodi.setText(user.getProdi());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNameChanged() | isHpChanged() | isEmailChanged()){
                    Toast.makeText(Profil.this, "Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Profil.this, "Tidak Ada Perubahan", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private boolean isNameChanged(){
        if(!namanya.equals(p_nama.getText().toString())){
            ref.child(nomornya).child("nama").setValue(p_nama.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isHpChanged(){
        if(!nohpnya.equals(p_nohp.getText().toString())){
            ref.child(nomornya).child("nohp").setValue(p_nohp.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged(){
        if(!emailnya.equals(p_email.getText().toString())){
            ref.child(nomornya).child("email").setValue(p_email.getText().toString());
            return true;
        } else {
            return false;
        }
    }
}



