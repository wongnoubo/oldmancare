package com.noubo.oldmancare.olamancaremanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.noubo.oldmancare.R;
import com.noubo.oldmancare.util.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private static final int LOGIN_SUCCESS = 0;
    private static final int CONNECT_FAILED = 1;
    private static final int LOGIN_FAILED = 2;
    private static final int UNKNOWN_ERROR = 3;
    private TextView loginErrorText;
    private TextView passwordErrorText;
    private TextView registerText;
    private TextView forgetPassword;
    private EditText userNameText;
    private EditText passwordText;
    private View loginButton;
    private Handler mHandler;
    private String userName = null;
    private String password = null;

    private String FILE = "userNamePwd";

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

        mHandler =  new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOGIN_SUCCESS:
                        SharedPreferences.Editor editor = getSharedPreferences(FILE, MODE_PRIVATE).edit();
                        editor.putBoolean("isMemory", true);
                        editor.putString("userName", userName);
                        editor.putString("password", password);
                        editor.apply();
                        loginButton.setBackgroundResource(R.drawable.red_btn);
                        loginErrorText.setVisibility(View.INVISIBLE);
                        passwordErrorText.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case CONNECT_FAILED:
                        loginErrorText.setText(R.string.connect_error);
                        loginErrorText.setVisibility(View.VISIBLE);
                        passwordErrorText.setVisibility(View.INVISIBLE);
                        loginButton.setBackgroundResource(R.drawable.red_btn);
                        loginButton.setClickable(true);
                        break;
                    case LOGIN_FAILED:
                        loginErrorText.setText(R.string.login_error);
                        loginErrorText.setVisibility(View.VISIBLE);
                        passwordErrorText.setVisibility(View.INVISIBLE);
                        loginButton.setBackgroundResource(R.drawable.red_btn);
                        loginButton.setClickable(true);
                        break;
                    case UNKNOWN_ERROR:
                        loginErrorText.setText(R.string.unknown_error);
                        loginErrorText.setVisibility(View.VISIBLE);
                        passwordErrorText.setVisibility(View.INVISIBLE);
                        loginButton.setBackgroundResource(R.drawable.red_btn);
                        loginButton.setClickable(true);
                        break;
                    default:
                        break;
                }
            }
        };

        boolean loginFailed = getIntent().getBooleanExtra("loginFailed", false);
        if(loginFailed){
            Snackbar.make(loginButton, getResources().getString(R.string.account_change),
                    Snackbar.LENGTH_SHORT).show();//snackbar短时间显示
        }

        userNameText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" ")||source.equals("\t")||source.toString().contentEquals("\n"))return "";
                else return null;
            }
        }});
        //获取组件并为之设置监听器
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setBackgroundResource(R.drawable.red_card);
                loginButton.setClickable(false);
                loginErrorText.setVisibility(View.INVISIBLE);
                passwordErrorText.setVisibility(View.INVISIBLE);
                checkPassword();
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

    private void checkPassword(){
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();

        if(userName.equals("")){
            loginErrorText.setText(R.string.empty_username);
            loginErrorText.setVisibility(View.VISIBLE);
            passwordErrorText.setVisibility(View.INVISIBLE);
            loginButton.setBackgroundResource(R.drawable.red_btn);
            loginButton.setClickable(true);
            return;
        }else if(password.equals("")){
            loginErrorText.setVisibility(View.INVISIBLE);
            passwordErrorText.setVisibility(View.VISIBLE);
            loginButton.setBackgroundResource(R.drawable.red_btn);
            loginButton.setClickable(true);
            return;
        }

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
            //成功
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = CONNECT_FAILED;
                mHandler.sendMessage(message);
            }
            //失败
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if(result.equals("True")){
                    MyApplication application = (MyApplication) getApplicationContext();
                    application.setUserName(userName);
                    Message message = new Message();
                    message.what = LOGIN_SUCCESS;
                    mHandler.sendMessage(message);
                }else if(result.equals("False")){
                    Message message = new Message();
                    message.what = LOGIN_FAILED;
                    mHandler.sendMessage(message);
                }else{
                    Message message = new Message();
                    message.what = UNKNOWN_ERROR;
                    mHandler.sendMessage(message);
                }
            }
        });
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
