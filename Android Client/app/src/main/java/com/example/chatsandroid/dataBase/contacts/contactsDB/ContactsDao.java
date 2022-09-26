package com.example.chatsandroid.dataBase.contacts.contactsDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.chatsandroid.entities.Contact;
import java.util.List;

@Dao
public interface ContactsDao {
    // Get all user contacts.
    @Query("SELECT * FROM contact WHERE ConnectedUserName = :connectedUsername")
    List<Contact> index(String connectedUsername);

    // Get the contact of the connected user by his name.
    @Query("SELECT * FROM contact WHERE (UserName = :userName And ConnectedUsername = :connectedUsername)")
    Contact get(String userName, String connectedUsername);

    @Insert
    void Insert(Contact... contacts);

    @Update
    void Update(Contact... contacts);

    @Delete
    void Delete(Contact... contacts);
}