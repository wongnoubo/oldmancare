package com.noubo.oldmancare.olamancaremanager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import com.noubo.oldmancare.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;//谷歌官方推荐的HttpURLConnection
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.noubo.oldmancare.model.GPSModel;
import com.noubo.oldmancare.model.Data;
import com.noubo.oldmancare.model.CurrentValue;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment{
    MapView mMapView = null;
    AMap aMap=null;
    private static final String TAG="AddressFragment";

    double lat;
    double lon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //onenetapiKey DeviceID
    private static final String DeviceID = "518991790";
    private static final String ApiKey = "Es6uSmz5efNHODQUP=YLpm6WYos=";

    //c从Onenet接收到的信息
    private String receivedInfo;
    

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //获取数据线程
//        final Runnable getRunable = new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                receivedInfo = null;
//                receivedInfo = getInfo("location");
//
//                if (getActivity() == null)
//                    return;
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d(TAG,receivedInfo);
//                        GPSModel gpsModel = JSON.parseObject(receivedInfo,GPSModel.class);
//                        lat = gpsModel.getData().getCurrent_value().getLat();
//                        lon = gpsModel.getData().getCurrent_value().getLon();
//                        Log.d("lat",Double.toString(lat));
//                        Log.d("lon",Double.toString(lon));
//                        Log.d(TAG,"获取数据点击事件");
//                    }
//                });
//                Looper.loop();
//            }
//        };
//        new Thread(getRunable).start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_address, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (MapView) getActivity().findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        if(aMap==null){
            aMap = mMapView.getMap();
        }
        //获取数据线程
        final Runnable getRunable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                receivedInfo = null;
                receivedInfo = getInfo("location");

                if (getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,receivedInfo);
                        GPSModel gpsModel = JSON.parseObject(receivedInfo,GPSModel.class);
                        lat = gpsModel.getData().getCurrent_value().getLat();
                        lon = gpsModel.getData().getCurrent_value().getLon();
                        Log.d("lat",Double.toString(lat));
                        Log.d("lon",Double.toString(lon));
                        LatLng latLng = new LatLng(lat,lon);
                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("珠海").snippet("老人实时位置"));

                    }
                });
                Looper.loop();
            }
        };
        new Thread(getRunable).start();

        //显示定位蓝点
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //从云服务器获取数据
    public String getInfo(String dataStream){
//        Toast.makeText(MainActivity.this, "开始从云服务器获取数据", Toast.LENGTH_SHORT).show();    //提示
        String response = null;
        try{
            URL url = new URL("http://api.heclouds.com/devices/" + DeviceID + "/datastreams/" + dataStream);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(15*1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("api-key",ApiKey);
            if (connection.getResponseCode() == 200){   //返回码是200，网络正常
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while((len = inputStream.read(buffer))!=-1){
                    os.write(buffer,0,len);
                }
                inputStream.close();
                os.close();
                response = os.toString();
            }else{
                //返回码不是200，网络异常
                Toast.makeText(getActivity().getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
            }

        }catch (IOException e){
            Toast.makeText(getActivity().getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return response;

    }
}
