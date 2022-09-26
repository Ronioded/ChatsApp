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

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText nickname;
    private EditText password;
    private TextView usernameError;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get elements by Id and create users view model.
        username = findViewById(R.id.username_register);
        nickname = findViewById(R.id.nickname_register);
        password = findViewById(R.id.password_register);
        usernameError = findViewById(R.id.username_error);
        EditText password_again = findViewById(R.id.edit_text_confirm_password_register);
        TextView nicknameError = findViewById(R.id.nickname_error);
        TextView firstPasswordError = findViewById(R.id.first_password_error);
        TextView secondPasswordError = findViewById(R.id.second_password_error);
        TextView[] textViews = { usernameError, nicknameError, firstPasswordError, secondPasswordError };
        EditText[] editTexts = { username, nickname, password, password_again };

        // Set the register logic on click on register button.
        Button button = findViewById(R.id.register_button);
        button.setOnClickListener(view -> {
            // Remove error messages.
            Utils.removeErrors(textViews);

            // Check that all fields are full.
            if (Utils.isEmptyInput(editTexts, textViews)) {
                return;
            }

            // Check that the passwords equal.
            if (!password.getText().toString().equals(password_again.getText().toString())) {
                firstPasswordError.setVisibility(View.VISIBLE);
                firstPasswordError.setText("The passwords does not match, please register again.");
                secondPasswordError.setVisibility(View.VISIBLE);
                secondPasswordError.setText("The passwords does not match, please register again.");
                return;
            }

            // Check the password contain letters and numbers.
            if (!checkValidPassword(password.getText().toString())) {
                firstPasswordError.setVisibility(View.VISIBLE);
                firstPasswordError.setText("Password must contain numbers and letters. Try again!");
                secondPasswordError.setVisibility(View.VISIBLE);
                secondPasswordError.setText("Password must contain numbers and letters. Try again!");
                return;
            }

            // Update and Check on the server that there is no other user with that username.
            UsersApi usersApi = new UsersApi();
            usersApi.register(username.getText().toString(), nickname.getText().toString(),
                    password.getText().toString(), this);
        });

        // Move to login when click click here button.
        Button goToLoginButton = findViewById(R.id.go_to_login_button);
        goToLoginButton.setOnClickListener(view -> {
            Intent goToLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(goToLoginIntent);
        });
    }

    private boolean checkValidPassword(String password) {
        boolean numbers = false;
        boolean letters = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                numbers = true;
            }
            else if (Character.isLetter(password.charAt(i))) {
                letters = true;
            }
        }
        return numbers && letters;
    }

    // If register succeeded, go to chats bar.
    public void onSuccessRegister() {
        Intent goToChatsListIntent = new Intent(this, ChatsListActivity.class);
        goToChatsListIntent.putExtra("UserName", username.getText().toString());
        goToChatsListIntent.putExtra("NickName", nickname.getText().toString());
        goToChatsListIntent.putExtra("From", "Register");
        startActivity(goToChatsListIntent);
    }

    // If register failed, show error message.
    @SuppressLint("SetTextI18n")
    public void onFailureRegister() {
        runOnUiThread(() -> {
            usernameError.setVisibility(View.VISIBLE);
            usernameError.setText("The username is already taken!!!");
        });
    }
}