package com.example.ijuin.testapplication.interfaces;

import com.google.firebase.database.DataSnapshot;
/**
 * Created by Khanh on 11/11/2017.
 */

public interface FirebaseCallbacks
{
    void onNewMessage(DataSnapshot dataSnapshot);
}
