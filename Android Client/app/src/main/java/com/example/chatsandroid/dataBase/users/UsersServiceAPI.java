package com.example.chatsandroid.dataBase.users;

import com.example.chatsandroid.entities.AndroidToken;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersServiceAPI {
    @POST("Users/register")
    Call<Void> register(@Body User connectedUser);

    @POST("Users/login")
    Call<Contact> login(@Body User user);

    @POST("Users/androidToken")
    Call<Void> AndroidTokenUpdate(@Body AndroidToken token);
}
