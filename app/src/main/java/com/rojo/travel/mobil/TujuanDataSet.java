package com.rojo.travel.mobil;

public class TujuanDataSet {



    String id_tujuan, nama_tujuan;

    public TujuanDataSet(){}

    public TujuanDataSet(String id_tujuan, String nama_tujuan) {
        this.id_tujuan = id_tujuan;
        this.nama_tujuan = nama_tujuan;
    }

    public String getId_tujuan() {
        return id_tujuan;
    }

    public void setId_tujuan(String id_tujuan) {
        this.id_tujuan = id_tujuan;
    }

    public String getNama_tujuan() {
        return nama_tujuan;
    }

    public void setNama_tujuan(String nama_tujuan) {
        this.nama_tujuan = nama_tujuan;
    }
}
