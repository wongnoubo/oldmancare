package com.noubo.oldmancare.util;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.noubo.oldmancare.R;

/**
 * Created by admin on 2019/2/4.
 */

public class MyApplication extends Application{
    private static Context mContext;

    private int statusBarHeight = 0;
    private int actionBarHeight = 0;
    private int tabLayoutHeight = 0;
    private int screenHeight = 0;
    private int screenWidth = 0;
    private int medicalCardHeight = 0;
    private float fabY = 0;

    private static String userName = null;
    //经纬度
    private double lat;
    private double lon;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplicationContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
       /* View view = inflate.inflate(R.layout.medical_item,null);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        medicalCardHeight = view.getMeasuredHeight();*/
    }
    public static Context getGlobalContext(){
        return mContext;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public void setLat(double lat){this.lat = lat;}
    public void setLon(double lon){this.lon = lon;}
    public double getLat(){return lat;}
    public double getLon(){return lon;}
}
