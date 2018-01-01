package com.example.ijuin.testapplication.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ijuin.testapplication.factories.MessageFactory;
import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.models.FieldModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by ijuin on 11/12/2017.
 */
public class FirebaseManager implements ChildEventListener
{
    private volatile static FirebaseManager sFirebaseManager;
    private FirebaseDatabase _database;
    private FirebaseStorage _storage;
    private StorageReference _profileImageReference;
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
        _storage = FirebaseStorage.getInstance();
        _profileImageReference = _storage.getReferenceFromUrl("gs://testandroidstudio-2b160.appspot.com").child("profile_pictures/" + FirebaseAuth.getInstance().getUid());
    }

    public void uploadProfileImage(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = _profileImageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                _user.setImageUrl(new FieldModel<String>(downloadUrl.toString(),false));
                _userReference.child(_auth.getUid()).setValue(_user);
            }
        });
    }

    public void sendVideoMessage()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = _profileImageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String key = _messageReference.push().getKey();
                MessageItemModel message = MessageFactory.createVideoMessage(downloadUrl.toString());
                message.setMessageKey(key);
                _messageReference.child(key).setValue(message);
            }
        });
    }


    public void addCallback(FirebaseCallbacks callback)
    {
        _callbacks.add(callback);
    }

    public void removeCallback(FirebaseCallbacks callback)
    {
        _callbacks.remove(callback);
    }

    public void addUserListener()
    {
        _userReference.addChildEventListener(this);
    }

    public void removeUserListener()
    {
        _userReference.removeEventListener(this);
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
                for(FirebaseCallbacks callback: _callbacks)
                {
                    callback.onUserUpdated(_user);
                }
            }
        }
        else if(root.equals("chatrooms") && dataSnapshot.getKey().equals(_chatRoom))
        {
            if((boolean)dataSnapshot.child("isAvailable").getValue() == false)
            {
                for(FirebaseCallbacks callback: _callbacks)
                {
                    callback.onChatEnded();
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
