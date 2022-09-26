package com.example.chatsandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatsandroid.R;
import com.example.chatsandroid.dataBase.users.UsersApi;
import com.example.chatsandroid.Utils;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get elements by Id and create users view model.
        username= findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        TextView passwordsError = findViewById(R.id.passwords_error_login);
        TextView usernameError = findViewById(R.id.username_error_login);
        TextView[] textViews = { usernameError, passwordsError };
        EditText[] editTexts = { username, password };

        // Define login logic when click on loginButton.
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            // Remove error messages.
            Utils.removeErrors(textViews);

            // Check that all fields are full.
            if (Utils.isEmptyInput(editTexts, textViews)) {
                return;
            }

            // Try to login.
            UsersApi usersApi = new UsersApi();
            usersApi.login(username.getText().toString(), password.getText().toString(), this);
        });

        // Go to register page when click on goToRegisterButton.
        Button goToRegisterButton = findViewById(R.id.go_to_register_button);
        goToRegisterButton.setOnClickListener(v -> {
            Intent goToRegisterIntent = new Intent(this, RegisterActivity.class);
            startActivity(goToRegisterIntent);
        });
    }

    public void onSuccessLogin(String nickName) {
        Intent goToChatsListIntent = new Intent(this, ChatsListActivity.class);
        goToChatsListIntent.putExtra("UserName", username.getText().toString());
        goToChatsListIntent.putExtra("NickName", nickName);
        goToChatsListIntent.putExtra("From", "Login");
        startActivity(goToChatsListIntent);
    }

    @SuppressLint("SetTextI18n")
    public void onFailureLogin() {
        runOnUiThread(() -> {
            TextView serverError = findViewById(R.id.error_from_server);
            serverError.setVisibility(View.VISIBLE);
            serverError.setText("Bad username or password!!");
        });
    }
}
