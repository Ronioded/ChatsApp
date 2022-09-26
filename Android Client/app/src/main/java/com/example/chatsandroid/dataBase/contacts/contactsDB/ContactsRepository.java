package com.example.chatsandroid.dataBase.contacts.contactsDB;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatsandroid.activities.AddContactActivity;
import com.example.chatsandroid.dataBase.contacts.ContactsApi;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.Utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private ContactsDao dao;
    private ContactsApi contactsApi;
    private static ContactsListData contactsListData = null;
    private static ContactsRepository contactsRepository = null;

    private ContactsRepository() {
        dao = ContactsDB.getDatabase().contactsDao();
        contactsApi = new ContactsApi();
        contactsListData = new ContactsListData();
    }

    // Create 1 instance of the repository.
    public static ContactsRepository getContactsRepository() {
        if (contactsRepository == null) {
            contactsRepository = new ContactsRepository();
        }
        return contactsRepository;
    }

    // The method create new ContactsListData in case need to reset.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setConnectedUser (String connectedUsername, boolean needToReset) {
        if ((contactsListData.getValue().size() == 0) || (needToReset)) {
            contactsListData.updateList(connectedUsername);
        }
    }

    class ContactsListData extends MutableLiveData<List<Contact>> {
        public ContactsListData() {
            super();
            this.setValue(new LinkedList<>());
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void updateList(String connectedUsername) {
            List<Contact> contacts = dao.index(connectedUsername);
            if (contacts == null) {
                contacts = new LinkedList<>();
            }
            Collections.sort(contacts, new Utils.sortByLastMessage());
            try {
                this.setValue(contacts);
            } catch (Exception e) {
                this.postValue(contacts);
            }
            contactsApi.getAllContacts(this, connectedUsername);
        }
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactsListData;
    }

    public void addContact(String username, final Contact contact, AddContactActivity addContactActivity, boolean needToUpdateServer) {
        // If need to update server about the contact, update it.
        if (needToUpdateServer) {
            contactsApi.addContact(contactsListData, contact, username, addContactActivity);
        } else {
            addContactToLocalDB(contact);
        }
    }

    public void updateLastMessage(String contactUserName, String lastMessage, String lastMessageDate, String connectedUsername) {
        new Thread(() -> {
            Contact c = dao.get(contactUserName, connectedUsername);
            // If there is a contact and the lastMessage is not equal to the new last message, update the message.
            if ((c != null) && (!lastMessage.equals(c.getLastMessageContent()) || !(lastMessageDate.equals(c.getLastMessageTimeStamp())))) {
                List<Contact> contacts = contactsListData.getValue();
                if (contacts != null) {
                    contacts.remove(c);
                } else {
                    contacts = new LinkedList<>();
                }
                c.setLastMessageContent(lastMessage);
                c.setLastMessageTimeStamp(lastMessageDate);
                dao.Update(c);
                contacts.add(0, c);
                try {
                    contactsListData.setValue(contacts);
                } catch (Exception e) {
                    contactsListData.postValue(contacts);
                }
            }
        }).start();
    }

    public void addContactToLocalDB(Contact contact) {
        new Thread(() -> {
            dao.Insert(contact);
            List<Contact> list = contactsListData.getValue();
            if (list != null) {
                list.add(0, contact);
            } else {
                list = new LinkedList<>();
                list.add(contact);
            }
            try {
                contactsListData.setValue(list);
            } catch (Exception e) {
                contactsListData.postValue(list);
            }
        }).start();
    }
}
