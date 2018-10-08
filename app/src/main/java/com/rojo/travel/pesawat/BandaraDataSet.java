package com.rojo.travel.pesawat;

public class BandaraDataSet {

    String token, id, airport_name, airport_code, country_name, country_id, location_name;

    public BandaraDataSet(){}

    public BandaraDataSet(String token, String id, String airport_name, String airport_code, String country_name, String country_id, String location_name) {
        this.token = token;
        this.id = id;
        this.airport_name = airport_name;
        this.airport_code = airport_code;
        this.country_name = country_name;
        this.country_id = country_id;
        this.location_name = location_name;
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

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }
}
