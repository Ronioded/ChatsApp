package com.example.chatsandroid.entities;

public class User {
    private String UserName;
    private String NickName;
    private String Password;

    public User(String UserName, String NickName, String Password) {
        this.UserName = UserName;
        this.NickName = NickName;
        this.Password = Password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        this.NickName = nickName;
    }
}