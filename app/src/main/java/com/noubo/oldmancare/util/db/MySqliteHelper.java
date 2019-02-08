package com.noubo.oldmancare.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 2019/2/8.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    public static final String TAG = "MySqliteHelper";
    public static final String CREATE_ADMIN = "create table admin ("
        +"id integer primary key autoincrement," +"username text,"+
            "password text)";
    private Context mContext;

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_ADMIN);//创建用户表
        Toast.makeText(mContext,"创建用户表成功",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onCreate创建admin数据表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("drop table if exits admin");
        onCreate(db);
        Log.d(TAG,"onUpgrade更新数据库");
    }


}
