package com.example.ijuin.testapplication.factories;

import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static MessageItemModel createAudioMessage(String audioUrl)
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage(audioUrl);
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.AUDIO_TYPE);
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

    public static MessageItemModel createInfoRequestMessage()
    {
        MessageItemModel messageItemModel = new MessageItemModel();
        messageItemModel.setMessage("Your partner asks to know your infomation!");
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.INFO_REQUEST_TYPE);
        return messageItemModel;
    }

    public static MessageItemModel createInfoAcceptMessage(ArrayList<String> selectFields)
    {
        String message = "";
        MessageItemModel messageItemModel = new MessageItemModel();
        UserModel currentUser = FirebaseManager.getInstance().getUser();
        for (String select: selectFields)
        {
            switch (select)
            {
                case "Name:": {
                    message += select + "\t" + currentUser.getDisplayName().getValue() + "\n";
                    break;
                }
                case "Yearborn:": {
                    message += select + "\t" + currentUser.getYearBorn().getValue() + "\n";
                    break;
                }
                case "Gender:": {
                    message += select + "\t" + currentUser.getGender().getValue() + "\n";
                    break;
                }
                case "Phone:": {
                    message += select + "\t" + currentUser.getPhoneNumber().getValue() + "\n";
                    break;
                }
                case "Address:": {
                    message += select + "\t" + currentUser.getAddress().getValue() + "\n";
                    break;
                }
                case "Company:": {
                    message += select + "\t" + currentUser.getJob().getValue() + "\n";
                    break;
                }
                case "City:": {
                    message += select + "\t" + currentUser.getCity().getValue() + "\n";
                    break;
                }
                case "Country:": {
                    message += select + "\t" + currentUser.getCountry().getValue() + "\n";
                    break;
                }
                case "Weight:": {
                    message += select + "\t" + currentUser.getWeight().getValue() + "\n";
                    break;
                }
                case "Height:": {
                    message += select + "\t" + currentUser.getHeight().getValue() + "\n";
                    break;
                }
                case "Link Facebook:": {
                    message += select + "\t" + currentUser.getFacebook().getValue() + "\n";
                    break;
                }
                case "Link Twitter:": {
                    message += select + "\t" + currentUser.getTwitter().getValue() + "\n";
                    break;
                }
                default:
                    break;
            }
        }
        messageItemModel.setMessage(message);
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.INFO_ACCEPT_TYPE);
        return messageItemModel;
    }
}
