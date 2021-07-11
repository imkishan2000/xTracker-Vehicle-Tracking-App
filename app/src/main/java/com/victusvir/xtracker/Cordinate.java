package com.victusvir.xtracker;

public class Cordinate {
    String cordinatesId;
    double latitude;
    double longitude;

    public Cordinate() {

    }

    public Cordinate(String cordinatesId, double latitude, double longitude) {
        this.cordinatesId = cordinatesId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCordinatesId() {
        return cordinatesId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
