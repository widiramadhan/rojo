package com.rojo.travel.tour;

public class TypeDataSet {



    String id_type, nama_type;

    public TypeDataSet(){}

    public TypeDataSet(String id_type, String nama_type) {
        this.id_type = id_type;
        this.nama_type = nama_type;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getNama_type() {
        return nama_type;
    }

    public void setNama_type(String nama_type) {
        this.nama_type = nama_type;
    }
}
