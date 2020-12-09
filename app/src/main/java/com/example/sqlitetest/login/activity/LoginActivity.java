package com.example.sqlitetest.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.sqlitetest.R;
import com.example.sqlitetest.exam.StartActivity;
import com.example.sqlitetest.login.userDB.DBOpenHelper;
import com.example.sqlitetest.login.userDB.User;
import com.example.sqlitetest.login.util.StringUtils;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private DBOpenHelper mDBOpenHelper;
    private EditText mAccount;
    private EditText mPwd;
    private Button mBtnLogin;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView(){
        //初始化控件
        mBtnLogin = findViewById(R.id.btn_login2);
        mAccount = findViewById(R.id.LoginAccount);
        mPwd = findViewById(R.id.LoginPwd);

        //设置点击事件监听器
        mBtnLogin.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login2:
                String account = mAccount.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(pwd)){
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (account.equals(user.getAccount()) && pwd.equals(user.getPwd())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, StartActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "账号或密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"请输入账号或密码",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}