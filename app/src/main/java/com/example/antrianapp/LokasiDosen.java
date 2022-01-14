package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianapp.models.Helper_LocJarak;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LokasiDosen extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG ="LokasiDosen" ;
    Button btn_a, btn_b;
    TextView tv_lat, tv_long, tv_al;
    public Double loclat;
    public Double loclong;
    public Double res_m;
    public String nomor, nama;
    String waktu = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
    String tanggal = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    private static String hasil_geofence,  iddos, namados, idmhs, tglbim;
    DatabaseReference db;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("UsersLoc");

    MyBackgroundService myService=null;
    boolean mBound = false;

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;
    private GeofencingRequest geofencingRequest;
    private GoogleApiClient googleApiClient;
    private boolean isMonitoring = true; //false
    private MarkerOptions markerOptions;
    private Marker currentLocationMarker;
    private PendingIntent pendingIntent;
    public static final String GEOFENCE_ID = "DOSEN";
    public static final float GEOFENCE_RADIUS_IN_METERS = 30;
    public final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();
    private GeofencingClient geofencingClient;
    SessionManager sessionManager;



    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBackgroundService.LocalBinder binder = (MyBackgroundService.LocalBinder)iBinder;
            myService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myService = null;
            mBound = false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_dosen);

        LocalBroadcastManager lbc = LocalBroadcastManager.getInstance(this);
        LokasiDosenBR ldos = new LokasiDosenBR(this);
        lbc.registerReceiver(ldos, new IntentFilter("antriangeofence"));
        //Anything with this intent will be sent to this receiver


        db = FirebaseDatabase.getInstance().getReference("UsersLoc");

        sessionManager = new SessionManager(this);

        //Geofence Dosen [PCR]
         AREA_LANDMARKS.put(GEOFENCE_ID, new LatLng(0.5705569604873754, 101.42538271682268));

        geofencingClient = LocationServices.getGeofencingClient(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        Dexter.withActivity(this).withPermissions(Arrays.asList(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                btn_a = findViewById(R.id.bt_getlok);
                btn_b = findViewById(R.id.bt_simpanlok);

                btn_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myService.reqLocUpdates();
                        startGeofencing();
                        btn_a.setBackgroundResource(R.drawable.btn_bg55);

                    }

                });

                btn_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myService.removeLocUpdates();
                        stopGeoFencing();
                        btn_a.setBackgroundResource(R.drawable.btn_bg44);
                    }
                });

                setButtonState(Common.ReqLocUpdates(LokasiDosen.this));
                bindService(new Intent(LokasiDosen.this,
                                MyBackgroundService.class),
                        mServiceConnection,
                        Context.BIND_AUTO_CREATE);
            }


            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

    }//tutup onCreate

    public class LokasiDosenBR extends BroadcastReceiver {

        LokasiDosen mActivity;


        public LokasiDosenBR(AppCompatActivity activity){
            mActivity = (LokasiDosen) activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

            String namanya = userDetails.get(SessionManager.KEY_NAMA);
            String nomornya = userDetails.get(SessionManager.KEY_NOMOR);


            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ProsesBim") ;
            Query getDosen = dbRef.orderByChild("id_mhs").equalTo(nomornya);


            hasil_geofence = intent.getStringExtra("Key");
            Log.i("HASIL BROADCAST RECEIVER", hasil_geofence);

            String loc_id=db.child("UsersLoc").push().getKey();

            int res_m_int = Integer.valueOf(res_m.intValue());
            int nomor_int = Integer.valueOf(nomornya);


            getDosen.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        iddos = dataSnapshot.child("id_dos").getValue(String.class); //6
                        namados = dataSnapshot.child("to_dosen").getValue(String.class); //6
                        idmhs = dataSnapshot.child("id_mhs").getValue(String.class); //6
                        tglbim = dataSnapshot.child("tanggal").getValue(String.class); //6

                        if (idmhs.equals(nomornya)) {
                            if (tglbim.equals(tanggal)) { //berhasil
                                Helper_LocJarak data = new Helper_LocJarak(loc_id, namanya, hasil_geofence, tanggal, waktu, iddos, namados, nomor_int, res_m_int);
                                db.child(nomornya).setValue(data);
                                Toast.makeText(LokasiDosen.this, "Berhasil Checkin Kehadiran", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }

    private void startGeofencing() {
        Log.d(TAG, "Start geofencing monitoring call");
        pendingIntent = getGeofencePendingIntent();
        geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
                .addGeofence(getGeofence())
                .build();


        if (!googleApiClient.isConnected()) {
            Log.d(TAG, "Google API client not connected");
        } else {
            try {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d(TAG, "Successfully Geofencing Connected");
                        } else {
                            Log.d(TAG, "Failed to add Geofencing " + status.getStatus());
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }
        }
        isMonitoring = true;
        invalidateOptionsMenu();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceRegistrationService.class);
        pendingIntent = PendingIntent.getService(LokasiDosen.this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private Geofence getGeofence() {
        LatLng latLng = AREA_LANDMARKS.get(GEOFENCE_ID);
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_ID)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(latLng.latitude, latLng.longitude, GEOFENCE_RADIUS_IN_METERS)
                .setNotificationResponsiveness(1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private void stopGeoFencing() {
        pendingIntent = getGeofencePendingIntent();
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, pendingIntent)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess())
                            Log.d(TAG, "Stop geofencing");
                        else
                            Log.d(TAG, "Not stop geofencing");
                    }
                });
        isMonitoring = false;
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LokasiDosen.this);
        if (response != ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Service Not Available");
            GoogleApiAvailability.getInstance().getErrorDialog(LokasiDosen.this, response, 1).show();
        } else {
            Log.d(TAG, "Google play service available");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);
        googleApiClient.reconnect();
    }

    @Override
    protected void onStop() {
        if(mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);
        super.onStop();
        googleApiClient.disconnect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(Common.KEY_REQUESTING_LOCATION_UPDATES)){
            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATES,false));
        }
    }

    private void setButtonState(boolean isRequestEnable) {
        if(isRequestEnable){
            btn_a.setEnabled(false);
        } else {
            btn_a.setEnabled(true);

        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)

    public void onListenLocation(SendLocationToActivity event){
        if(event !=null){
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            loclat = event.getLocation().getLatitude(); //latitude lbs
            loclong = event.getLocation().getLongitude(); //longitude lbs
             Toast.makeText(myService, data, Toast.LENGTH_SHORT).show();

            Double pi = 3.14159265358979;
            //ambil lokasi mhs = loclat, loclong -> diasumsikan lat long user
            //ambil lokasi geofance dosen = 0.5638900815414866, 101.44237184320527 -> diasumsikan lat long tujuan
            Double loclat_dosen = 0.5638900815414866;
            Double loclong_dosen = 101.44237184320527;
            //buat variable radius bumi
            Double rad = 6371e3;

            Double latRad1 = loclat_dosen * (pi/180);
            Double latRad2 = loclat * (pi/180);
            Double deltaLatRad = (loclat - loclat_dosen) * (pi/180);
            Double deltaLongRad = (loclong - loclong_dosen) * (pi/180);

            //rumus haversine
            Double a = Math.sin(deltaLatRad/2) * Math.sin(deltaLatRad/2) + Math.cos(latRad1) * Math.cos(latRad2)
                    * Math.sin(deltaLongRad/2) * Math.sin(deltaLongRad/2);
            Double c = Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            //mendapatkan hasil jarak dalam meter
            res_m = rad * c;

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Api Client Connected");

        isMonitoring = true;


    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Connection Suspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isMonitoring = false;
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());

    }
}