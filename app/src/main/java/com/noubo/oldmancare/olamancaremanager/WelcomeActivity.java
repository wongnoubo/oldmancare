package com.noubo.oldmancare.olamancaremanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.annotation.TargetApi;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

import com.noubo.oldmancare.R;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}
