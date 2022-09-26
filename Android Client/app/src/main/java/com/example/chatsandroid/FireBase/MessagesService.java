package com.example.chatsandroid.FireBase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.chatsandroid.R;
import com.example.chatsandroid.dataBase.contacts.ContactsViewModel;
import com.example.chatsandroid.dataBase.messages.MessagesViewModel;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.entities.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagesService extends FirebaseMessagingService {
    public static int notificationId;
    private NotificationManager notificationManager;
    ContactsViewModel contactsViewModel;
    MessagesViewModel messagesViewModel;

    public MessagesService() {
        notificationId = 0;
        contactsViewModel = new ContactsViewModel();
    }

        @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Create the firebase channel.
        createChannel();

        // Get the data from the message.
        String contactUserName = remoteMessage.getData().get("contactUserName");
        String connectedUserName = remoteMessage.getData().get("connectedUserName");
        String addContact = remoteMessage.getData().get("addContact");

        // If addContact in true, go to add contact.
        if (Objects.equals(addContact, "true")) {
            addContact(contactUserName, connectedUserName);
            return;
        }
        // Else, add message.
        contactsViewModel.setConnectedUser(connectedUserName, false);
        NotificationCompat.Builder builder;

        // Build the notification.
        builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Notify the notification.
        notificationManager.notify(notificationId, builder.build());
        notificationId++;

        // Update the dao with the new message.
        updateMessages(connectedUserName, contactUserName, remoteMessage);

        // Update the last message in the contact object.
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("Asia/Jerusalem"));
        contactsViewModel.updateLastMessage(contactUserName, remoteMessage.getNotification().getBody(),
                zonedDateTime.toString(), connectedUserName);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateMessages(String connectedUserName, String contactUserName, RemoteMessage remoteMessage) {
        messagesViewModel = new MessagesViewModel();
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("Asia/Jerusalem"));
        Message m = new Message(
                Integer.parseInt(remoteMessage.getData().get("messageId")),
                contactUserName,
                connectedUserName,
                remoteMessage.getNotification().getBody(),
                zonedDateTime.toString(),
                false);
        try {
            messagesViewModel.addMessageToDao(m);
        } catch(Exception e) {}
        // If the current contactUserName and connected username are the right ones, add the message to the list.
        String viewModelCurrentConnected = messagesViewModel.getConnectedUserName();
        String viewModelCurrentFriend = messagesViewModel.getContactUserName();
        if ((viewModelCurrentConnected != null) && (viewModelCurrentFriend != null)
            && (viewModelCurrentConnected.equals(connectedUserName)) && (viewModelCurrentFriend.equals(contactUserName))) {
            messagesViewModel.setList(connectedUserName, contactUserName, false);
            messagesViewModel.add(m, false);
        }
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addContact(String contactUserName, String connectedUserName){
        contactsViewModel.addNewContact(connectedUserName,
                new Contact(contactUserName, contactUserName, connectedUserName, "", ""),
                null, false);
    }
}