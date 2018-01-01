package com.example.ijuin.testapplication.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ijuin on 11/12/2017.
 */
public class FirebaseManager implements ChildEventListener
{
    private volatile static FirebaseManager sFirebaseManager;
    private FirebaseDatabase _database;
    private DatabaseReference _userReference;
    private DatabaseReference _messageReference;
    private DatabaseReference _chatRoomsReference;
    private DatabaseReference _currentChatRoomReference;
    private ArrayList<FirebaseCallbacks> _callbacks;

    private FirebaseAuth _auth;

    private UserModel _user;

    private String _chatRoom;

    public static synchronized FirebaseManager getInstance()
    {
        if(sFirebaseManager == null)
        {
            synchronized (FirebaseManager.class)
            {
                sFirebaseManager = new FirebaseManager();
            }
        }
        return sFirebaseManager;
    }

    private FirebaseManager(){
        _database = FirebaseDatabase.getInstance();
        _userReference = _database.getReference().child("users");
        _chatRoomsReference = _database.getReference().child("chatrooms");
        _auth = FirebaseAuth.getInstance();
        _callbacks = new ArrayList<>();
        _chatRoom = "";
    }

    public void addCallback(FirebaseCallbacks callback)
    {
        _callbacks.add(callback);
    }

    public void removeCallback(FirebaseCallbacks callback)
    {
        _callbacks.remove(callback);
    }

    public void addMessageListener()
    {
        _messageReference.addChildEventListener(this);
    }

    public void addChatRoomListener()
    {
        _chatRoomsReference.addChildEventListener(this);
    }

    public void removeMessageListener()
    {
        _messageReference.removeEventListener(this);
    }

    public void removeChatRoomListener()
    {
        _chatRoomsReference.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s)
    {
        String root = dataSnapshot.getRef().getParent().getKey();

        if(root.equals("chatrooms") && (boolean)dataSnapshot.child("isAvailable").getValue())
        {
            if(dataSnapshot.child("user1").getValue().equals(FirebaseAuth.getInstance().getUid()) ||
                    dataSnapshot.child("user2").getValue().equals(FirebaseAuth.getInstance().getUid()))
            {
                for(FirebaseCallbacks callback: _callbacks)
                {
                    callback.onChatroom(dataSnapshot.getKey());
                }
            }
        }
        else if(root.equals("messages"))
        {
            for(FirebaseCallbacks callback: _callbacks)
            {
                callback.onMessage(dataSnapshot.getValue(MessageItemModel.class));
            }
        }

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s)
    {
        String root = dataSnapshot.getRef().getParent().getKey();
        if(root.equals("users"))
        {
            if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid()))
            {
                //update user.
            }
        }
        if(root.equals("chatrooms") && dataSnapshot.getKey().equals(_chatRoom))
        {
            if((boolean)dataSnapshot.child("isAvailable").getValue() == false)
            {
                for(FirebaseCallbacks callback: _callbacks)
                {
                    callback.onChatEnded();
                }
            }
        }
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

    public void updateUser(UserModel user)
    {
        if(_user == null ||_user != user)
        {
            _user = user;
        }

        if(_auth.getCurrentUser() == null)
        {
            return;
        }

        _userReference.child(_auth.getUid()).setValue(user);
    }

    public void updateProfilePicture()
    {
    }

    public UserModel getUser()
    {
        return _user;
    }

    public void signOut()
    {
        _auth.signOut();
        _user = null;
    }

    public void sendMessage(MessageItemModel message)
    {
        String key = _messageReference.push().getKey();
        message.setMessageKey(key);
        _messageReference.child(key).setValue(message);
    }

    public void updateChatRoom(String chatRoom)
    {
        _chatRoom = chatRoom;
        _currentChatRoomReference = _chatRoomsReference.child(chatRoom);
        _messageReference = _currentChatRoomReference.child("messages");
    }
    public void destroy() {
        sFirebaseManager=null;
    }


}
