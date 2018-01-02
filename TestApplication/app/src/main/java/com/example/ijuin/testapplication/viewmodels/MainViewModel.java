package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;

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
    public void onMessage(MessageItemModel message)
    {

    }

    @Override
    public void onChatroom(String roomName)
    {
        FirebaseManager.getInstance().updateChatRoom(roomName);
        notifyObservers(MyUtils.CHAT_ROOM_FOUND, roomName);
    }

    @Override
    public void onChatEnded()
    {

    }

    @Override
    public void onUserUpdated(UserModel user)
    {
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
