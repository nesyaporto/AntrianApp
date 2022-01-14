package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianapp.models.Helper_Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LogIn extends AppCompatActivity{

    private TextView btn;
    private EditText ni, pass;
    private ProgressDialog mLoadingBar;

    SessionManager sessionManager;

    public static final String KEY_NOMOR = "nomor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = findViewById(R.id.daftarAkun);
        ni = findViewById(R.id.ni);
        pass = findViewById(R.id.login_pass);

        mLoadingBar = new ProgressDialog(LogIn.this);

        //btn ke halaman daftar akun
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogIn.this, Register.class));
            }

        });

    }

    private Boolean validateNomor() {
        String val = ni.getText().toString();

        if (val.isEmpty()) {
            mLoadingBar.cancel();
            ni.setError("NIP / NIM Wajib Diisi");
            return false;
        } else {
            ni.setError(null);
            return true;
        }
    }


    private Boolean validatePass() {
        String val = pass.getText().toString();

        if (val.isEmpty()) {
            pass.setError("Password Wajib Diisi");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }


    public void loginUser(View view) { //onClick btn login

        if (!validateNomor() | !validatePass()) { //cek info login
            return;
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Mohon Tunggu, Sedang Memproses Info Login");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            isUser();
        }
    }
    private void isUser() {
        String noEntered = ni.getText().toString();
        String passEntered = pass.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query cekUser = reference.orderByChild("nomor").equalTo(noEntered);

        cekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passDb = snapshot.child(noEntered).child("pass").getValue(String.class);
                    String level = snapshot.child(noEntered).child("level").getValue(String.class);
                    if (passDb.equals(passEntered)) {
                        if (level.equals("Dosen")) {
                            String emaildos = snapshot.child(noEntered).child("email").getValue(String.class);
                            String levelnya= snapshot.child(noEntered).child("level").getValue(String.class);
                            String namados = snapshot.child(noEntered).child("nama").getValue(String.class);
                            String nohpdos = snapshot.child(noEntered).child("nohp").getValue(String.class);
                            String nomordos = snapshot.child(noEntered).child("nomor").getValue(String.class);
                            String passdos = snapshot.child(noEntered).child("pass").getValue(String.class);
                            String prodidos = snapshot.child(noEntered).child("prodi").getValue(String.class);

                            //Create Session
                            sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createLoginSession(emaildos, levelnya, namados, nohpdos, nomordos, passdos,prodidos);
                            startActivity(new Intent(getApplicationContext(),MenuDosen.class));
                            Toast.makeText(LogIn.this, "Dosen "+namados+" Berhasil Login",Toast.LENGTH_SHORT).show();

                        } else if (level.equals("Mahasiswa")) {
                            String emailmhs = snapshot.child(noEntered).child("email").getValue(String.class);
                            String levelnya= snapshot.child(noEntered).child("level").getValue(String.class);
                            String namamhs = snapshot.child(noEntered).child("nama").getValue(String.class);
                            String nohpmhs = snapshot.child(noEntered).child("nohp").getValue(String.class);
                            String nomormhs = snapshot.child(noEntered).child("nomor").getValue(String.class);
                            String passmhs = snapshot.child(noEntered).child("pass").getValue(String.class);
                            String prodimhs = snapshot.child(noEntered).child("prodi").getValue(String.class);

                            //Create Session
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createLoginSession(emailmhs, levelnya, namamhs, nohpmhs, nomormhs, passmhs, prodimhs);
                            startActivity(new Intent(getApplicationContext(),MenuMhs.class));
                            Toast.makeText(LogIn.this, "Mahasiswa "+namamhs+" Berhasil Login",Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        mLoadingBar.cancel();
                        pass.setError("Password Salah");
                    }
                } else {
                    mLoadingBar.cancel();
                    ni.setError("NIP / NIM Belum Terdaftar");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
