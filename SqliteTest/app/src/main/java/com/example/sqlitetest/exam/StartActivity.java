package com.example.sqlitetest.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sqlitetest.MainActivity;
import com.example.sqlitetest.R;
import com.example.sqlitetest.test.SqliteActivity;

public class StartActivity extends AppCompatActivity {

    private Button btnExam;
    private Button btnData;
    private Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnData = findViewById(R.id.btn_data);
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StartActivity.this, SqliteActivity.class);
                startActivity(in);
            }
        });

        btnExam = findViewById(R.id.btn_exam);
        btnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StartActivity.this, ExamActivity.class);
                startActivity(in);
            }
        });

        btnQuit = findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StartActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

    }
}