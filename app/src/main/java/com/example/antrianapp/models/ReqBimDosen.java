package com.example.antrianapp.models;

// ini class untuk mengambil data nama dosen, akan diimplementasikan pada spinner pilihan nama dosen
// supaya bisa mengambil nomor induk dari nama dosen yang dipilih
// data yang ditampilkan pada spinner hanya nama dosen saja

public class ReqBimDosen {

    String idnya;
    String namanya;

    public ReqBimDosen(String idnya, String namanya) {
        this.idnya = idnya;
        this.namanya = namanya;
    }

    public String getIdnya() {
        return idnya;
    }

    public void setIdnya(String idnya) {
        this.idnya = idnya;
    }

    public String getNamanya() {
        return namanya;
    }

    public void setNamanya(String namanya) {
        this.namanya = namanya;
    }

    @Override
    public String toString() {
        return namanya;
    }
}
