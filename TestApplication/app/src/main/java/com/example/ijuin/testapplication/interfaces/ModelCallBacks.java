package com.example.ijuin.testapplication.interfaces;

import com.example.ijuin.testapplication.models.MessageItemModel;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/11/2017.
 */

public interface ModelCallBacks
{
    void onModelUpdated(ArrayList<MessageItemModel> messages);
}
