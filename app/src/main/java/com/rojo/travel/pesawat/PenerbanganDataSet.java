package com.rojo.travel.pesawat;

/**
 * Created by limadawai on 11/9/17.
 */

public class PenerbanganDataSet {

    String token, flight_id, domestik, airlines_name, flight_number, stop, price_value, price_adult,
            price_child, price_infant, simple_departure_time, simple_arrival_time,
            departure_city_name,  arrival_city_name,
            full_via, image, departure_flight_date_str, departure_date, return_date, airlines_real_name;

    public PenerbanganDataSet(){}

    public PenerbanganDataSet(String token, String flight_id, String domestik, String airlines_name, String flight_number,
                              String stop, String price_value, String price_adult, String price_child,
                              String price_infant, String simple_departure_time,
                              String simple_arrival_time, String departure_city_name, String arrival_city_name,
                              String full_via, String image, String departure_flight_date_str,
                              String departure_date, String return_date, String airlines_real_name) {
        this.token = token;
        this.flight_id = flight_id;
        this.domestik = domestik;
        this.airlines_name = airlines_name;
        this.flight_number = flight_number;
        this.stop = stop;
        this.price_value = price_value;
        this.price_adult = price_adult;
        this.price_child = price_child;
        this.price_infant = price_infant;
        this.simple_departure_time = simple_departure_time;
        this.simple_arrival_time = simple_arrival_time;
        this.departure_city_name = departure_city_name;
        this.arrival_city_name = arrival_city_name;
        this.full_via = full_via;
        this.image = image;
        this.departure_flight_date_str = departure_flight_date_str;
        this.departure_date = departure_date;
        this.return_date = return_date;
        this.airlines_real_name = airlines_real_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getDomestik() {
        return domestik;
    }

    public void setDomestik(String domestik) {
        this.domestik = domestik;
    }

    public String getAirlines_name() {
        return airlines_name;
    }

    public void setAirlines_name(String airlines_name) {
        this.airlines_name = airlines_name;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getPrice_value() {
        return price_value;
    }

    public void setPrice_value(String price_value) {
        this.price_value = price_value;
    }

    public String getPrice_adult() {
        return price_adult;
    }

    public void setPrice_adult(String price_adult) {
        this.price_adult = price_adult;
    }

    public String getPrice_child() {
        return price_child;
    }

    public void setPrice_child(String price_child) {
        this.price_child = price_child;
    }

    public String getPrice_infant() {
        return price_infant;
    }

    public void setPrice_infant(String price_infant) {
        this.price_infant = price_infant;
    }

    public String getSimple_departure_time() {
        return simple_departure_time;
    }

    public void setSimple_departure_time(String simple_departure_time) {
        this.simple_departure_time = simple_departure_time;
    }

    public String getSimple_arrival_time() {
        return simple_arrival_time;
    }

    public void setSimple_arrival_time(String simple_arrival_time) {
        this.simple_arrival_time = simple_arrival_time;
    }

    public String getDeparture_city_name() {
        return departure_city_name;
    }

    public void setDeparture_city_name(String departure_city_name) {
        this.departure_city_name = departure_city_name;
    }

    public String getArrival_city_name() {
        return arrival_city_name;
    }

    public void setArrival_city_name(String arrival_city_name) {
        this.arrival_city_name = arrival_city_name;
    }

    public String getFull_via() {
        return full_via;
    }

    public void setFull_via(String full_via) {
        this.full_via = full_via;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeparture_flight_date_str() {
        return departure_flight_date_str;
    }

    public void setDeparture_flight_date_str(String departure_flight_date_str) {
        this.departure_flight_date_str = departure_flight_date_str;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getAirlines_real_name() {
        return airlines_real_name;
    }

    public void setAirlines_real_name(String airlines_real_name) {
        this.airlines_real_name = airlines_real_name;
    }
}
