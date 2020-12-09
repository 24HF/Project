package com.example.sqlitetest.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitetest.MainActivity;
import com.example.sqlitetest.R;
import com.example.sqlitetest.login.userDB.DBOpenHelper;
import com.example.sqlitetest.login.util.StringUtils;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnRegister;
    private DBOpenHelper mDBOpenHelper;
    private EditText mAccount;
    private EditText mPwd;

    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView(){
        mAccount = findViewById(R.id.RegisterAccount);
        mPwd = findViewById(R.id.RegisterPwd);
        mBtnRegister = findViewById(R.id.btn_register2);

        mBtnRegister.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_register2:
                String account = mAccount.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();

                //注册验证
                if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(pwd)){
                    //用户名和密码加入到数据库中
                    mDBOpenHelper.add(account,pwd);
                    Intent intent2  = new Intent(this, MainActivity.class);
                    Toast.makeText(this,"验证通过，注册成功",Toast.LENGTH_SHORT).show();
                    startActivity(intent2);
                    finish();
                }else {
                    Toast.makeText(this,"信息不完善，注册失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}