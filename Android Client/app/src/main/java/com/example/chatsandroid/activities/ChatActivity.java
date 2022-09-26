package com.example.chatsandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsandroid.R;
import com.example.chatsandroid.adapters.MessageAdapter;
import com.example.chatsandroid.dataBase.messages.MessagesViewModel;
import com.example.chatsandroid.entities.Message;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String contactUserName;
    private String connectedUserName;
    private EditText newMessage;
    private MessagesViewModel messagesViewModel;
    private MessageAdapter messageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        // Get details from the intent.
        Intent intent = getIntent();
        connectedUserName = intent.getExtras().getString("connectedUserName");
        String connectedNickName = intent.getExtras().getString("connectedNickName");
        contactUserName = intent.getExtras().getString("contactUserName");
        String contactNickName = intent.getExtras().getString("contactNickName");

        // Get elements by Id and define chat title.
        newMessage = findViewById(R.id.new_message);
        TextView chatTitle = findViewById(R.id.chat_title);
        chatTitle.setText("Chat with " + contactNickName);

        // Send message when enter key pressed.
        newMessage.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                sendMessage();
                return true;
            }
            return false;
        });

        // Send message when the button pressed.
        ImageButton sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(v-> {
            sendMessage();
        });

        // When go back button pressed, go back to chatsList activity.
        ImageButton goBack = findViewById(R.id.go_back_button);
        goBack.setOnClickListener(v-> {
            int size = messagesViewModel.getMessagesList().getValue().size();
            Intent goBackIntent = new Intent(this, ChatsListActivity.class);
            goBackIntent.putExtra("UserName", connectedUserName);
            goBackIntent.putExtra("NickName", connectedNickName);
            goBackIntent.putExtra("contactUserName", contactUserName);
            if (size > 0) {
                goBackIntent.putExtra("LastMessage", messagesViewModel.getMessagesList().getValue().get(size - 1).getContent());
                goBackIntent.putExtra("LastMessageDate", messagesViewModel.getMessagesList().getValue().get(size - 1).getTimeStamp());
            } else {
                goBackIntent.putExtra("LastMessage", "");
                goBackIntent.putExtra("LastMessageDate", "");
            }
            startActivity(goBackIntent);
        });

        // Initialize view model and recycle view.
        messagesViewModel = new MessagesViewModel();
        messagesViewModel.setList(connectedUserName, contactUserName, true);
        initializeRecycleView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private void sendMessage() {
        if (!newMessage.getText().toString().isEmpty()) {
            boolean flag = false;
            List<Message> messageList = messagesViewModel.getMessagesList().getValue();
            if ((messageList != null) && (messageList.size() == 0)) {
                flag = true;
            }
            String content = newMessage.getText().toString();
            LocalDateTime dateTime = LocalDateTime.now(); // Gets the current date and time
            ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of( "Asia/Jerusalem"));
            messagesViewModel.add(new Message(
                    messagesViewModel.getMaxId() + 1,
                    contactUserName,
                    connectedUserName,
                    content,
                    zonedDateTime.toString(),
                    true), true);

            if (flag) {
                initializeRecycleView();
            }
        }
        newMessage.setText(null);
    }

    private void initializeRecycleView(){
        List<Message> messageList = messagesViewModel.getMessagesList().getValue();
        RecyclerView messagesChat = findViewById(R.id.messageList);
        messagesChat.setNestedScrollingEnabled(false);
        messagesChat.setHasFixedSize(false);
        messageAdapter = new MessageAdapter(this, messageList);
        if (messageList != null) {
            try {
                messagesChat.smoothScrollToPosition(messageList.size() - 1);
            } catch (Exception e) {}
        }
        messagesChat.setAdapter(messageAdapter);
        messagesChat.setLayoutManager(new LinearLayoutManager(this));
        messagesViewModel.getMessagesList().observe(this, messages -> {
            messageAdapter.setMessageList(messages);
            try {
                messagesChat.smoothScrollToPosition(messages.size() - 1);
            } catch (Exception e) {}
        });
    }
}