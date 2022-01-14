package com.example.antrianapp.models;

public class Helper_AccBim{

    public String tanggal, to_dosen, bimb, nama_mhs, id_dos, id_bim, status, pesan;
    public Integer id_mhs;

    public Helper_AccBim(String tanggal,String to_dosen, String bimb, String nama_mhs, String id_dos, String id_bim, String status, String pesan, Integer id_mhs) {
        this.tanggal = tanggal;
        this.to_dosen = to_dosen;
        this.bimb = bimb;
        this.nama_mhs = nama_mhs;
        this.id_dos = id_dos;
        this.id_bim = id_bim;
        this.status = status;
        this.pesan = pesan;
        this.id_mhs = id_mhs;
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

    public void setTo_dosen(String to_dosen) {
        this.to_dosen = to_dosen;
    }

    public String getBimb() {
        return bimb;
    }

    public void setBimb(String bimb) {
        this.bimb = bimb;
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

    public String getId_bim() {
        return id_bim;
    }

    public void setId_bim(String id_bim) {
        this.id_bim = id_bim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId_mhs() {
        return id_mhs;
    }

    public void setId_mhs(Integer id_mhs) {
        this.id_mhs = id_mhs;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

//    @Override
//    //Melakukan perbandingan terhadap id mahasiswa
//        public int compareTo(Helper_AccBim o) { //16
//
//        return this.getId_mhs() < o.getId_mhs() ? -1 : 1; //17
//    }

    @Override
    public String toString() {
        return "Helper_AccBim{" +
                "tanggal='" + tanggal + '\'' +
                ", to_dosen='" + to_dosen + '\'' +
                ", bimb='" + bimb + '\'' +
                ", nama_mhs='" + nama_mhs + '\'' +
                ", id_dos='" + id_dos + '\'' +
                ", id_bim='" + id_bim + '\'' +
                ", status='" + status + '\'' +
                ", pesan='" + pesan + '\'' +
                ", id_mhs=" + id_mhs +
                '}';
    }
}
