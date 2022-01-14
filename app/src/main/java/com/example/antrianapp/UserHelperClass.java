package com.example.antrianapp;

// ini class helper dari profil user

public class UserHelperClass {

    String nama, nomor, nohp, email, pass, level, prodi;

    public UserHelperClass() {
    }

    public UserHelperClass(String nama, String nomor, String nohp, String email, String pass, String level, String prodi) {
        this.nama = nama;
        this.nomor = nomor;
        this.nohp = nohp;
        this.email = email;
        this.pass = pass;
        this.level = level;
        this.prodi = prodi;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLevel() {
        return level;
    }

    public String setLevel(String level) {
        this.level = level;
        return level;
    }

    public String getProdi() {
        return prodi;
    }

    public String setProdi(String prodi) {
        this.prodi = prodi;
        return prodi;
    }
}
