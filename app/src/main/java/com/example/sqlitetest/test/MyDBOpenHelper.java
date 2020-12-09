package com.example.sqlitetest.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "ttit";

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, null, version);
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "MyDBOpenHelper: 执行了 onCreate");
        db.execSQL("CREATE TABLE question(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question VARCHAR(255)," +
                "answerA VARCHAR(255)," +
                "answerB VARCHAR(255)," +
                "answerC VARCHAR(255)," +
                "answerD VARCHAR(255)," +
                "answerE INTEGER,"+
                "explanation VARCHAR(255));");

    }

    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "MyDBOpenHelper: 执行了onUpgrade");
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)");
    }
}
