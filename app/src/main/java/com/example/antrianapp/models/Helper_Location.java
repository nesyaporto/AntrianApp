package com.example.antrianapp.models;

import java.util.Date;

public class Helper_Location {

    String id, nomor, nama, status, tgl, jam;

    public Helper_Location() {
    }

    public Helper_Location(String id, String nomor, String nama, String status, String tgl, String jam) {
        this.id = id;
        this.nomor = nomor;
        this.nama = nama;
        this.status = status;
        this.tgl = tgl;
        this.jam = jam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
