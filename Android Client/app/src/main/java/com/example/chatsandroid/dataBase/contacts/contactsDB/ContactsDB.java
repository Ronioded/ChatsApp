package com.example.chatsandroid.dataBase.contacts.contactsDB;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.chatsandroid.ChatsApplication;
import com.example.chatsandroid.entities.Contact;

@Database(entities = {Contact.class}, version = 7)
public abstract class ContactsDB extends RoomDatabase {
    public abstract ContactsDao contactsDao();
    private static ContactsDB INSTANCE = null;

    // create or get data base instance.
    public static ContactsDB getDatabase() {
        synchronized (ContactsDB.class) {
            if (INSTANCE == null) {
                INSTANCE =  Room.databaseBuilder(ChatsApplication.context.getApplicationContext(),
                                ContactsDB.class,"contact")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
}