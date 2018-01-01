package com.example.ijuin.testapplication.interfaces;

import com.example.ijuin.testapplication.models.MessageItemModel;
import com.google.firebase.database.DataSnapshot;
/**
 * Created by Khanh on 11/11/2017.
 */

public interface FirebaseCallbacks
{
    void onMessage(MessageItemModel message);
    void onChatroom(String roomName);
    void onChatEnded();
}
