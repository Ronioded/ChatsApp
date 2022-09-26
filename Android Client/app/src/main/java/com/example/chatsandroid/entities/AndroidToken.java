package com.example.chatsandroid.entities;

public class AndroidToken {
    private String Token;
    private String UserName;

    public AndroidToken(String UserName, String token) {
        this.Token = UserName;
        this.UserName = token;
    }
}
