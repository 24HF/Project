package com.example.sqlitetest.test;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitetest.MainActivity;
import com.example.sqlitetest.R;
import com.example.sqlitetest.exam.ExamActivity;


public class SqliteActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private Button btn_insert;
    private Button btn_query;
//    private Button
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        mContext = this;
//        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 2);
        myDBHelper = new MyDBOpenHelper(mContext, "question.db", null, 2);
        bindViews();
    }

    private void bindViews() {
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_query = (Button) findViewById(R.id.btn_query);


        btn_query.setOnClickListener(this);
        btn_insert.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        db = myDBHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btn_insert:

                db.execSQL("INSERT INTO question( question, answerA, answerB, answerC,answerD,answerE,explanation) " +
                        "VALUES('以“近月江楼听水韵”为上句，下面四个句子中哪个能作为下句与它组成对偶句？','A.临水楼台听涛声','B.逢春枯木又发芽','C.临风野陌醉花香','D.临日山崖观海潮',2,'解析：C.C属于工对，野是田野，陌是小路，与“江楼”对得上');");
                Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                sb = new StringBuilder();
                //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
                //指定查询结果的排序方式
                Cursor cursor = db.query("question", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int qid = cursor.getInt(cursor.getColumnIndex("ID"));
                        String question = cursor.getString(cursor.getColumnIndex("question"));
                        sb.append("ID：" + qid + "：" + question + "\n");
                    } while (cursor.moveToNext());
                }
                cursor.close();
                Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
                break;

        }



    }
}
