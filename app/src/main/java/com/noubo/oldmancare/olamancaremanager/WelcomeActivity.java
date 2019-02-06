package com.noubo.oldmancare.olamancaremanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.annotation.TargetApi;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.noubo.oldmancare.R;
import com.noubo.oldmancare.util.MyApplication;
public class WelcomeActivity extends AppCompatActivity {

    private Handler mHandler;
    private static final int TIMES_UP = 0;

    private String FILE = "userNamePwd";

    private boolean autoLogin = false;
    private boolean loginSuccess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initWindow();
        setContentView(R.layout.activity_welcome);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case TIMES_UP:
                        if(autoLogin && loginSuccess){
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this, MainActivity.class);
                            getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.in_alpha,
                                    R.anim.out_alpha);
                        }else if (!loginSuccess){
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this, LoginActivity.class);
                            intent.putExtra("loginFailed", true);
                            SharedPreferences.Editor editor = getSharedPreferences(FILE, MODE_PRIVATE).edit();
                            editor.putBoolean("isMemory", false);
                            editor.putString("userName", null);
                            editor.putString("password", null);
                            editor.apply();
                            getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.in_alpha,
                                    R.anim.out_alpha);
                        }else{
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this, LoginActivity.class);
                            getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.in_alpha,
                                    R.anim.out_alpha);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        checkPassword();

        MyTimer timer = new MyTimer();
        timer.start();
    }

    private void checkPassword(){
        SharedPreferences preferences = getSharedPreferences(FILE, MODE_PRIVATE);
        autoLogin = preferences.getBoolean("isMemory", false);
        if(autoLogin){
            final String userName = preferences.getString("userName", null);
            String password = preferences.getString("password", null);
            OkHttpClient mOkHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("name", userName)
                    .add("password", password)
                    .build();
            Request request = new Request.Builder()
                    .url(getResources().getString(R.string.server_url) + "/account/verify/")
                    .post(requestBody)
                    .build();

            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e) {
                    loginSuccess = false;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if(result.equals("True")){
                        MyApplication application = (MyApplication) getApplicationContext();
                        application.setUserName(userName);
                        loginSuccess = true;
                    }else if(result.equals("False")){
                        loginSuccess = false;
                    }else{
                        loginSuccess = false;
                    }
                }
            });
        }
    }

    @TargetApi(19)
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public class MyTimer extends Thread {
        public MyTimer() {
            // TODO Auto-generated constructor stub
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(2000);
                mHandler.sendEmptyMessage(TIMES_UP);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
