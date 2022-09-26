package com.example.chatsandroid;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.chatsandroid.entities.Contact;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Utils {
    // The function pass the textViews, remove the text in them and set them unvisible.
    public static void removeErrors(TextView[] textViews) {
        for (TextView t : textViews) {
            t.setText("");
            t.setVisibility(View.INVISIBLE);
        }
    }

    // The function check if there is an empty input in the list and show an error message if there is.
    @SuppressLint("SetTextI18n")
    public static boolean isEmptyInput(EditText[] editTexts, TextView[] textViews) {
        boolean flag = false;
        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().equals("")) {
                textViews[i].setVisibility(View.VISIBLE);
                textViews[i].setText("Field is required!");
                flag = true;
            }
        }
        return flag;
    }

    // The function return the time from timeAndDate.
    public static String returnTime(String timeAndDate) {
        if (timeAndDate == null || timeAndDate.equals("")) {
            return "";
        }
        return timeAndDate.substring(11, 16);
    }

    // The function decide what time will be displayed of the last message in the chat.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String timeDisplay(String timeAndDate) {
        String time = returnTime(timeAndDate);
        if (time.equals("")) {
            return timeAndDate;
        }
        String message_date = timeAndDate.substring(8,10) + '.' + timeAndDate.substring(5,7) + '.' + timeAndDate.substring(0,4);
        LocalDateTime thisTime = LocalDateTime.now();
        String[] date_splitted = message_date.split("\\.", -3);
        String[] time_splitted = time.split(":", -3);

        // if the last message is not in this year or month or more than a day passed, show the date.
        if ((thisTime.getYear() != Integer.parseInt(date_splitted[2]))
                || (Integer.parseInt(date_splitted[1]) != (thisTime.getMonth().ordinal() + 1))
                || (thisTime.getDayOfMonth() - Integer.parseInt(date_splitted[0])) > 1) {
            return message_date;
        }

        // If the last message was sent yesterday, display yesterday.
        if ((thisTime.getDayOfMonth() - Integer.parseInt(date_splitted[0])) == 1) {
            return "yesterday";
        }

        // if it passed more than a hour, return the time.
        if (thisTime.getHour() - Integer.parseInt(time_splitted[0]) > 1) {
            return time;
        }
        // the message was sent in the same hour. if 10 minutes passed, return time, else return number of minutes passed.
        else if (thisTime.getHour() == Integer.parseInt(time_splitted[0])) {
            if (thisTime.getMinute() - Integer.parseInt(time_splitted[1]) < 10) {
                return thisTime.getMinute() - Integer.parseInt(time_splitted[1]) + " min ago";
            } else {
                return time;
            }
        } // it is not the same hour, if 10 minutes passed, return time, else return number of minutes passed.
        else {
            if ((thisTime.getMinute() + 60 - Integer.parseInt(time_splitted[1])) < 10) {
                return (thisTime.getMinute() + 60 - Integer.parseInt(time_splitted[1])) + " min ago";
            } else {
                return time;
            }
        }
    }

    // The class sort the contacts by last message.
    public static class sortByLastMessage implements Comparator<Contact>
    {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public int compare(Contact x, Contact y)
        {
            if ((x.getLastMessageTimeStamp() == null) || (x.getLastMessageTimeStamp().equals("")))
            {
                return -1;
            }
            else if ((y.getLastMessageTimeStamp() == null) || (y.getLastMessageTimeStamp().equals("")))
            {
                return 1;
            }
            LocalDateTime xTime = LocalDateTime.parse(x.getLastMessageTimeStamp().substring(0, 19));
            LocalDateTime yTime = LocalDateTime.parse(y.getLastMessageTimeStamp().substring(0, 19));
            if (xTime.isBefore(yTime)) {
                return 1;
            }
            else if (xTime.isEqual(yTime)) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }
}
