package com.noubo.oldmancare.model;

/**
 * Created by Wongnoubo on 2019/3/19.
 */

public class CurrentValue {
    private double lat;
    private double lon;

    private int key;

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

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString(){
        return " lat: "+lat+" lon: "+lon+" key"+key;
    }
}
