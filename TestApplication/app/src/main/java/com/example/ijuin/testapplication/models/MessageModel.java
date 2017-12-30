package com.example.ijuin.testapplication.models;

import com.example.ijuin.testapplication.interfaces.ModelCallBacks;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class MessageModel
{
    private ArrayList<MessageItemModel> mMessages;

    public void addMessages(MessageItemModel message, ModelCallBacks callBacks){
        if (mMessages==null){
            mMessages= new ArrayList<>();
        }

        for(MessageItemModel messageIterator : mMessages)
        {
            if(message.getMessageKey().equals(messageIterator.getMessageKey()))
            {
                messageIterator.setMessage(message.getMessage());
                messageIterator.setType(message.getType());
                messageIterator.setTimeStamp(message.getTimeStamp());
                messageIterator.setSenderId(message.getSenderId());

                callBacks.onModelUpdated(mMessages);

                return;
            }
        }
        MessageItemModel messageItemModel=new MessageItemModel();
        mMessages.add(messageItemModel);
        callBacks.onModelUpdated(mMessages);
    }
}
