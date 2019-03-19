package com.noubo.oldmancare.model;

/**
 * Created by Wongnoubo on 2019/3/14.
 */

public class GPSModel {
    private String errno;
    private String error;
    private Data data;

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getErrno() {
        return errno;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return " error: "+error+" errno: "+errno+" data: "+data;
    }
}
