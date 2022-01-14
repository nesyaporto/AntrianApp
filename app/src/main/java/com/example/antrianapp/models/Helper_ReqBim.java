package com.example.antrianapp.models;

public class Helper_ReqBim{

    String tanggal,waktu, to_dosen, bimb, id_mhs, nama_mhs, id_dos, id_bim, status, pesan, prodi;

    public Helper_ReqBim(String tanggal, String waktu, String to_dosen, String bimb, String id_mhs, String nama_mhs, String id_dos, String id_bim, String status, String pesan, String prodi) {
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.to_dosen = to_dosen;
        this.bimb = bimb;
        this.id_mhs = id_mhs;
        this.nama_mhs = nama_mhs;
        this.id_dos = id_dos;
        this.id_bim = id_bim;
        this.status = status;
        this.pesan = pesan;
        this.prodi = prodi;
    }

    public Helper_ReqBim() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_bim() {
        return id_bim;
    }

    public void setId_bim(String id_bim) {
        this.id_bim = id_bim;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTo_dosen() {
        return to_dosen;
    }

    public String setTo_dosen(String to_dosen) {
        this.to_dosen = to_dosen;
        return to_dosen;
    }

    public String getBimb() {
        return bimb;
    }

    public String setBimb(String bimb) {
        this.bimb = bimb;
        return bimb;
    }

    public String getId_mhs() {
        return id_mhs;
    }

    public void setId_mhs(String id_mhs) {
        this.id_mhs = id_mhs;
    }

    public String getNama_mhs() {
        return nama_mhs;
    }

    public void setNama_mhs(String nama_mhs) {
        this.nama_mhs = nama_mhs;
    }

    public String getId_dos() {
        return id_dos;
    }

    public void setId_dos(String id_dos) {
        this.id_dos = id_dos;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    @Override
    public String toString() {
        return id_dos;
    }
}



