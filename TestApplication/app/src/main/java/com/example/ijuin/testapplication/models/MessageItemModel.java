package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * Created by ijuin on 11/11/2017.
 */

public class MessageItemModel extends BaseObservable
{
    private String messageKey;
    private String timeStamp;
    private String message;
    private Boolean isMine;

    public MessageItemModel(DataSnapshot dataSnapshot)
    {
        HashMap<String, Object> object = (HashMap<String, Object>) dataSnapshot.getValue();
        this.messageKey=dataSnapshot.getKey();
        this.message=object.get("text").toString();
        if (object.get("senderId").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            isMine = true;
        }
        else
        {
            isMine = false;
        }
        this.timeStamp= MyUtils.convertTime(Long.parseLong(object.get("time").toString()));

    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgKey() {
        return messageKey;
    }

    public void setMsgKey(String msgKey) {
        this.messageKey = msgKey;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

}
