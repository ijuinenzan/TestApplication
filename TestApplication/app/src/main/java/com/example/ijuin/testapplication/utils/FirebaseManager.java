package com.example.ijuin.testapplication.utils;

import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
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

    public static synchronized FirebaseManager getInstance(String roomName, FirebaseCallbacks callBacks)
    {
        if(sFirebaseManager == null)
        {
            synchronized (FirebaseManager.class)
            {
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

    public void sendMessageToFirebase(String message, String type)
    {
        Map<String,Object> map=new HashMap<>();

        map.put("text",message);
        map.put("time",System.currentTimeMillis());
        map.put("senderId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("type", type );

        String keyToPush= mMessageReference.push().getKey();

        mMessageReference.child(keyToPush).setValue(map);
    }






    // To upload to Firebase Storage through a byte[]
    public void uploadToFirebaseStorage(StorageReference storageReference, byte[] data )
    {
        UploadTask uploadTask = storageReference.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Do something when upload Success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when upload Failure
            }
        });
    }


    // To upload to Firebase Storage through a InputStream
    public void uploadToFirebaseStorage(StorageReference storageReference,  InputStream inputStream )
    {
        UploadTask uploadTask = storageReference.putStream(inputStream);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Do something when upload Success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when upload Failure
            }
        });
    }



    // To upload to Firebase Storage through a Uri
    public void uploadToFirebaseStorage(StorageReference storageReference,  Uri uri )
    {
        UploadTask uploadTask = storageReference.putFile(uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Do something when upload Success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when upload Failure
            }
        });
    }


    // Upload pause
    // Upload resume
    // Upload cancel



    // To download from Firebase Storage to memory
    public void downloadFromFirebaseStorage (StorageReference storageReference, long maxDownloadSizeByte)
    {
        storageReference.getBytes(maxDownloadSizeByte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Do something when download success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when download failure
            }
        });
    }



    // To download from Firebase Storage to File
    public void downloadFromFirebaseStorage (StorageReference storageReference, File localFile)
    {
        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Do something when download success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when download failure
            }
        });
    }


    // To download from Firebase Storage through URL
    public void downloadFromFirebaseStorage (StorageReference storageReference)
    {
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Do something when download success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when download failure
            }
        });
    }



    // To delete File on Firebase Storage
    public void deleteFile(StorageReference storageReference)
    {
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Do something when delete success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Do something when delete success
            }
        });
    }


    public void destroy() {
        sFirebaseManager=null;
        mCallbacks =null;
    }
}
