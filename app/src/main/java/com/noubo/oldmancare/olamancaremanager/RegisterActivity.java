package com.noubo.oldmancare.olamancaremanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
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


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.noubo.oldmancare.R;
import com.noubo.oldmancare.util.db.MySqliteHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private MySqliteHelper mySqliteHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private TextWatcher TextEvent = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           // if (userNameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
             if(TextUtils.isEmpty(userNameText.getText())||TextUtils.isEmpty(passwordText.getText())){
                //这里是文本框空的情况
                RegisterButton.setEnabled(false);
                RegisterButton.setClickable(false);
            } else {
                //这里是文本框有字符了的情况
                RegisterButton.setEnabled(true);
                RegisterButton.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setBackgroundDrawable(null);// 减少重绘
        Log.d("RegisterActivity", getClass().getSimpleName());

        registerErrorText = (TextView) findViewById(R.id.register_login_error_text);
        passwordErrorText = (TextView) findViewById(R.id.register_password_error_text);
        userNameText = (EditText) findViewById(R.id.register_user_name);
        passwordText = (EditText) findViewById(R.id.register_password);
        olduserText = (TextView) findViewById(R.id.old_user);
        RegisterButton = findViewById(R.id.register_login_btn);
        RegisterButton.setEnabled(false);
        RegisterButton.setClickable(false);

        registerErrorText.setVisibility(View.INVISIBLE);
        passwordErrorText.setVisibility(View.INVISIBLE);
        userNameText.addTextChangedListener(TextEvent);
        passwordText.addTextChangedListener(TextEvent);
        mySqliteHelper = new MySqliteHelper(this, "Admin.db", null, 1);

        //已有账户监听器，点击跳转
        olduserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent olduserIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(olduserIntent);
                Log.d(TAG, "返回login活动");
            }

        });
        //注册按键点击监听
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //往数据库中插入数据
                SQLiteDatabase db = mySqliteHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("username", userNameText.getText().toString());
                values.put("password", passwordText.getText().toString());
                db.insert("Admin", null, values);
                Log.d(TAG, "点击注册");
                //点击注册后自动跳转登录活动
                Intent registerToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerToLogin);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 正则匹配手机号码
     *
     * @param tel
     * @return
     */
    public boolean checkTel(String tel) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
