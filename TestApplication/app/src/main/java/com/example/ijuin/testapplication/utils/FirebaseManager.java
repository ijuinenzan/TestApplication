package com.example.ijuin.testapplication.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */
public class FirebaseManager implements ChildEventListener
{
    private volatile static FirebaseManager sFirebaseManager;
    private final FirebaseStorage _storage;
    private final StorageReference _profileImageReference;
    private StorageReference _chatRoomStorageReference;
    private final DatabaseReference _userReference;
    private DatabaseReference _messageReference;
    private final DatabaseReference _chatRoomsReference;
    private DatabaseReference _currentChatRoomReference;
    private final ArrayList<FirebaseCallbacks> _callbacks;

    private final FirebaseAuth _auth;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        _userReference = database.getReference().child("users");
        _chatRoomsReference = database.getReference().child("chatrooms");
        _auth = FirebaseAuth.getInstance();
        _callbacks = new ArrayList<>();
        _chatRoom = "";
        _storage = FirebaseStorage.getInstance();
        _profileImageReference = _storage.getReference().child("profile_pictures/" + FirebaseAuth.getInstance().getUid());
    }

    public void exitRoom(){
        _currentChatRoomReference.child("isAvailable").setValue(false);
        _user.setState("Not Finding");
        _userReference.child(_auth.getUid()).setValue(_user);
        _chatRoom = "";
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
                _user.setImageUrl(new FieldModel<>(downloadUrl.toString(),false));
                _userReference.child(_auth.getUid()).setValue(_user);
            }
        });
    }

    public void sendVideoMessage(Uri uri)
    {
        try
        {
            final String key = _messageReference.push().getKey();
            StorageReference uploadRef = _chatRoomStorageReference.child(key);
            UploadTask uploadTask = uploadRef.putFile(uri);
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
                    MessageItemModel message = MessageFactory.createVideoMessage(downloadUrl.toString());
                    message.setMessageKey(key);
                    _messageReference.child(key).setValue(message);
                }
            });
        }
        catch (Exception e)
        {

        }

    }

    public void sendAudioMessage(Uri uri)
    {
        final String key = _messageReference.push().getKey();
        StorageReference uploadRef = _chatRoomStorageReference.child(key);
        UploadTask uploadTask = uploadRef.putFile(uri);
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
                MessageItemModel message = MessageFactory.createAudioMessage(downloadUrl.toString());
                message.setMessageKey(key);
                _messageReference.child(key).setValue(message);
            }
        });

    }

    public void sendImageBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final String key = _messageReference.push().getKey();
        StorageReference uploadRef = _chatRoomStorageReference.child(key);

        UploadTask uploadTask = uploadRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                MessageItemModel message = MessageFactory.createImageMessage(downloadUrl.toString());
                message.setMessageKey(key);
                _messageReference.child(key).setValue(message);
            }
        });
    }

    public void sendImageUri(Uri uri)
    {
        final String key = _messageReference.push().getKey();
        StorageReference uploadRef = _chatRoomStorageReference.child(key);

        UploadTask uploadTask = uploadRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                MessageItemModel message = MessageFactory.createImageMessage(downloadUrl.toString());
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
            if(!((boolean) dataSnapshot.child("isAvailable").getValue()))
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
        _chatRoomStorageReference = _storage.getReference().child(_chatRoom);
    }
}
