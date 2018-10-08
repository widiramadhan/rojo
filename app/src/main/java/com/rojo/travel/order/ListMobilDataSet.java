package com.rojo.travel.order;

/**
 * Created by limadawai on 14/04/18.
 */

public class ListMobilDataSet {

    String id, nama, no_hp, email, mobil, tujuan, tanggal, permintaan_khusus, status, date_create, uid;

    public ListMobilDataSet(){}

    public ListMobilDataSet(String id, String nama, String no_hp, String email, String mobil, String tujuan, String tanggal, String permintaan_khusus, String status, String date_create, String uid) {
        this.id = id;
        this.nama = nama;
        this.no_hp = no_hp;
        this.email = email;
        this.mobil = mobil;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.permintaan_khusus = permintaan_khusus;
        this.status = status;
        this.date_create = date_create;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPermintaan_khusus() {
        return permintaan_khusus;
    }

    public void setPermintaan_khusus(String permintaan_khusus) {
        this.permintaan_khusus = permintaan_khusus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
