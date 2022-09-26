package com.example.chatsandroid.dataBase.messages.messageDB;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.chatsandroid.entities.Message;
import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message WHERE ContactUserName = :contactUserName AND ConnectedUsername = :connectedUserName")
    List<Message> index(String contactUserName, String connectedUserName);

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Query("SELECT MAX(id) FROM message")
    int getMaxId();

    @Insert
    void Insert(Message... messages);

    @Update
    void Update(Message... messages);

    @Delete
    void Delete(Message... messages);
}