package com.noubo.oldmancare.olamancaremanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

public class ForgetpasswordActivity extends AppCompatActivity {
    public static final String TAG = "ForgetpasswordActivity";
    private EditText userNameText;
    private View findpasswordButton;
    private TextView loginText;
    private TextView finduserErrorText;
    private AlertDialog alertDialog;
    private String myPassWord;
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
                                Log.d(TAG,admins.get(0).getPassword());
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
                if(!admins.toString().equals("[]")){
                    myPassWord = admins.get(0).getPassword();
                    Log.d(TAG,"找回密码："+myPassWord+"，请妥善保管");
                    initDialog();
                    showDialog(getWindow().getDecorView());
                    Log.d(TAG,"找回密码成功跳转登录");
                }
            }
        });
    }

    /*
  初始化AlertDialog
   */
    public void initDialog()
    {
        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder=new AlertDialog.Builder(ForgetpasswordActivity.this);
        //设置构造器标题
        builder.setTitle("找回密码");
        //构造器对应的图标
        builder.setIcon(R.mipmap.ic_launcher);
        //构造器内容,为对话框设置文本项(之后还有列表项的例子)
        builder.setMessage("您的密码是："+myPassWord);
        //为构造器设置确定按钮,第一个参数为按钮显示的文本信息，第二个参数为点击后的监听事件，用匿名内部类实现
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //第一个参数dialog是点击的确定按钮所属的Dialog对象,第二个参数which是按钮的标示值
                finish();//结束当前Activity
            }
        });
        //为构造器设置取消按钮,若点击按钮后不需要做任何操作则直接为第二个参数赋值null
        builder.setNegativeButton("退出",null);
        //为构造器设置一个比较中性的按钮，比如忽略、稍后提醒等
        builder.setNeutralButton("稍后提醒",null);
        //利用构造器创建AlertDialog的对象,实现实例化
        alertDialog=builder.create();
    }

    /*
    实现Button监听器的除了内部类外的方法
    点击Button时弹出AlertDialog
     */
    public void showDialog(View v)
    {
        //当AlertDialog存在实例对象并且没有在展示时
        if(alertDialog!=null&&!alertDialog.isShowing())
            alertDialog.show();
    }

    /**
     * 表示点击安卓系统返回键时回调的方法
     */
    @Override
    public void onBackPressed()
    {
        //当AlertDialog存在实例对象并且没有在展示时
        if(alertDialog!=null&&!alertDialog.isShowing())
        {
            alertDialog.show();
        }
    }
}
