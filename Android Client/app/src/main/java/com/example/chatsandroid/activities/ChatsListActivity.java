package com.example.chatsandroid.activities;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatsandroid.FireBase.MessagesService;
import com.example.chatsandroid.R;
import com.example.chatsandroid.adapters.ContactsAdapter;
import com.example.chatsandroid.dataBase.contacts.ContactsViewModel;
import com.example.chatsandroid.dataBase.users.UsersApi;
import com.example.chatsandroid.entities.Contact;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

public class ChatsListActivity extends AppCompatActivity {
    private String connectedUserName;
    private List<Contact> contacts;
    private ContactsAdapter adapter;
    private UsersApi usersApi;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_list);
        usersApi = new UsersApi();

        // Get details from the intent.
        Intent intent = getIntent();
        connectedUserName = intent.getExtras().getString("UserName");
        String connectedNickName = intent.getExtras().getString("NickName");
        TextView connectedUserTextView = findViewById(R.id.connected_user);
        connectedUserTextView.setText(connectedNickName);
        
        // Create viewModel and get the contacts list.
        ContactsViewModel contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        String fromActivity = intent.getExtras().getString("From");
        boolean needToReset = ((fromActivity != null) && ((fromActivity.equals("Register")) || (fromActivity.equals("Login"))));
        contactsViewModel.setConnectedUser(connectedUserName, needToReset);
        contacts = contactsViewModel.get().getValue();

        // Create adapter, sign to observe the contacts.
        adapter = new ContactsAdapter(getApplicationContext(), contacts);
        ListView listView = findViewById(R.id.contacts_list);
        listView.setAdapter(adapter);
        contactsViewModel.get().observe(this, contacts -> {
            if (adapter.isEquals(contacts)){
                return;
            }
            adapter.clear();
            adapter.addAll(contacts);
            adapter.setContactsList(contacts);
        });

        // Set on click on each contact will go the chat.
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent goToChatIntent = new Intent(ChatsListActivity.this, ChatActivity.class);
            goToChatIntent.putExtra("contactUserName", contacts.get(position).getUserName());
            goToChatIntent.putExtra("contactNickName", contacts.get(position).getNickName());
            goToChatIntent.putExtra("connectedUserName", connectedUserName);
            goToChatIntent.putExtra("connectedNickName", connectedNickName);
            startActivity(goToChatIntent);
        });

        // If there are all this fields in the intent, we got back from the chat so need to update last message.
        String content = intent.getExtras().getString("LastMessage");
        String date = intent.getExtras().getString("LastMessageDate");
        String contactUserName = intent.getExtras().getString("contactUserName");
        if ((content != null)  && (date != null) && (contactUserName != null)) {
            contactsViewModel.updateLastMessage(contactUserName, content, date, connectedUserName);
        }

        // Set on click on the logout to go to login activity.
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            onLogout();
            Intent goToLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(goToLoginIntent);
        });

        // Set on click on the add contact to go to add contact activity.
        ImageButton addContactButton = findViewById(R.id.go_to_add_contact);
        addContactButton.setOnClickListener(view -> {
            Intent goToAddContactIntent = new Intent(this, AddContactActivity.class);
            goToAddContactIntent.putExtra("UserName", connectedUserName);
            startActivity(goToAddContactIntent);
        });

        // Update the server with the Fire base token.
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ChatsListActivity.this,
                instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                    usersApi.AndroidTokenUpdate(connectedUserName, token);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onLogout();
    }

    private void onLogout() {
        // When logout - update the server we logged out and remove all notifications.
        usersApi.AndroidTokenUpdate(connectedUserName, "");
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int numNotifications = MessagesService.notificationId;
        for (int i = 0; i < numNotifications; i++) {
            try {
                notificationManager.cancel(i);
            } catch (Exception e) {}
        }
    }
}