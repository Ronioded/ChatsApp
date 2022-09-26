package com.example.chatsandroid.dataBase.contacts;

import com.example.chatsandroid.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactsServiceAPI {
    @GET("Contacts")
    Call<List<Contact>> getAllContacts(@Query("username") String username);

    @POST("Contacts")
    Call<Void> addContact(@Query("username") String username,
                          @Body Contact contact);
}
