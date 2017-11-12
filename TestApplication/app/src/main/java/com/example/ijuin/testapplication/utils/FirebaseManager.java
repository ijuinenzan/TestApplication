package com.example.ijuin.testapplication.utils;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ijuin on 11/12/2017.
 */

public class FirebaseManager implements ChildEventListener
{
    private volatile static FirebaseManager sFirebaseManager;
    private DatabaseReference mMessageReference;
    private FirebaseCallbacks mCallbacks;

    public static synchronized FirebaseManager getInstance(String roomName, FirebaseCallbacks callBacks) {
        if(sFirebaseManager == null) {
            synchronized (FirebaseManager.class) {
                sFirebaseManager = new FirebaseManager(roomName,callBacks);
            }
        }
        return sFirebaseManager;
    }

    private FirebaseManager(String roomName, FirebaseCallbacks callBacks){
        mMessageReference = FirebaseDatabase.getInstance().getReference().child(roomName);
        this.mCallbacks =callBacks;
    }

    public void addMessageListeners(){
        mMessageReference.addChildEventListener(this);
    }

    public void removeListeners(){
        mMessageReference.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        mCallbacks.onNewMessage(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void sendMessageToFirebase(String message, String type) {
        Map<String,Object> map=new HashMap<>();

        map.put("text",message);
        map.put("time",System.currentTimeMillis());
        map.put("senderId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("type", type );

        String keyToPush= mMessageReference.push().getKey();

        mMessageReference.child(keyToPush).setValue(map);
    }

    public void destroy() {
        sFirebaseManager=null;
        mCallbacks =null;
    }
}
