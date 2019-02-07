package com.noubo.oldmancare.olamancaremanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.noubo.oldmancare.R;

public class ForgetpasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        getSupportActionBar().hide();//隐藏标题栏
        Log.d("ForgetpasswordActivity",getClass().getSimpleName());

    }
}
