package com.example.antrianapp.models;

public class Helper_LocJarak implements Comparable<Helper_LocJarak>{

    String id, nama, status, tgl, jam, iddos, namados;
    int nomor,jarak;

    public Helper_LocJarak(String id, String nama, String status, String tgl, String jam, String iddos, String namados, int nomor, int jarak) {
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.tgl = tgl;
        this.jam = jam;
        this.iddos = iddos;
        this.namados = namados;
        this.nomor = nomor;
        this.jarak = jarak;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
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

    public int getJarak() {
        return jarak;
    }

    public void setJarak(int jarak) {
        this.jarak = jarak;
    }

    public String getIddos() {
        return iddos;
    }

    public void setIddos(String iddos) {
        this.iddos = iddos;
    }

    public String getNamados() {
        return namados;
    }

    public void setNamados(String namados) {
        this.namados = namados;
    }

    public void setGenerasi(int nomor){
        this.nomor = nomor;
    }

    public int getGenerasi(){
        int gen = Integer.parseInt(Integer.toString(nomor).substring(0, 2));
        return gen;
    }

    @Override
    public int compareTo(Helper_LocJarak o) {
        if(this.getJarak() == o.getJarak()) {
            return this.getNomor() < o.getNomor() ? -1 : 1;
        } else
            return this.getJarak() < o.getJarak() ? -1 : 1;
    }

}
