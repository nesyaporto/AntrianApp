package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.antrianapp.models.Adapter_Location;
import com.example.antrianapp.models.Helper_LokasiTerkini;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class KehadiranMhs extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db, db2;
    Adapter_Location myAdapter;
    ArrayList<Helper_LokasiTerkini> list;

    LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran_mhs);

        db = FirebaseDatabase.getInstance().getReference("ProsesBim");
        db2 = FirebaseDatabase.getInstance().getReference("UsersLoc");

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        //set layout as Linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new Adapter_Location(this, list);
        recyclerView.setAdapter(myAdapter);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        String nomornya = userDetails.get(SessionManager.KEY_NOMOR);

        Query q = db.orderByChild("id_dos").equalTo(nomornya);

        Query cek1 = db2.orderByChild("nomor");

        cek1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot_a, @Nullable String previousChildName) {
                String a = snapshot_a.child("id").getValue(String.class);
                String b = snapshot_a.child("jam").getValue(String.class);
                String c = snapshot_a.child("nama").getValue(String.class);
                String d = snapshot_a.child("nomor").getValue(String.class);
                String e = snapshot_a.child("status").getValue(String.class);
                String f = snapshot_a.child("tgl").getValue(String.class);

                q.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String item_a = snapshot.child("bimb").getValue(String.class);
                        String item_b = snapshot.getKey();
                        String item_c = snapshot.child("id_dos").getValue(String.class);
                        String item_d = snapshot.child("id_mhs").getValue(String.class);
                        String item_e = snapshot.child("nama_mhs").getValue(String.class);
                        String item_f = snapshot.child("pesan").getValue(String.class);
                        String item_g = snapshot.child("status").getValue(String.class);
                        String item_h = snapshot.child("tanggal").getValue(String.class);
                        String item_i = snapshot.child("to_dosen").getValue(String.class);
                        String item_j = snapshot.child("waktu").getValue(String.class);

                        if(item_g.equals("Terima") & item_c.equals(nomornya)) {
                            if (item_d.equals(d) & item_e.equals(c)) {
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
                                try {
                                    Date tanggal_geofence = format.parse(f);
                                    Date tanggal_bimbingan = format.parse(item_h);
                                    Date jam_geofence = format2.parse(b);
                                    Date jam_bimbingan = format2.parse(item_j);

                                    if (tanggal_geofence.equals(tanggal_bimbingan)) {
                                        if (jam_geofence.equals(jam_bimbingan) | jam_geofence.before(jam_bimbingan)) {
//
                                            String kehadiran = "Hadir";
                                            Helper_LokasiTerkini ini = new Helper_LokasiTerkini(a,d,c,e,f,b,kehadiran);
                                            db2.child(d).setValue(ini);
                                            list.add(ini);
                                        }
                                        else if (jam_geofence.after(jam_bimbingan)) {
                                            String kehadiran = "Terlambat";
                                            Helper_LokasiTerkini ini = new Helper_LokasiTerkini(a,d,c,e,f,b,kehadiran);
                                            db2.child(d).setValue(ini);
                                            list.add(ini);
//
                                        }
                                    } else {
                                        String kehadiran = "Tanggal tidak sesuai jadwal bimbingan";
                                        Helper_LokasiTerkini ini = new Helper_LokasiTerkini(a,d,c,e,f,b,kehadiran);
                                        db2.child(d).setValue(ini);
                                        list.add(ini);
//
                                    }


                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }
                            }
                        }

                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}