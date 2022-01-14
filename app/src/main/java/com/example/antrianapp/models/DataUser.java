package com.example.antrianapp.models;

import java.io.Serializable;

public class DataUser implements Serializable {

    private String nama;
    private String nomor;
    private String nohp;
    private String email;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DataUser(){}

    public DataUser(String nama, String nomor, String nohp, String email){
        this.nama=nama;
        this.nomor=nomor;
        this.nohp=nohp;
        this.email=email;
    }
}
