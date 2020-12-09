package com.example.sqlitetest.exam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBService {

    private SQLiteDatabase db;

    public DBService(){
        db = SQLiteDatabase.openDatabase("/data/data/com.example.sqlitetest/databases/question.db" + "",null,SQLiteDatabase.OPEN_READWRITE);

    }

    //获取数据库中的问题
    public List<Question> getQuestion(){
        List<Question> list = new ArrayList<Question>();

        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            int count = cursor.getCount();
            for (int i = 0; i < count; i++){
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                question.answerE = cursor.getInt(cursor.getColumnIndex("answerE"));

                question.explanation = cursor.getString(cursor.getColumnIndex("explanation"));

                question.selectAnswer = -1; //表示没有选择
                list.add(question);

            }
        }
        return list;
    }

}
