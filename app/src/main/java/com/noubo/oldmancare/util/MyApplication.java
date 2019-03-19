package com.noubo.oldmancare.util;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.noubo.oldmancare.R;

import org.litepal.LitePal;

/**
 * Created by admin on 2019/2/4.
 */

public class MyApplication extends Application{
    //app内实现SPP协议需要服务对应的UUID，蓝牙串口服务的UUID为：
    public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private static Context mContext;
    private static String userName = null;
    private int statusBarHeight = 0;
    private int actionBarHeight = 0;
    private int tabLayoutHeight = 0;
    private int screenHeight = 0;
    private int screenWidth = 0;
    private int medicalCardHeight = 0;
    private float fabY = 0;
    //经纬度
    private double lat;
    private double lon;

    public static Context getGlobalContext(){
        return mContext;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplicationContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        //创建或者升级数据库
        LitePal.initialize(this);
       /* View view = inflate.inflate(R.layout.medical_item,null);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        medicalCardHeight = view.getMeasuredHeight();*/
    }

    public double getLat(){return lat;}

    public void setLat(double lat){this.lat = lat;}

    public double getLon(){return lon;}

    public void setLon(double lon){this.lon = lon;}
}
