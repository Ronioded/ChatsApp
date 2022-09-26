package com.example.chatsandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String UserName;
    private String NickName;
    private String LastMessageContent;
    private String LastMessageTimeStamp;
    private String ConnectedUserName;

    public Contact(int id, String userName, String nickName, String lastMessageContent, String lastMessageTimeStamp, String connectedUserName) {
        Id = id;
        UserName = userName;
        NickName = nickName;
        LastMessageContent = lastMessageContent;
        LastMessageTimeStamp = lastMessageTimeStamp;
        ConnectedUserName = connectedUserName;
    }

    public Contact(String userName, String nickName, String lastMessageContent, String lastMessageTimeStamp, String connectedUserName) {
        UserName = userName;
        NickName = nickName;
        LastMessageContent = lastMessageContent;
        LastMessageTimeStamp = lastMessageTimeStamp;
        ConnectedUserName = connectedUserName;
    }

    public Contact(String nickName) {
        NickName = nickName;
    }

    public Contact() {
        Id = 0;
        UserName = "";
        NickName = "";
        LastMessageContent = "";
        LastMessageTimeStamp = "";
        ConnectedUserName = "";
    }

    public String getConnectedUserName() {
        return ConnectedUserName;
    }

    public void setConnectedUserName(String connectedUserName) {
        ConnectedUserName = connectedUserName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getLastMessageContent() {
        return LastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        LastMessageContent = lastMessageContent;
    }

    public String getLastMessageTimeStamp() {
        return LastMessageTimeStamp;
    }

    public void setLastMessageTimeStamp(String lastMessageTimeStamp) {
        LastMessageTimeStamp = lastMessageTimeStamp;
    }

    @Override
    public boolean equals(Object obj){
        if(obj.getClass() != Contact.class){
            return false;
        }
        Contact c = (Contact) obj;
        if(!c.UserName.equals(this.UserName)){
            return false;
        }
        if(!Objects.equals(c.NickName, this.NickName)){
            return false;
        }
        if(!Objects.equals(c.LastMessageContent, this.LastMessageContent)){
            return false;
        }
        return Objects.equals(c.LastMessageTimeStamp, this.LastMessageTimeStamp);
    }
}