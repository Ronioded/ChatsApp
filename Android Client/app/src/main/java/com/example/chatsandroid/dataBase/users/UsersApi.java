package com.example.chatsandroid.dataBase.users;

import androidx.annotation.NonNull;

import com.example.chatsandroid.ChatsApplication;
import com.example.chatsandroid.R;
import com.example.chatsandroid.activities.LoginActivity;
import com.example.chatsandroid.activities.RegisterActivity;
import com.example.chatsandroid.entities.AndroidToken;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersApi {
    Retrofit retrofit;
    UsersServiceAPI usersServiceAPI;

    public UsersApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ChatsApplication.context.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersServiceAPI = retrofit.create(UsersServiceAPI.class);
    }

    public void register(String username, String nickname, String password, RegisterActivity registerActivity) {
        User connectedUser = new User(username, nickname, password);
        Call<Void> call = usersServiceAPI.register(connectedUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() != 200) {
                    registerActivity.onFailureRegister();
                } else {
                    registerActivity.onSuccessRegister();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                registerActivity.onFailureRegister();
            }
        });
    }

    public void login(String username, String password, LoginActivity loginActivity) {
        User connectedUser = new User(username, username, password);
        Call<Contact> call = usersServiceAPI.login(connectedUser);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(@NonNull Call<Contact> call, @NonNull Response<Contact> response) {
                if (response.code() != 200) {
                    loginActivity.onFailureLogin();
                } else {
                    Contact c = response.body();
                    if (c != null) {
                        loginActivity.onSuccessLogin(c.getNickName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                loginActivity.onFailureLogin();
            }
        });
    }

    public void AndroidTokenUpdate(String user, String token) {
        AndroidToken androidToken = new AndroidToken(token, user);
        Call<Void> call = usersServiceAPI.AndroidTokenUpdate(androidToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }
}