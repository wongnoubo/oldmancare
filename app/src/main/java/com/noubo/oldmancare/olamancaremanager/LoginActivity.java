package com.noubo.oldmancare.olamancaremanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.noubo.oldmancare.R;
import com.noubo.oldmancare.model.Admin;
import com.noubo.oldmancare.util.MyApplication;
import com.noubo.oldmancare.util.PhoneFormatCheckUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private TextView loginErrorText;
    private TextView passwordErrorText;
    private TextView registerText;
    private TextView forgetPassword;
    private EditText userNameText;
    private EditText passwordText;
    private View loginButton;
    private String userName = null;
    private String password = null;

    private TextWatcher TextEvent = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(userNameText.getText())||TextUtils.isEmpty(passwordText.getText())){
                //这里是文本框空的情况
                loginButton.setEnabled(false);
                loginButton.setClickable(false);
            } else {
                List<Admin> admins = DataSupport.where("username = ?",userNameText.getText().toString()).find(Admin.class);
                if(admins.toString().equals("[]")) {
                    Log.d(TAG,"该用户名未被注册");
                    if(!PhoneFormatCheckUtils.isPhoneLegal(userNameText.getText().toString())){
                        loginErrorText.setText(R.string.standardtel);
                        loginErrorText.setVisibility(View.VISIBLE);
                        loginButton.setClickable(false);
                        loginButton.setEnabled(false);
                        Log.d(TAG,"手机号码不正确");
                    }else{
                        loginErrorText.setVisibility(View.VISIBLE);
                        loginErrorText.setText(R.string.plzregister);
                        Log.d(TAG,"该账号未注册，请注册");
                    }
                }else{
                    loginErrorText.setVisibility(View.INVISIBLE);
                    loginButton.setClickable(true);
                    loginButton.setEnabled(true);
                    Log.d(TAG,"该用户名已经注册");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LoginActivity", getClass().getSimpleName());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawable(null);// 减少重绘
        getSupportActionBar().hide();//隐藏标题栏


        //活动被回收之后数据处理
        if(savedInstanceState!=null){
            userName = savedInstanceState.getString("tempUserName");
            password = savedInstanceState.getString("tempPassWord");
            Log.d("LoginActivity",userName);
            Log.d("LoginActivity",password);
        }

        loginErrorText = (TextView)findViewById(R.id.login_error_text);
        passwordErrorText = (TextView)findViewById(R.id.password_error_text);
        userNameText = (EditText)findViewById(R.id.user_name);
        passwordText = (EditText)findViewById(R.id.password);
        registerText = (TextView)findViewById(R.id.register);
        forgetPassword = (TextView)findViewById(R.id.forget_password);
        loginButton = findViewById(R.id.login_btn);

        loginErrorText.setVisibility(View.INVISIBLE);
        passwordErrorText.setVisibility(View.INVISIBLE);
        userNameText.addTextChangedListener(TextEvent);
        passwordText.addTextChangedListener(TextEvent);
        loginButton.setEnabled(false);
        loginButton.setClickable(false);
        //获取组件并为之设置监听器
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setBackgroundResource(R.drawable.red_card);
                loginButton.setClickable(false);
                loginErrorText.setVisibility(View.INVISIBLE);
                passwordErrorText.setVisibility(View.INVISIBLE);
                if(checkPassword()){
                    Log.d(TAG,"账号密码正确");
                    Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(loginIntent);
                }else {
                    Log.d(TAG,"账号密码错误");
                    passwordErrorText.setText(R.string.password_old_error);
                    passwordErrorText.setVisibility(View.VISIBLE);
                }
            }
        });

        //注册事件监听器
        registerText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                Log.d(TAG,"点击注册事件");
            }
        });
        //忘记密码事件监听器
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetPasswordIntent = new Intent(LoginActivity.this,ForgetpasswordActivity.class);
                startActivity(forgetPasswordIntent);
                Log.d(TAG,"忘记密码");
            }
        });
    }

    private boolean checkPassword(){
        List<Admin> admins = DataSupport.where("username = ?",userNameText.getText().toString()).find(Admin.class);
        if(admins.get(0).getPassword().equals(passwordText.getText().toString())){
            return true;
        }else
            return false;
    }
    //预防活动被回收了，数据保存问题
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("tempUserName",userName);
        outState.putString("tempPassWord",password);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//对手机按键自定义操作
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
