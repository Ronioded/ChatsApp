package com.example.chatsandroid.dataBase.messages;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.chatsandroid.ChatsApplication;
import com.example.chatsandroid.R;
import com.example.chatsandroid.dataBase.messages.messageDB.MessageDB;
import com.example.chatsandroid.dataBase.messages.messageDB.MessageDao;
import com.example.chatsandroid.entities.Message;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesApi {
    private Retrofit retrofit;
    private MessagesServiceApi messagesServiceAPI;
    private MessageDao messageDao;

    public MessagesApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ChatsApplication.context.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messagesServiceAPI = retrofit.create(MessagesServiceApi.class);
        messageDao = MessageDB.getDatabase().messageDao();
    }

    public void getAllMessagesOnChat(MutableLiveData<List<Message>> messagesListData, String connectedUsername, String contactUserName) {
        Call<List<Message>> call = messagesServiceAPI.getAllMessagesOnChat(contactUserName, connectedUsername);
        call.enqueue(new Callback<List<Message>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                new Thread(() -> {
                    // If the request didn't succeeded return.
                    if (response.code() != 200) {
                        return;
                    }
                    List<Message> messageList = response.body();
                    if (messageList == null) {
                        messageList = new LinkedList<>();
                    }

                    // Update connectedUsername and contactUserName for each contact and add to the dao.
                    for (int i = 0 ; i < messageList.size(); i++) {
                        Message message = messageList.get(i);
                        message.setConnectedUserName(connectedUsername);
                        message.setContactUserName(contactUserName);
                        Message m = messageDao.get(message.getId());
                        if (m != null) {
                            messageDao.Update(message);
                        } else {
                            messageDao.Insert(message);
                        }
                    }

                    try {
                        messagesListData.setValue(messageList);
                    } catch (Exception e) {
                        messagesListData.postValue(messageList);
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {}
        });
    }

    public void addMessage(MutableLiveData<List<Message>> messagesListData, String connectedUsername, String contactUserName, Message message) {
        Call<Void> call = messagesServiceAPI.addMessage(contactUserName, message, connectedUsername);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                // After update the message at the user, get the updated messages.
                getAllMessagesOnChat(messagesListData, connectedUsername, contactUserName);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }
}
