package com.noubo.oldmancare.olamancaremanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;
import com.noubo.oldmancare.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {
    private static final String TAG="AddressFragment";

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
        Log.d(TAG,"AddressFragment");
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView Text_InfoReceived = (TextView) getActivity().findViewById(R.id.Text_InfoReceived);
        Button Button_GetInfo = (Button) getActivity().findViewById(R.id.Button_GetInfo);
        Text_InfoReceived.setText("");
        //获取数据线程
        final Runnable getRunable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                receivedInfo = null;
                receivedInfo = getInfo("location");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Text_InfoReceived.append(receivedInfo + "\n\n" );
                        Log.d(TAG,"获取数据点击事件");
                    }
                });
                Looper.loop();
            }
        };

        //获取数据
        Button_GetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(getRunable).start();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
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
        Log.d(TAG,"onAttach");
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
        Log.d(TAG,"onDetach");
        mListener = null;
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
