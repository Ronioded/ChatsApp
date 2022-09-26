package com.example.chatsandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatsandroid.R;
import com.example.chatsandroid.dataBase.contacts.ContactsViewModel;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.Utils;

import java.util.List;

public class AddContactActivity extends AppCompatActivity {
    private ContactsViewModel contactsViewModel;
    private TextView usernameError;
    private String connectedUsername;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Get the connected username from the intent and then get the contactsViewModel of the user.
        Intent intent = getIntent();
        connectedUsername = intent.getExtras().getString("UserName");
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.setConnectedUser(connectedUsername, false);

        // Set the add contact logic on click of the add contact button.
        Button button = findViewById(R.id.add_contact_button);
        button.setOnClickListener(view -> {
            EditText usernameEditText = findViewById(R.id.edit_text_username_add_contact);
            EditText nicknameEditText = findViewById(R.id.edit_text_nickname_add_contact);

            // Remove error messages and check that all fields are full.
            usernameError = findViewById(R.id.new_contact_username_error);
            TextView nicknameError = findViewById(R.id.new_contact_nickname_error);
            TextView[] textViews = { usernameError, nicknameError };
            EditText[] editTexts = { usernameEditText, nicknameEditText };
            Utils.removeErrors(textViews);
            if (Utils.isEmptyInput(editTexts, textViews)) {
                return;
            }

            // If contact exits, show error message and exit.
            String contactUsername = usernameEditText.getText().toString();
            String contactNickname = nicknameEditText.getText().toString();
            if (isContactAlreadyExist(contactUsername, contactsViewModel.get().getValue())) {
                usernameError.setVisibility(View.VISIBLE);
                usernameError.setText("Contact is already exists.");
                return;
            }

            // Add newContact to the server and localDB.
            Contact newContact =  new Contact(contactUsername, contactNickname, null, null, connectedUsername);
            contactsViewModel.addNewContact(connectedUsername, newContact, this, true);
        });

        // Define exit button on click.
        ImageButton exitBtn = findViewById(R.id.exit_button);
        exitBtn.setOnClickListener(view -> finish());
    }

    private boolean isContactAlreadyExist(String contactUsername, List<Contact> contactList) {
        if (contactList != null) {
            for (Contact c : contactList) {
                if (c.getUserName().equals(contactUsername)) {
                    return true;
                }
            }
        }
        return false;
    }

    // The method will run when adding contact was failed.
    @SuppressLint("SetTextI18n")
    public void onAddContactFailure() {
        runOnUiThread(() -> {
            usernameError.setVisibility(View.VISIBLE);
            usernameError.setText("User does not exists.");
        });
    }

    // The method will run when adding contact was succeeded.
    public void onAddContactSuccess(Contact contact) {
        contactsViewModel.addContactToLocalDB(contact);
        finish();
    }
}