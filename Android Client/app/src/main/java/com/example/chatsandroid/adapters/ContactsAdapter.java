package com.example.chatsandroid.adapters;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.chatsandroid.R;
import com.example.chatsandroid.entities.Contact;
import com.example.chatsandroid.Utils;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;
    List<Contact> contactsList;

    public ContactsAdapter(Context context, List<Contact> contactsList) {
        super(context, R.layout.contact_item, contactsList);
        this.contactsList = contactsList;
        this.inflater = LayoutInflater.from(context);
    }

    // The function return true if contactsList and contacts are equals.
    public boolean isEquals(List<Contact> contacts){
        if (contacts.size() != contactsList.size()) {
            return false;
        }
        for (int i = 0; i < contacts.size(); i++){
            if (!contacts.get(i).equals(contactsList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_item, parent, false);
        }

        // Get from the layout the elements that needs to be updated with information.
        TextView contactName = convertView.findViewById(R.id.contact_name);
        TextView lastMsg = convertView.findViewById(R.id.last_message_contact);
        TextView time = convertView.findViewById(R.id.last_date);

        // Update the fields.
        contactName.setText(contact.getUserName());
        lastMsg.setText(contact.getLastMessageContent());
        time.setText(Utils.timeDisplay(contact.getLastMessageTimeStamp()));
        return convertView;
    }

    public void setContactsList(List<Contact> contactsList) {
        this.contactsList = contactsList;
        notifyDataSetChanged();
    }
}
