package com.example.chatsandroid.dataBase.messages;
import com.example.chatsandroid.entities.Message;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessagesServiceApi {
    @GET("Messages/{contactUserName}")
    Call<List<Message>> getAllMessagesOnChat(@Path("contactUserName") String contactUserName,
                                                 @Query("username") String username);

    @POST("Messages/{contactUserName}")
    Call<Void> addMessage(@Path("contactUserName") String contactUserName,
                          @Body Message message,
                          @Query("username") String username);
}
