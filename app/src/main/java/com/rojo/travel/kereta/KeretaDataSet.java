package com.rojo.travel.kereta;

/**
 * Created by limadawai on 11/9/17.
 */

public class KeretaDataSet {

    String token, train_id, train_name, departure_station, arrival_station, dep_city, arr_city,
            departure_time, arrival_time, class_name_lang, price_total, duration, formatted_schedule_date, detail_availability,
            subClass, dateDep, dateArr, id_kereta;


    public KeretaDataSet(){}

    public KeretaDataSet(String token, String train_id, String train_name, String departure_station, String arrival_station, String dep_city, String arr_city, String departure_time, String arrival_time, String class_name_lang, String price_total, String duration, String formatted_schedule_date, String detail_availability, String subClass, String dateDep, String dateArr, String id_kereta) {
        this.token = token;
        this.train_id = train_id;
        this.train_name = train_name;
        this.departure_station = departure_station;
        this.arrival_station = arrival_station;
        this.dep_city = dep_city;
        this.arr_city = arr_city;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.class_name_lang = class_name_lang;
        this.price_total = price_total;
        this.duration = duration;
        this.formatted_schedule_date = formatted_schedule_date;
        this.detail_availability = detail_availability;
        this.subClass = subClass;
        this.dateDep = dateDep;
        this.dateArr = dateArr;
        this.id_kereta = id_kereta;
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

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(String arrival_station) {
        this.arrival_station = arrival_station;
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

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getClass_name_lang() {
        return class_name_lang;
    }

    public void setClass_name_lang(String class_name_lang) {
        this.class_name_lang = class_name_lang;
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

    public String getDetail_availability() {
        return detail_availability;
    }

    public void setDetail_availability(String detail_availability) {
        this.detail_availability = detail_availability;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getDateDep() {
        return dateDep;
    }

    public void setDateDep(String dateDep) {
        this.dateDep = dateDep;
    }

    public String getDateArr() {
        return dateArr;
    }

    public void setDateArr(String dateArr) {
        this.dateArr = dateArr;
    }

    public String getId_kereta() {
        return id_kereta;
    }

    public void setId_kereta(String id_kereta) {
        this.id_kereta = id_kereta;
    }
}
