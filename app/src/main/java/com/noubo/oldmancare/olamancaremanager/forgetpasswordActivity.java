package com.noubo.oldmancare.olamancaremanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.noubo.oldmancare.R;

public class ForgetpasswordActivity extends AppCompatActivity {
    public static final String TAG = "ForgetpasswordActivity";
    private EditText userNameText;
    private View findpasswordButton;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        getSupportActionBar().hide();//隐藏标题栏
        Log.d(TAG,getClass().getSimpleName());

        loginText = (TextView)findViewById(R.id.forget_login);

        //登录监听器
        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent forgetpasswordIntent = new Intent(ForgetpasswordActivity.this,LoginActivity.class);
                startActivity(forgetpasswordIntent);
                Log.d(TAG,"跳转登录活动");
            }
        });
    }
}
