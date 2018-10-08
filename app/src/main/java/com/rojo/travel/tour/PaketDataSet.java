package com.rojo.travel.tour;

public class PaketDataSet {



    String id_paket, nama_paket;

    public PaketDataSet(){}

    public PaketDataSet(String id_paket, String nama_paket) {
        this.id_paket = id_paket;
        this.nama_paket = nama_paket;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }
}
