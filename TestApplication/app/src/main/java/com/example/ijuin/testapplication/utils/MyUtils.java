package com.example.ijuin.testapplication.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by ijuin on 11/12/2017.
 */

public class MyUtils
{
    public static String MESSAGE_AUTHENTICATION_FAILED = "Firebase authentication failed, please check your internet connection";
    public static String MESSAGE_INVALIDE_ROOM_NAME= "Enter a valid Name";

    public static String TEXT_TYPE = "text";
    public static String EXTRA_ROOM_NAME="EXTRA_ROOM_NAME";

    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int UPDATE_MESSAGES=1;

    public static Integer MALE = 0;
    public static Integer FEMALE = 1;

    public static String convertTime(long timestamp) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    @BindingAdapter("android:text")
    public static void setFloat(TextView view, float value) {
        if (Float.isNaN(value)) view.setText("");
        else view.setText(String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView view) {
        String num = view.getText().toString();
        if(num.isEmpty()) return 0.0F;
        try {
            return Float.parseFloat(num);
        } catch (NumberFormatException e) {
            return 0.0F;
        }
    }

    @BindingAdapter("android:text")
    public static void setInt(TextView view, int value) {
        view.setText(String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getInt(TextView view) {
        String num = view.getText().toString();
        if(num.isEmpty()) return 0;
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
