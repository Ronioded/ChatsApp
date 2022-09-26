package com.example.chatsandroid.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey
    private int Id;
    private String ContactUserName;
    private String ConnectedUserName;
    private String Content;
    private String TimeStamp;
    private boolean Sent;

    public Message(int Id, String ContactUserName, String ConnectedUserName, String Content, String TimeStamp, boolean Sent) {
        this.Id = Id;
        this.ContactUserName = ContactUserName;
        this.ConnectedUserName = ConnectedUserName;
        this.Content = Content;
        this.TimeStamp = TimeStamp;
        this.Sent = Sent;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContactUserName() {
        return ContactUserName;
    }

    public void setContactUserName(String contactUserName) {
        ContactUserName = contactUserName;
    }

    public String getConnectedUserName() {
        return ConnectedUserName;
    }

    public void setConnectedUserName(String connectedUserName) {
        ConnectedUserName = connectedUserName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }
}
