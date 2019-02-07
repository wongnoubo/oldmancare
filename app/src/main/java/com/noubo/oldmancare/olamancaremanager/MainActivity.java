package com.noubo.oldmancare.olamancaremanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.noubo.oldmancare.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", getClass().getSimpleName());
        setContentView(R.layout.activity_main);
    }
}
