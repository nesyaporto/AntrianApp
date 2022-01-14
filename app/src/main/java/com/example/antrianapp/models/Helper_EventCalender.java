package com.example.antrianapp.models;

public class Helper_EventCalender {

    String id, iddos, namados, tglpilihan, info;

    public Helper_EventCalender() {
    }

    public Helper_EventCalender(String id, String iddos, String namados, String tglpilihan, String info) {
        this.id = id;
        this.iddos = iddos;
        this.namados = namados;
        this.tglpilihan = tglpilihan;
        this.info = info;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTglpilihan() {
        return tglpilihan;
    }

    public void setTglpilihan(String tglpilihan) {
        this.tglpilihan = tglpilihan;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
