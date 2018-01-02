package com.example.ijuin.testapplication.interfaces;

import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;

/**
 * Created by Khanh on 11/11/2017.
 */

public interface FirebaseCallbacks
{
    void onMessage(MessageItemModel message);
    void onChatroom(String roomName);
    void onChatEnded();
    void onUserUpdated(UserModel user);
}
