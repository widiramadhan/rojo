package com.rojo.travel.tour;

public class TourDataSet {

    String id, image, deskripsi, judul, harga, include, exclude, term;

    public TourDataSet(){}



    public TourDataSet(String id, String image, String desc, String judul, String harga, String include, String exclude, String term) {
        this.id = id;
        this.image = image;
        this.deskripsi = desc;
        this.harga = harga;
        this.judul = judul;
        this.include = include;
        this.exclude = exclude;
        this.term = term;

    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
