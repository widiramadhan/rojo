package com.rojo.travel.tour;

public class CountryDataSet {



    String id_provinsi, nama_provinsi, negara_id;

    public CountryDataSet(){}

    public CountryDataSet(String id_provinsi, String nama_provinsi, String negara_id) {
        this.id_provinsi = id_provinsi;
        this.nama_provinsi = nama_provinsi;
        this.negara_id = negara_id;
    }


    public String getId_provinsi() {
        return id_provinsi;
    }

    public void setId_provinsi(String id_provinsi) {
        this.id_provinsi = id_provinsi;
    }

    public String getNama_provinsi() {
        return nama_provinsi;
    }

    public void setNama_provinsi(String nama_provinsi) {
        this.nama_provinsi = nama_provinsi;
    }

    public String getNegara_id() {
        return negara_id;
    }

    public void setNegara_id(String negara_id) {
        this.negara_id = negara_id;
    }
}
