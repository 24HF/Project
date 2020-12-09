package com.example.sqlitetest.login.userDB;

public class User {
    private String account;
    private String pwd;

    public User(String account, String pwd){
        this.account = account;
        this.pwd = pwd;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(){
        this.account = account;
    }

    public String getPwd(){
        return pwd;
    }

    public void setPwd(){
        this.pwd = pwd;
    }

    public String toString(){
        return "User{" +
                "account ='" + account + '\'' +
                ", pwd ='" + pwd + '\'' +

                '}';
    }

}
