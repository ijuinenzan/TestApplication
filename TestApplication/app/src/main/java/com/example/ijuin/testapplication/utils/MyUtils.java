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
    public static String MESSAGE_INVALIDE_ROOM_NAME= "Enter a valid Name";

    public static String TEXT_TYPE = "TEXT";
    public static String VIDEO_TYPE = "VIDEO";
    public static String AUDIO_TYPE = "AUDIO";
    public static String LOCATION_TYPE = "LOCATION";

    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int UPDATE_MESSAGES=3;
    public static final int CHANGE_PICTURE = 4;
    public static final int LOG_OUT = 5;
    public static final int CHAT_ROOM_FOUND = 6;

    public static Boolean MALE = true;

    public static Boolean FEMALE = false;

}
