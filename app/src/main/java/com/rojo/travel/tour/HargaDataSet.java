package com.rojo.travel.tour;

/**
 * Created by kangyasin on 06/01/2018.
 */

public class HargaDataSet {

    String id_paket, paket_wisata_kelas_paket, harga_individual, harga_family, harga_group, market_paket, id_paket_wisata_kelas_paket, paket_wisata_jenis_paket, nama_tour;

    public HargaDataSet() {
    }

    public HargaDataSet(String id_paket, String paket_wisata_kelas_paket, String harga_individual, String harga_family, String harga_group, String market_paket, String id_paket_wisata_kelas_paket, String paket_wisata_jenis_paket, String nama_tour) {
        this.id_paket = id_paket;
        this.paket_wisata_kelas_paket = paket_wisata_kelas_paket;
        this.harga_individual = harga_individual;
        this.harga_family = harga_family;
        this.harga_group = harga_group;
        this.market_paket = market_paket;
        this.id_paket_wisata_kelas_paket = id_paket_wisata_kelas_paket;
        this.paket_wisata_jenis_paket = paket_wisata_jenis_paket;
        this.nama_tour = nama_tour;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getPaket_wisata_kelas_paket() {
        return paket_wisata_kelas_paket;
    }

    public void setPaket_wisata_kelas_paket(String paket_wisata_kelas_paket) {
        this.paket_wisata_kelas_paket = paket_wisata_kelas_paket;
    }

    public String getHarga_individual() {
        return harga_individual;
    }

    public void setHarga_individual(String harga_individual) {
        this.harga_individual = harga_individual;
    }

    public String getHarga_family() {
        return harga_family;
    }

    public void setHarga_family(String harga_family) {
        this.harga_family = harga_family;
    }

    public String getHarga_group() {
        return harga_group;
    }

    public void setHarga_group(String harga_group) {
        this.harga_group = harga_group;
    }

    public String getMarket_paket() {
        return market_paket;
    }

    public void setMarket_paket(String market_paket) {
        this.market_paket = market_paket;
    }

    public String getId_paket_wisata_kelas_paket() {
        return id_paket_wisata_kelas_paket;
    }

    public void setId_paket_wisata_kelas_paket(String id_paket_wisata_kelas_paket) {
        this.id_paket_wisata_kelas_paket = id_paket_wisata_kelas_paket;
    }

    public String getPaket_wisata_jenis_paket() {
        return paket_wisata_jenis_paket;
    }

    public void setPaket_wisata_jenis_paket(String paket_wisata_jenis_paket) {
        this.paket_wisata_jenis_paket = paket_wisata_jenis_paket;
    }

    public String getNama_tour() {
        return nama_tour;
    }

    public void setNama_tour(String nama_tour) {
        this.nama_tour = nama_tour;
    }
}
