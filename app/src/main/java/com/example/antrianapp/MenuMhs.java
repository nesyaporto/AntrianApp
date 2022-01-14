package com.example.antrianapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.antrianapp.models.Helper_Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuMhs extends AppCompatActivity {

    private Button btprofil,btjadbim, btantrian, btjdos;
    private TextView welcome_name, welcome_num;
    DatabaseReference db;
    String nomornya;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        sessionManager = new SessionManager(this);
        sessionManager.cekLogin();
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        String namanya = userDetails.get(SessionManager.KEY_NAMA);
         nomornya = userDetails.get(SessionManager.KEY_NOMOR);

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

        btprofil = findViewById(R.id.bt_profil);
        btjadbim=findViewById(R.id.bt_lihat_bimbingan);
        btantrian=findViewById(R.id.bt_req_antrian);
        btjdos=findViewById(R.id.bt_jadwal_dos);
        welcome_name = findViewById(R.id.judul_nama_user);
        welcome_num = findViewById(R.id.nomor_user);

        //ke halaman membuat request jadwal bimbingan & lihat daftarnya
        btjadbim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuMhs.this, ProsesBimMhs.class));
            }
        });

        btantrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuMhs.this, JadwalBimMhs.class));
            }
        });

        btprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuMhs.this, Profil.class));
            }
        });

        btjdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenuMhs.this, LihatDosen.class);
                intent1.putExtra("nomor", nomornya);
                intent1.putExtra("nama", namanya);
                startActivity(intent1);
            }
        });
    }//tutup onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_logout:
                AlertDialog.Builder b = new AlertDialog.Builder(MenuMhs.this);
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

}//TUTUP CLASS