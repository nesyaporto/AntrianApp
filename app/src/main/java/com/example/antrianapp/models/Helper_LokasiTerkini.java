package com.example.antrianapp.models;

public class Helper_LokasiTerkini {

    String id, nomor, nama, status, tgl, jam, kehadiran;

    public Helper_LokasiTerkini(String id, String nomor, String nama, String status, String tgl, String jam, String kehadiran) {
        this.id = id;
        this.nomor = nomor;
        this.nama = nama;
        this.status = status;
        this.tgl = tgl;
        this.jam = jam;
        this.kehadiran = kehadiran;
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

    public String getKehadiran() {
        return kehadiran;
    }

    public void setKehadiran(String kehadiran) {
        this.kehadiran = kehadiran;
    }
}
