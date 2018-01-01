package com.example.ijuin.testapplication.factories;

import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ijuin on 12/30/2017.
 */

public class MessageFactory
{
    public static MessageItemModel createTextMessage(String text)
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage(text);
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.TEXT_TYPE);
        return messageItemModel;
    }

    public static MessageItemModel createLocationMessage(Double lattitude,Double longitude)
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage(String.valueOf(lattitude) + " " + String.valueOf(longitude));
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.LOCATION_TYPE);
        return messageItemModel;
    }

    public static MessageItemModel createVideoMessage(String videoUrl)
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage(videoUrl);
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.VIDEO_TYPE);
        return messageItemModel;
    }

    public static MessageItemModel createImageMessage(String imageUrl)
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage(imageUrl);
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.IMAGE_TYPE);
        return messageItemModel;
    }
}
