package com.example.sqlitetest.login.userDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
//    private static final String TAG = "ttit";
    private SQLiteDatabase db;

    public DBOpenHelper(Context context){
        super(context,"databases",null,1);
        db = getReadableDatabase();
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account VARCHAR(255)," +
                "pwd VARCHAR(255));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void add(String account, String pwd){
        db.execSQL("INSERT INTO user(account, pwd) VALUES(?,?)",new Object[]{account,pwd});
    }

    public void delete(String account, String pwd){
        db.execSQL("DELETE FROM user WHERE account = AND pwd = "+ account + pwd);
    }

    public void update(String pwd){
        db.execSQL("UPDATE user SET pwd = ?",new Object[]{pwd});
    }

    public ArrayList<User> getAllData(){
        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            list.add(new User(account,pwd));
        }
        return list;
    }


}
