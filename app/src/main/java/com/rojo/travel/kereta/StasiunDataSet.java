package com.rojo.travel.kereta;

public class StasiunDataSet {

    String token, id, station_name, city_name, station_code;

    public StasiunDataSet(){}

    public StasiunDataSet(String token, String id, String station_name, String city_name, String station_code) {
        this.token = token;
        this.id = id;
        this.station_name = station_name;
        this.city_name = city_name;
        this.station_code = station_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStation_code() {
        return station_code;
    }

    public void setStation_code(String station_code) {
        this.station_code = station_code;
    }
}
