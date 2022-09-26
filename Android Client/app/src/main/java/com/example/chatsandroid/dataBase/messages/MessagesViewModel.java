package com.example.chatsandroid.dataBase.messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatsandroid.dataBase.messages.messageDB.MessagesRepository;
import com.example.chatsandroid.entities.Message;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MessagesRepository messageRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel() {
        this.messageRepository = MessagesRepository.getMessagesRepository();
        messages = messageRepository.getAllMessagesOnChat();
    }

    public LiveData<List<Message>> getMessagesList() {
        return messages;
    }
    
    public void add(Message m, boolean needToUpdateServer) {
        messageRepository.addMessage(m, needToUpdateServer);
    }

    public void setList(String connectedUsername, String contactUserName, boolean needToReset) {
        messageRepository.setList(connectedUsername, contactUserName, needToReset);
    }

    public int getMaxId() {
        return messageRepository.getMaxId();
    }

    public void addMessageToDao(Message m) {
        messageRepository.addMessageToDao(m);
    }

    public String getConnectedUserName() {
        return messageRepository.getConnectedUserName();
    }

    public void setConnectedUserName(String connectedUsername) {
        messageRepository.setConnectedUserName(connectedUsername);
    }

    public String getContactUserName() {
        return messageRepository.getContactUserName();
    }

    public void setContactUserName(String contactUserName) {
        messageRepository.setContactUserName(contactUserName);
    }

}
