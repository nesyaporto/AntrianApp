package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.antrianapp.models.Helper_AccBim;
import com.example.antrianapp.models.Helper_ReqBim;
import com.example.antrianapp.models.ReqBimDosen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ReqJadwalBimbingan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner, spinner_bim;
    DatabaseReference dbRef, dbRef2;
    final List<ReqBimDosen> dosens = new ArrayList<>();
    String item_dosen, item_bim, dosenklik, dosenid, bimbid, pesan;
    Helper_ReqBim helper_reqBim;
    String[] jenis = {"KP", "PA"};

    EditText etTanggal, etWaktu;

    int currentHour, currentMinute, mDay, mMonth, mYear, int_id;
    Calendar calendar;

    private BroadcastManager broadcastManager;
    String namanya,nomornya, status, prodinya, waktu;

    //fcm
    String url = "https://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_jadwal_bimbingan);

        // for sending notification to all

//        FirebaseMessaging.getInstance().subscribeToTopic("all");
        FirebaseMessaging.getInstance().subscribeToTopic(dosenid);

        requestQueue = Volley.newRequestQueue(this);




        spinner = findViewById(R.id.spinnernya);
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        etTanggal = findViewById(R.id.cari_req_tanggal);

        spinner_bim=findViewById(R.id.jenis_bim);

        spinner_bim.setOnItemSelectedListener(this);
        helper_reqBim=new Helper_ReqBim();

        //data spinner jenis bimbingan
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,jenis);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_bim.setAdapter(arrayAdapter);

        fetchData();

        broadcastManager = new BroadcastManager();
        calendar = Calendar.getInstance();

        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        currentHour=calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute=calendar.get(Calendar.MINUTE);

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ReqJadwalBimbingan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        String datestr = String.format("%02d/%02d/%04d", date ,month+1 ,year);
                        etTanggal.setText(datestr);
                        mYear = year;
                        mMonth = month;
                        mDay = date;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }

    private void fetchData() {
        //cek setiap child yang levelnya = dosen, lalu ditampilkan di spinner

        Query ceklevel = dbRef.orderByChild("level").equalTo("Dosen");

        ceklevel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot myData : snapshot.getChildren()) {
                    String id = myData.getKey();
                    String nama = myData.child("nama").getValue().toString();
                    dosens.add(new ReqBimDosen(id,nama));

                }
                ArrayAdapter<ReqBimDosen> arrayAdapterdos = new ArrayAdapter<>(ReqJadwalBimbingan.this, android.R.layout.simple_spinner_dropdown_item, dosens);
                arrayAdapterdos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapterdos);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        dosenklik = spinner.getSelectedItem().toString();
                        dosenid = dosens.get(position).getIdnya();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //klik tambah data request bimbingan
    public void TambahReqBim(View view) {

        //firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("ReqBimbingan");

        String new_tgl = etTanggal.getText().toString();


        if(new_tgl.isEmpty()){
            etTanggal.setError("Tanggal Tidak Boleh Kosong");
            return;
        }

        item_dosen  = spinner.getSelectedItem().toString();
        String pilihan_dosen = helper_reqBim.setTo_dosen(item_dosen);

        item_bim = spinner_bim.getSelectedItem().toString();
        String pilihan_bimb = helper_reqBim.setBimb(item_bim);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String > userDetails = sessionManager.getUserDetailFromSession();

        namanya = userDetails.get(SessionManager.KEY_NAMA);
        nomornya = userDetails.get(SessionManager.KEY_NOMOR);
        prodinya = userDetails.get(SessionManager.KEY_PRODI);

        int_id = Integer.parseInt(nomornya);

        //untuk nambah id
        bimbid=dbRef.push().getKey();

        //nambah status awal = Pending
        status = "Pending";

        //nambah pesan awal = Pending
        pesan = "Menunggu Konfirmasi Dosen "+pilihan_dosen;

        waktu = "Belum diatur";

        if (!new_tgl.isEmpty() |  spinner.getSelectedItem() !=null | spinner_bim.getSelectedItem() !=null){

            Helper_ReqBim helper_reqBim = new Helper_ReqBim(new_tgl, waktu, pilihan_dosen, pilihan_bimb, nomornya, namanya, dosenid, bimbid, status, pesan, prodinya);

            Helper_AccBim helper_accBim = new Helper_AccBim(new_tgl, pilihan_dosen, pilihan_bimb, namanya, dosenid, bimbid, status, pesan, int_id);

            dbRef.child(nomornya).setValue(helper_reqBim); //ini yang dipakai!!!


            //fcm 1
        FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/"+dosenid,
                namanya + " Menambahkan Request Bimbingan Baru!",
                "Konfirmasi bimbingan sekarang",
                getApplicationContext(), ReqJadwalBimbingan.this);

        fcmNotificationsSender.SendNotifications();


        };
        Toast.makeText(ReqJadwalBimbingan.this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ReqJadwalBimbingan.this, ProsesBimMhs.class);
        startActivity(i);




    }

    private void sendANotif(String prodi, String namanya, String pilihan_dosen, String pilihan_bimb, String dosenid, String nomornya) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to","/topics/"+dosenid);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", "Notifikasi Request Bimbingan "+pilihan_dosen);
            jsonObject1.put("body", namanya +  " dari prodi "+prodi+ " menambahkan request bimbingan baru untuk bimbingan "
                    +pilihan_bimb+"!");

            jsonObject.put("notif", jsonObject1);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url ,jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("content-type", "application/json");
                    map.put("authorization", "AAAAuoNn5II:APA91bHscmHH-e5iRY3UG1qILzOwE3jJQCzadKuFLq3x_qfk5mOLkSOvej6mXCDFr6kp4V51JUZG8ikGn06sbytAuK-ARgeTCEJ7N64wX59TwozVfo5cBQeH2ZtcVp9AHVGFiNUP_CnX");
                    return map;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item_bim=spinner_bim.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuMhs.class);
        startActivity(i);
    }
}