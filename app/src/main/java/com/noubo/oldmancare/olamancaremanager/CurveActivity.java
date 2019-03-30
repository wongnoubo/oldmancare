package com.noubo.oldmancare.olamancaremanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noubo.oldmancare.R;

public class CurveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
        getSupportActionBar().hide();//隐藏标题栏
    }
}
