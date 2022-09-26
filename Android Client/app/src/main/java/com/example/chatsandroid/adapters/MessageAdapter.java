package com.example.chatsandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatsandroid.R;
import com.example.chatsandroid.entities.Message;
import com.example.chatsandroid.Utils;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    class MessageViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private TextView messageContent;
        private TextView messageTimeStamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(com.google.android.material.R.id.layout);
            messageContent = itemView.findViewById(R.id.message);
            messageTimeStamp = itemView.findViewById(R.id.created_time);
        }
    }

    private final LayoutInflater inflater;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messagesList) {
        super();
        inflater = LayoutInflater.from(context);
        messageList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView;
        if (viewType == 1){
            layoutView = inflater.inflate(R.layout.sent_message, parent, false);
        }
        else {
            layoutView = inflater.inflate(R.layout.got_message, parent, false);
        }
        return new MessageViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        final Message current = messageList.get(position);
        holder.messageContent.setText(current.getContent());
        holder.messageTimeStamp.setText(Utils.returnTime(current.getTimeStamp()));
    }


    @Override
    public int getItemViewType(int position) {
        if (messageList != null && messageList.size()!=0) {
            // if the connected user sent the message return 1, else return 2.
            if (messageList.get(position).isSent())
                return 1;
        }
        return 2;
    }

    @Override
    public int getItemCount(){
        if (messageList != null){
            return messageList.size();
        }
        return 0;
    }

    public void setMessageList(List<Message> messages) {
        messageList = messages;
        notifyDataSetChanged();
    }
}
