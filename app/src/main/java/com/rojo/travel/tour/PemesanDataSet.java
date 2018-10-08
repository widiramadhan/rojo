package com.rojo.travel.tour;

public class PemesanDataSet {



    String namapa, phonepa, noktp_pa, firstpa, lastpa, salutepa, typepa;

    private String emailpa = "";

    public PemesanDataSet(){}

    public PemesanDataSet(String namapa, String phonepa, String noktp_pa, String firstpa, String lastpa, String salutepa, String typepa, String emailpa) {
        this.namapa = namapa;
        this.phonepa = phonepa;
        this.noktp_pa = noktp_pa;
        this.firstpa = firstpa;
        this.lastpa = lastpa;
        this.salutepa = salutepa;
        this.typepa = typepa;
        this.emailpa = emailpa;
    }

    public String getNamapa() {
        return namapa;
    }

    public void setNamapa(String namapa) {
        this.namapa = namapa;
    }

    public String getPhonepa() {
        return phonepa;
    }

    public void setPhonepa(String phonepa) {
        this.phonepa = phonepa;
    }

    public String getNoktp_pa() {
        return noktp_pa;
    }

    public void setNoktp_pa(String noktp_pa) {
        this.noktp_pa = noktp_pa;
    }

    public String getFirstpa() {
        return firstpa;
    }

    public void setFirstpa(String firstpa) {
        this.firstpa = firstpa;
    }

    public String getLastpa() {
        return lastpa;
    }

    public void setLastpa(String lastpa) {
        this.lastpa = lastpa;
    }

    public String getSalutepa() {
        return salutepa;
    }

    public void setSalutepa(String salutepa) {
        this.salutepa = salutepa;
    }

    public String getTypepa() {
        return typepa;
    }

    public void setTypepa(String typepa) {
        this.typepa = typepa;
    }

    public String getEmailpa() {
        return emailpa;
    }

    public void setEmailpa(String emailpa) {
        this.emailpa = emailpa;
    }
}
