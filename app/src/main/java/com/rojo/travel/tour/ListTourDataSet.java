package com.rojo.travel.tour;

/**
 * Created by kangyasin on 19/11/2017.
 */

public class ListTourDataSet {
    String id, image, deskripsi, judul, harga, rate, include, exclude, term, hargadouble, hargagroup;
    String[] hargapaket;

    public ListTourDataSet(){

    }

    public ListTourDataSet(String id, String image, String deskripsi, String judul, String harga, String rate, String include, String exclude, String term, String hargadouble, String hargagroup, String[] hargapaket) {
        this.id = id;
        this.image = image;
        this.deskripsi = deskripsi;
        this.judul = judul;
        this.harga = harga;
        this.rate = rate;
        this.include = include;
        this.exclude = exclude;
        this.term = term;
        this.hargadouble = hargadouble;
        this.hargagroup = hargagroup;
        this.hargapaket = hargapaket;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getHargadouble() {
        return hargadouble;
    }

    public void setHargadouble(String hargadouble) {
        this.hargadouble = hargadouble;
    }

    public String getHargagroup() {
        return hargagroup;
    }

    public void setHargagroup(String hargagroup) {
        this.hargagroup = hargagroup;
    }

    public String[] getHargapaket() {
        return hargapaket;
    }

    public void setHargapaket(String[] hargapaket) {
        this.hargapaket = hargapaket;
    }
}
