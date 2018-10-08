package com.rojo.travel.order;

/**
 * Created by limadawai on 27/05/18.
 */

public class ListKeretaDataSet {

    String namaKereta, tglBrgkt, tujuan;

    public ListKeretaDataSet(){}

    public ListKeretaDataSet(String namaKereta, String tglBrgkt, String tujuan) {
        this.namaKereta = namaKereta;
        this.tglBrgkt = tglBrgkt;
        this.tujuan = tujuan;
    }

    public String getNamaKereta() {
        return namaKereta;
    }

    public void setNamaKereta(String namaKereta) {
        this.namaKereta = namaKereta;
    }

    public String getTglBrgkt() {
        return tglBrgkt;
    }

    public void setTglBrgkt(String tglBrgkt) {
        this.tglBrgkt = tglBrgkt;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
}
