package com.rojo.travel.tour;

public class KelasDataSet {



    String id_kelas, nama_kelas;

    public KelasDataSet(){}

    public KelasDataSet(String id_kelas, String nama_kelas) {
        this.id_kelas = id_kelas;
        this.nama_kelas = nama_kelas;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }
}
