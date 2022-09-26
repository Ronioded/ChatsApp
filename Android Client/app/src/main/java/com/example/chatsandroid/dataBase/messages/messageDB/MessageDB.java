package com.example.chatsandroid.dataBase.messages.messageDB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.chatsandroid.ChatsApplication;
import com.example.chatsandroid.entities.Message;

@Database(entities = {Message.class}, version = 8)
public abstract class MessageDB extends RoomDatabase{
    public abstract MessageDao messageDao();
    private static MessageDB INSTANCE;

    // create or get data base instance.
    public static MessageDB getDatabase() {
        if (INSTANCE == null) {
            synchronized (MessageDB.class) {
                if (INSTANCE == null) {
                    INSTANCE =  Room.databaseBuilder(ChatsApplication.context.getApplicationContext(),
                                    MessageDB.class,"message")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}