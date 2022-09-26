package com.example.chatsandroid.dataBase.messages.messageDB;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.chatsandroid.dataBase.messages.MessagesApi;
import com.example.chatsandroid.entities.Message;
import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {
    private MessageDao dao;
    private MessagesApi messagesApi;
    private static MessagesListData messagesListData = null;
    private static MessagesRepository messagesRepository = null;
    private String connectedUsername = null;
    private String contactUserName = null;

    private MessagesRepository() {
        dao = MessageDB.getDatabase().messageDao();
        messagesApi = new MessagesApi();
        messagesListData = new MessagesListData();
    }

    // Create 1 instance of the repository.
    public static MessagesRepository getMessagesRepository() {
        if (messagesRepository == null) {
            messagesRepository = new MessagesRepository();
        }
        return messagesRepository;
    }

    public void setList(String connectedUsername, String contactUserName, boolean needToReset) {
        if ((messagesListData.getValue().size() == 0) || (needToReset)) {
            messagesListData.updateList(connectedUsername, contactUserName);
            this.connectedUsername = connectedUsername;
            this.contactUserName = contactUserName;
        }
    }

    class MessagesListData extends MutableLiveData<List<Message>> {
        public MessagesListData() {
            super();
            this.setValue(new LinkedList<>());
        }

        public void updateList(String connectedUsername, String contactUserName) {
            List<Message> messages = dao.index(contactUserName, connectedUsername);
            if (messages == null) {
                messages = new LinkedList<>();
            }
            try {
                this.setValue(messages);
            } catch (Exception e) {
                this.postValue(messages);
            }
            // Get updates from sever about the messages.
            messagesApi.getAllMessagesOnChat(this, connectedUsername, contactUserName);
        }
    }

    public LiveData<List<Message>> getAllMessagesOnChat() {
        return messagesListData;
    }

    public void addMessage(final Message message, boolean needToUpdateServer) {
        new Thread(() -> {
            try {
                dao.Insert(message);
            } catch (Exception e) {
                message.setId(dao.getMaxId() + 1);
            }
            List<Message> list = messagesListData.getValue();
            if (list == null) {
                list = new LinkedList<>();
            }
            list.add(message);
            try {
                messagesListData.setValue(list);
            } catch (Exception e) {
                messagesListData.postValue(list);
            }
        }).start();
        // If need to update server about the message, update it.
        if (needToUpdateServer) {
            messagesApi.addMessage(messagesListData, connectedUsername, contactUserName, message);
        }
    }

    public void addMessageToDao(Message m) {
        dao.Insert(m);
    }

    public int getMaxId() {
        return dao.getMaxId();
    }

    public String getConnectedUserName() {
        return connectedUsername;
    }

    public void setConnectedUserName(String connectedUsername) {
        this.connectedUsername = connectedUsername;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }
}
