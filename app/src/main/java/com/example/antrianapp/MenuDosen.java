package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Maps Activity
import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

public class MenuDosen extends AppCompatActivity {

    private Button btprofil,btjadbim,btantri,btjdos;
    private TextView welcome_name, welcome_num;
    DatabaseReference db;
    SessionManager sessionManager;
    String key = "AAAAuoNn5II:APA91bHscmHH-e5iRY3UG1qILzOwE3jJQCzadKuFLq3x_qfk5mOLkSOvej6mXCDFr6kp4V51JUZG8ikGn06sbytAuK-ARgeTCEJ7N64wX59TwozVfo5cBQeH2ZtcVp9AHVGFiNUP_CnX";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dosen);

        btprofil = findViewById(R.id.bt_profil);
        btjadbim=findViewById(R.id.bt_req_bimbingan);
        btantri=findViewById(R.id.bt_lihat_antrian);
        btjdos=findViewById(R.id.bt_jadwal_dos);
        welcome_name = findViewById(R.id.judul_nama_user);
        welcome_num = findViewById(R.id.nomor_user);

        sessionManager = new SessionManager(this);
        sessionManager.cekLogin();
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        FirebaseMessaging.getInstance().subscribeToTopic(nomornya);


        db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(nomornya).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass user = snapshot.getValue(UserHelperClass.class);

                welcome_name.setText(user.getNama());
                welcome_num.setText(user.getNomor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btjadbim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDosen.this, ProsesBimDos.class));
            }
        });

        btantri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDosen.this, DaftarReqBimbingan.class));
            }
        });

        btprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDosen.this, Profil.class));
            }
        });

        btjdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDosen.this, AturKalenderLokasi.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_logout:
                AlertDialog.Builder b = new AlertDialog.Builder(MenuDosen.this);
                b.setMessage("Anda yakin ingin Logout?");
                b.setCancelable(true);
                b.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.logout();
                        finish();

                    }

                });

                b.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = b.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Tidak Diizinkan", Toast.LENGTH_SHORT).show();
    }
}