package com.rojo.travel.order;

/**
 * Created by kangyasin on 27/01/2018.
 */

public class ListPesawatDataSet {

    String tiketId, tiketInvoice, totalPrice, flightDepCityName, arrCityName, flightDateStr, depTime, airlinesName, depCityCode, depCityName, order_id;

    public ListPesawatDataSet(){}

    public ListPesawatDataSet(String tiketId, String tiketInvoice, String totalPrice, String flightDepCityName, String arrCityName, String flightDateStr, String depTime, String airlinesName, String depCityCode, String depCityName, String order_id) {
        this.tiketId = tiketId;
        this.tiketInvoice = tiketInvoice;
        this.totalPrice = totalPrice;
        this.flightDepCityName = flightDepCityName;
        this.arrCityName = arrCityName;
        this.flightDateStr = flightDateStr;
        this.depTime = depTime;
        this.airlinesName = airlinesName;
        this.depCityCode = depCityCode;
        this.depCityName = depCityName;
        this.order_id = order_id;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getTiketId() {
        return tiketId;
    }

    public void setTiketId(String tiketId) {
        this.tiketId = tiketId;
    }

    public String getTiketInvoice() {
        return tiketInvoice;
    }

    public void setTiketInvoice(String tiketInvoice) {
        this.tiketInvoice = tiketInvoice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFlightDepCityName() {
        return flightDepCityName;
    }

    public void setFlightDepCityName(String flightDepCityName) {
        this.flightDepCityName = flightDepCityName;
    }

    public String getArrCityName() {
        return arrCityName;
    }

    public void setArrCityName(String arrCityName) {
        this.arrCityName = arrCityName;
    }

    public String getFlightDateStr() {
        return flightDateStr;
    }

    public void setFlightDateStr(String flightDateStr) {
        this.flightDateStr = flightDateStr;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getAirlinesName() {
        return airlinesName;
    }

    public void setAirlinesName(String airlinesName) {
        this.airlinesName = airlinesName;
    }

    public String getDepCityCode() {
        return depCityCode;
    }

    public void setDepCityCode(String depCityCode) {
        this.depCityCode = depCityCode;
    }

    public String getDepCityName() {
        return depCityName;
    }

    public void setDepCityName(String depCityName) {
        this.depCityName = depCityName;
    }
}
