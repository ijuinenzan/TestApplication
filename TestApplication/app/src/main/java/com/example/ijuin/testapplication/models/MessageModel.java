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

    public void addMessages(DataSnapshot dataSnapshot, ModelCallBacks callBacks){
        if (mMessages==null){
            mMessages= new ArrayList<>();
        }
        MessageItemModel messageItemModel=new MessageItemModel(dataSnapshot);
        mMessages.add(messageItemModel);
        callBacks.onModelUpdated(mMessages);
    }
}
