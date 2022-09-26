package com.example.chatsandroid.dataBase.contacts;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.chatsandroid.ChatsApplication;
import com.example.chatsandroid.R;
import com.example.chatsandroid.activities.AddContactActivity;
import com.example.chatsandroid.dataBase.contacts.contactsDB.ContactsDB;
import com.example.chatsandroid.dataBase.contacts.contactsDB.ContactsDao;
import com.example.chatsandroid.entities.Contact;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsApi {
    private ContactsDao contactsDao;
    private Retrofit retrofit;
    private ContactsServiceAPI contactsServiceAPI;

    public ContactsApi() {
        this.contactsDao = ContactsDB.getDatabase().contactsDao();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(ChatsApplication.context.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactsServiceAPI = retrofit.create(ContactsServiceAPI.class);
    }

    public void getAllContacts(MutableLiveData<List<Contact>> contactsListData, String connectedUsername) {
        Call<List<Contact>> call = contactsServiceAPI.getAllContacts(connectedUsername);
        call.enqueue(new Callback<List<Contact>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    // If the request didn't succeeded return.
                    if (response.code() != 200) {
                        return;
                    }
                    List<Contact> contactList = response.body();
                    // If the contactList return is null, return.
                    if (contactList == null) {
                        return;
                    }
                    // Update the connected user of each contact and add it to the dao.
                    for (int i = 0 ; i < contactList.size(); i++) {
                        Contact contact = contactList.get(i);
                        contact.setConnectedUserName(connectedUsername);
                        Contact c = contactsDao.get(contact.getUserName(), connectedUsername);
                        if (c != null) {
                            contactsDao.Delete(c);
                        }
                        contactsDao.Insert(contact);
                    }
                    // Update the contactsList on the contactsRepository.
                    try {
                        contactsListData.setValue(contactList);
                    } catch (Exception e) {
                        contactsListData.postValue(contactList);
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {}
        });
    }

    public void addContact(MutableLiveData<List<Contact>> contactsListData, Contact contact, String connectedUserName, AddContactActivity addContactActivity) {
        Call<Void> call = contactsServiceAPI.addContact(connectedUserName, contact);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                new Thread(() -> {
                    if (response.code() != 201) {
                        addContactActivity.onAddContactFailure();
                    } else {
                        // If the add contact succeeded on server, get update from server about the contacts.
                        getAllContacts(contactsListData, connectedUserName);
                        addContactActivity.onAddContactSuccess(contact);
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                addContactActivity.onAddContactFailure();
            }
        });
    }
}
