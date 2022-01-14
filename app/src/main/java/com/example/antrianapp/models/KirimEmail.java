package com.example.antrianapp.models;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.antrianapp.MenuDosen;

import java.util.HashMap;

public class KirimEmail extends AppCompatActivity {

    String ini_pesan, namamhs, namados, idbim, iddos, jenisbim, tglbim, waktubim, stat_baru;
    String idmhs, email, prodi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Mendapatkan value dari activity sebelumnya
        Bundle b = getIntent().getExtras();
        namamhs = b.getString("nama_mhs");
        tglbim = b.getString("tanggal");
        waktubim = b.getString("waktu");
        jenisbim = b.getString("bimb");
        idbim = b.getString("id_bim");
        idmhs = b.getString("id_mhs");
        iddos = b.getString("id_dos");
        namados = b.getString("to_dosen");
        ini_pesan = b.getString("pesan");
        stat_baru = b.getString("status");
        email = b.getString("email");
        prodi = b.getString("prodi");


        //kirim ke email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Info Bimbingan " + jenisbim + " Mahasiswa " + namamhs);
        intent.putExtra(Intent.EXTRA_TEXT, "Detail Bimbingan :"
                +"\n\nTanggal Bimbingan : "+tglbim
                +"\nWaktu Bimbingan : "+waktubim
                +"\nStatus : "+stat_baru
                +"\nPesan : "+ini_pesan);
        intent.setType("message/rfc822");
if(intent.resolveActivity(getPackageManager()) != null){
    startActivity(intent);
} else {
    Toast.makeText(KirimEmail.this, "Tidak ada aplikasi yang mendukung",
            Toast.LENGTH_SHORT).show();

    Intent i = new Intent(KirimEmail.this, MenuDosen.class);
        startActivity(i);
}

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuDosen.class);
        startActivity(i);
    }
}
