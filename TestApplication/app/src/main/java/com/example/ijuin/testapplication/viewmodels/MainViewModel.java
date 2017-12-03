package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by ijuin on 12/2/2017.
 */

public class MainViewModel extends BaseObservable implements FirebaseCallbacks
{
    public ArrayList<Observer> _observers;

    public MainViewModel()
    {
        _observers = new ArrayList<>();

        FirebaseManager.getInstance().addCallback(this);

        setListener();
    }

    @Override
    public void onMessage(DataSnapshot datasnapshot) {

    }

    @Override
    public void onChatroom(DataSnapshot datasnapshot)
    {
        if(datasnapshot.child("user1").getValue().equals(FirebaseAuth.getInstance().getUid()) ||
                datasnapshot.child("user2").getValue().equals(FirebaseAuth.getInstance().getUid()))
        {
            FirebaseManager.getInstance().updateChatRoom(datasnapshot.getKey());
            notifyObservers(MyUtils.CHAT_ROOM_FOUND, datasnapshot.getKey());
        }
    }

    public void setListener()
    {
        FirebaseManager.getInstance().addChatRoomListener();
    }

    public void onDestroy()
    {
        FirebaseManager.getInstance().removeChatRoomListener();
        FirebaseManager.getInstance().removeCallback(this);
    }

    public void addObserver(Observer client) {
        if (!_observers.contains(client)) {
            _observers.add(client);
        }
    }

    public void removeObserver(Observer clientToRemove) {
        if (_observers.contains(clientToRemove)) {
            _observers.remove(clientToRemove);
        }
    }

    public void notifyObservers(int eventType, String chatroom) {
        for (int i=0; i< _observers.size(); i++) {
            _observers.get(i).onObserve(eventType, chatroom);
        }
    }
}
