package com.noubo.oldmancare.model;

/**
 * Created by Wongnoubo on 2019/3/19.
 */

public class CurrentValue {
    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString(){
        return " lat: "+lat+" lon: "+lon;
    }
}
