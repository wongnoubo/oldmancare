package com.noubo.oldmancare.olamancaremanager;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.noubo.oldmancare.R;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private TextView registerErrorText;
    private TextView passwordErrorText;
    private TextView olduserText;
    private EditText userNameText;
    private EditText passwordText;
    private View RegisterButton;
    private Handler mHandler;
    private String userName = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setBackgroundDrawable(null);// 减少重绘
        Log.d("RegisterActivity",getClass().getSimpleName());

        registerErrorText = (TextView)findViewById(R.id.register_login_error_text);
        passwordErrorText = (TextView)findViewById(R.id.register_password_error_text);
        userNameText = (EditText)findViewById(R.id.register_user_name);
        passwordText = (EditText)findViewById(R.id.register_password);
        olduserText = (TextView)findViewById(R.id.old_user);
        RegisterButton = findViewById(R.id.register_login_btn);

        registerErrorText.setVisibility(View.INVISIBLE);
        passwordErrorText.setVisibility(View.INVISIBLE);

        //已有账户监听器
        olduserText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent olduserIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(olduserIntent);
                Log.d(TAG,"返回login活动");
            }

        });
    }
}
