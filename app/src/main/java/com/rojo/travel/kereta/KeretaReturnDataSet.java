package com.rojo.travel.kereta;

/**
 * Created by limadawai on 11/9/17.
 */

public class KeretaReturnDataSet {

    String token, train_id, train_name, dep_station, arr_station, dep_city, arr_city,
            dep_jam, arr_jam, class_name, price_total, duration, formatted_schedule_date, imagekereta, jumlahkursi;

    public KeretaReturnDataSet(){}

    public KeretaReturnDataSet(String token, String train_id, String train_name, String dep_station, String arr_station, String dep_city, String arr_city, String dep_jam, String arr_jam, String class_name, String price_total, String duration, String formatted_schedule_date, String imagekereta, String jumlahkursi) {
        this.token = token;
        this.train_id = train_id;
        this.train_name = train_name;
        this.dep_station = dep_station;
        this.arr_station = arr_station;
        this.dep_city = dep_city;
        this.arr_city = arr_city;
        this.dep_jam = dep_jam;
        this.arr_jam = arr_jam;
        this.class_name = class_name;
        this.price_total = price_total;
        this.duration = duration;
        this.formatted_schedule_date = formatted_schedule_date;
        this.imagekereta = imagekereta;
        this.jumlahkursi = jumlahkursi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrain_id() {
        return train_id;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getDep_station() {
        return dep_station;
    }

    public void setDep_station(String dep_station) {
        this.dep_station = dep_station;
    }

    public String getArr_station() {
        return arr_station;
    }

    public void setArr_station(String arr_station) {
        this.arr_station = arr_station;
    }

    public String getDep_city() {
        return dep_city;
    }

    public void setDep_city(String dep_city) {
        this.dep_city = dep_city;
    }

    public String getArr_city() {
        return arr_city;
    }

    public void setArr_city(String arr_city) {
        this.arr_city = arr_city;
    }

    public String getDep_jam() {
        return dep_jam;
    }

    public void setDep_jam(String dep_jam) {
        this.dep_jam = dep_jam;
    }

    public String getArr_jam() {
        return arr_jam;
    }

    public void setArr_jam(String arr_jam) {
        this.arr_jam = arr_jam;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getPrice_total() {
        return price_total;
    }

    public void setPrice_total(String price_total) {
        this.price_total = price_total;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFormatted_schedule_date() {
        return formatted_schedule_date;
    }

    public void setFormatted_schedule_date(String formatted_schedule_date) {
        this.formatted_schedule_date = formatted_schedule_date;
    }

    public String getImagekereta() {
        return imagekereta;
    }

    public void setImagekereta(String imagekereta) {
        this.imagekereta = imagekereta;
    }

    public String getJumlahkursi() {
        return jumlahkursi;
    }

    public void setJumlahkursi(String jumlahkursi) {
        this.jumlahkursi = jumlahkursi;
    }
}
