package com.example.chatsandroid.dataBase.contacts;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.chatsandroid.activities.AddContactActivity;
import com.example.chatsandroid.dataBase.contacts.contactsDB.ContactsRepository;
import com.example.chatsandroid.entities.Contact;
import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository contactsRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel() {
        contactsRepository = ContactsRepository.getContactsRepository();
        contacts = contactsRepository.getAllContacts();
    }

    public LiveData<List<Contact>> get() {
        return contacts;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setConnectedUser(String connectedUsername, boolean needToReset) {
        contactsRepository.setConnectedUser(connectedUsername, needToReset);
    }

    public void addNewContact(String username, Contact contact, AddContactActivity addContactActivity, boolean needToUpdateServer) {
        contactsRepository.addContact(username, contact, addContactActivity, needToUpdateServer);
    }

    public void updateLastMessage(String contactUserName, String lastMessage, String lastMessageDate, String connectedUsername) {
        contactsRepository.updateLastMessage(contactUserName, lastMessage, lastMessageDate, connectedUsername);
    }

    public void addContactToLocalDB(Contact contact) {
        contactsRepository.addContactToLocalDB(contact);
    }
}