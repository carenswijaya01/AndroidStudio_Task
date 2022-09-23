package com.example.firebase_gmaps;

public class Maps {
    private String latitude, longitude;

    public Maps() {
    }

    public Maps(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
