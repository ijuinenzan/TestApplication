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
        StringBuilder message = new StringBuilder();
        MessageItemModel messageItemModel = new MessageItemModel();
        UserModel currentUser = FirebaseManager.getInstance().getUser();
        for (String select: selectFields)
        {
            switch (select)
            {
                case "Name:": {
                    message.append(select).append("\t").append(currentUser.getDisplayName().getValue()).append("\n");
                    break;
                }
                case "Yearborn:": {
                    message.append(select).append("\t").append(currentUser.getYearBorn().getValue()).append("\n");
                    break;
                }
                case "Gender:": {
                    message.append(select).append("\t").append(currentUser.getGender().getValue()).append("\n");
                    break;
                }
                case "Phone:": {
                    message.append(select).append("\t").append(currentUser.getPhoneNumber().getValue()).append("\n");
                    break;
                }
                case "Address:": {
                    message.append(select).append("\t").append(currentUser.getAddress().getValue()).append("\n");
                    break;
                }
                case "Company:": {
                    message.append(select).append("\t").append(currentUser.getJob().getValue()).append("\n");
                    break;
                }
                case "City:": {
                    message.append(select).append("\t").append(currentUser.getCity().getValue()).append("\n");
                    break;
                }
                case "Country:": {
                    message.append(select).append("\t").append(currentUser.getCountry().getValue()).append("\n");
                    break;
                }
                case "Weight:": {
                    message.append(select).append("\t").append(currentUser.getWeight().getValue()).append("\n");
                    break;
                }
                case "Height:": {
                    message.append(select).append("\t").append(currentUser.getHeight().getValue()).append("\n");
                    break;
                }
                case "Link Facebook:": {
                    message.append(select).append("\t").append(currentUser.getFacebook().getValue()).append("\n");
                    break;
                }
                case "Link Twitter:": {
                    message.append(select).append("\t").append(currentUser.getTwitter().getValue()).append("\n");
                    break;
                }
                default:
                    break;
            }
        }
        messageItemModel.setMessage(message.toString());
        messageItemModel.setSenderId(FirebaseAuth.getInstance().getUid());
        messageItemModel.setTimeStamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        messageItemModel.setType(MyUtils.INFO_ACCEPT_TYPE);
        return messageItemModel;
    }
}
