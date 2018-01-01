package com.example.ijuin.testapplication.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

    public static String TEXT_TYPE = "TEXT";

    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int UPDATE_MESSAGES=3;
    public static final int CHANGE_PICTURE = 4;
    public static final int LOG_OUT = 5;
    public static final int CHAT_ROOM_FOUND = 6;
    public static final int EXIT_ROOM=7;

    public static Boolean MALE = true;

    public static Boolean FEMALE = false;

}
