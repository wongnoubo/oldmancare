package com.noubo.oldmancare.olamancaremanager;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noubo.oldmancare.R;
import com.noubo.oldmancare.util.PhoneFormatCheckUtils;
import com.noubo.oldmancare.model.Admin;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class ForgetpasswordActivity extends AppCompatActivity {
    public static final String TAG = "ForgetpasswordActivity";
    private EditText userNameText;
    private View findpasswordButton;
    private TextView loginText;
    private TextView finduserErrorText;
    //用户名正则式校验
    private TextWatcher TextEvent = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(userNameText.getText())){
                //这里是文本框空的情况
                findpasswordButton.setEnabled(false);
                findpasswordButton.setClickable(false);
            } else {//这里是文本框有字符了的情况

                    if(!PhoneFormatCheckUtils.isPhoneLegal(userNameText.getText().toString())){
                        finduserErrorText.setText(R.string.standardtel);
                        finduserErrorText.setVisibility(View.VISIBLE);
                        Log.d(TAG,"手机号码不正确");
                        findpasswordButton.setEnabled(false);
                        findpasswordButton.setClickable(false);
                    }else{
                            finduserErrorText.setVisibility(View.INVISIBLE);
                            findpasswordButton.setEnabled(true);
                            findpasswordButton.setClickable(true);
                            Log.d(TAG,"手机号码正确");
                            List<Admin> admins = DataSupport.where("username = ?",userNameText.getText().toString()).find(Admin.class);
                            if(admins.toString().equals("[]")){
                                Log.d(TAG,"该账号没有注册");
                                finduserErrorText.setText(R.string.notgegistername);
                                finduserErrorText.setVisibility(View.VISIBLE);
                            }else{
                                Log.d(TAG,"该账号已被注册");
                            }
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
        setContentView(R.layout.activity_forgetpassword);
        getSupportActionBar().hide();//隐藏标题栏
        Log.d(TAG,getClass().getSimpleName());

        loginText = (TextView)findViewById(R.id.forget_login);
        userNameText = (EditText)findViewById(R.id.find_user_name);
        findpasswordButton = findViewById(R.id.find_login_btn);
        finduserErrorText = (TextView)findViewById(R.id.find_login_error_text);

        findpasswordButton.setEnabled(false);
        findpasswordButton.setClickable(false);
        userNameText.addTextChangedListener(TextEvent);

        //创建数据库
        Connector.getDatabase();

        //登录监听器
        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent forgetpasswordIntent = new Intent(ForgetpasswordActivity.this,LoginActivity.class);
                startActivity(forgetpasswordIntent);
                Log.d(TAG,"跳转登录活动");
            }
        });

        findpasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                List<Admin> admins = DataSupport.where("username = ?",userNameText.getText().toString()).find(Admin.class);
                Log.d(TAG,"找回密码："+admins.get(0).getPassword());
                Intent toLoginIntent = new Intent(ForgetpasswordActivity.this,LoginActivity.class);
                startActivity(toLoginIntent);
                Log.d(TAG,"找回密码成功跳转登录");
            }
        });
    }
}
