package com.rojo.travel.mobil;

public class MobilDataSet {



    String id_mobil, nama_mobil, tahun_mobil, supir_mobil, info_mobil, harga_mobil, image_mobil, kursi_mobil;

    public MobilDataSet(){}

    public MobilDataSet(String id_mobil, String nama_mobil, String tahun_mobil, String supir_mobil, String info_mobil, String harga_mobil, String image_mobil, String kursi_mobil) {
        this.id_mobil = id_mobil;
        this.nama_mobil = nama_mobil;
        this.tahun_mobil = tahun_mobil;
        this.supir_mobil = supir_mobil;
        this.info_mobil = info_mobil;
        this.harga_mobil = harga_mobil;
        this.image_mobil = image_mobil;
        this.kursi_mobil = kursi_mobil;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getTahun_mobil() {
        return tahun_mobil;
    }

    public void setTahun_mobil(String tahun_mobil) {
        this.tahun_mobil = tahun_mobil;
    }

    public String getSupir_mobil() {
        return supir_mobil;
    }

    public void setSupir_mobil(String supir_mobil) {
        this.supir_mobil = supir_mobil;
    }

    public String getInfo_mobil() {
        return info_mobil;
    }

    public void setInfo_mobil(String info_mobil) {
        this.info_mobil = info_mobil;
    }

    public String getHarga_mobil() {
        return harga_mobil;
    }

    public void setHarga_mobil(String harga_mobil) {
        this.harga_mobil = harga_mobil;
    }

    public String getImage_mobil() {
        return image_mobil;
    }

    public void setImage_mobil(String image_mobil) {
        this.image_mobil = image_mobil;
    }

    public String getKursi_mobil() {
        return kursi_mobil;
    }

    public void setKursi_mobil(String kursi_mobil) {
        this.kursi_mobil = kursi_mobil;
    }
}
