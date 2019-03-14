package com.noubo.oldmancare.Bean;

import java.util.Date;

/**
 * Created by Wongnoubo on 2019/3/14.
 */

public class OldManLocBean {
    private String oldManId;
    private Date date;
    private double longitude;
    private double latitude;

    public String getOldManId() {
        return oldManId;
    }

    public void setOldManId(String oldManId) {
        this.oldManId = oldManId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
