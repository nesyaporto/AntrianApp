package com.example.antrianapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText regName, regNi, regNoHP, regEmail, regPass;
    Button regBtn;
    Spinner spinner,spinner2;
    DatabaseReference databaseReference;
    String item, item_prodi, regProdi;
    UserHelperClass userHelperClass;

    private ProgressDialog mLoadingBar;

    String[] levels = {"Dosen", "Mahasiswa"};
    String[] prodis = {"TI", "SI", "TK"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.nama_daftar);
        regNi = findViewById(R.id.nomorinduk_daftar);
        regNoHP = findViewById(R.id.nohp_daftar);
        regEmail = findViewById(R.id.email_daftar);
        regPass = findViewById(R.id.pass_daftar);
        regBtn = findViewById(R.id.bt_daftar);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(this);
        mLoadingBar = new ProgressDialog(Register.this);

        userHelperClass = new UserHelperClass();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levels);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prodis);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner2.setAdapter(arrayAdapter2);


        //Klik Registrasi
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("Users");

                mLoadingBar.setTitle("Register");
                mLoadingBar.setMessage("Mohon Tunggu, Sedang Memproses Pendaftaran");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();

                //Get all the values
                String nama = regName.getText().toString();
                String nomor = regNi.getText().toString();
                //nohp not required
                String nohp = regNoHP.getText().toString();
                String email = regEmail.getText().toString();
                String pass = regPass.getText().toString();


                   if (nama.isEmpty()) {
                       mLoadingBar.cancel();
                        regName.setError("Nama Wajib Diisi");
                      return;
                }

                    if (nomor.isEmpty()) {
                        mLoadingBar.cancel();
                        regNi.setError("Nomor Induk Wajib Diisi");
                        return;
                }

                    if (!email.contains("pcr.ac.id")) {
                        mLoadingBar.cancel();
                        regEmail.setError("Wajib Menggunakan Email PCR");
                        return;
                    }

                    if (pass.length()<6) {
                        mLoadingBar.cancel();
                        regPass.setError("Minimal Password 6 Karakter");
                        return;
                    }

                item = spinner.getSelectedItem().toString();
                String level = userHelperClass.setLevel(item);

                item_prodi = spinner2.getSelectedItem().toString();
                regProdi = userHelperClass.setProdi(item_prodi);


                if (!nama.isEmpty() | !nomor.isEmpty() | !email.isEmpty() | !pass.isEmpty() | spinner.getSelectedItem() !=null){
                    UserHelperClass helperClass = new UserHelperClass(nama,  nomor,  nohp,  email,  pass, level, regProdi);
                    databaseReference.child(nomor).setValue(helperClass);
                };
                Toast.makeText(Register.this, "Berhasil Melakukan Pendaftaran", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Register.this, LogIn.class);
                startActivity(i);
            }

        });

        TextView btn = findViewById(R.id.masukLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LogIn.class));
            }
        });
    }

    //Spinner Selected Level User
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}//tutup

