package com.example.chatsandroid;

import android.app.Application;
import android.content.Context;

// The class keeps the application context.
public class ChatsApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
