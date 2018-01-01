package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.factories.MessageFactory;
import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.ModelCallBacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.MessageModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatViewModel extends BaseObservable implements ModelCallBacks, FirebaseCallbacks {
    private MessageModel _model;
    private String _message;
    public ArrayList<Observer> observers;


    public ChatViewModel()
    {
        _model=new MessageModel();
        observers=new ArrayList<>();
        _message = "";

        FirebaseManager.getInstance().addCallback(this);
        setListener();
    }

    @Bindable
    public String getMessage()
    {
        return _message;
    }

    public void setMessage(String message)
    {
        _message = message;
        notifyPropertyChanged(BR.message);
    }


    public void sendMessage()
    {
        if (!_message.trim().equals(""))
        {
            FirebaseManager.getInstance().sendMessage(MessageFactory.createTextMessage(_message));
            setMessage("");
        }
    }

    public void sendLocation(Double lattitude, Double longitude)
    {
        FirebaseManager.getInstance().sendMessage(MessageFactory.createLocationMessage(lattitude,longitude));
    }

    public void setListener()
    {
        FirebaseManager.getInstance().addMessageListener();
    }

    public void onDestroy() {
        FirebaseManager.getInstance().addCallback(this);
        FirebaseManager.getInstance().removeMessageListener();
    }

    @Override
    public void onModelUpdated(ArrayList<MessageItemModel> messages) {
        if (messages.size()>0) {
            notifyObservers(MyUtils.UPDATE_MESSAGES,messages);
        }
    }

    public void addObserver(Observer client) {
        if (!observers.contains(client)) {
            observers.add(client);
        }
    }

    public void removeObserver(Observer clientToRemove) {
        if (observers.contains(clientToRemove)) {
            observers.remove(clientToRemove);
        }
    }

    public void notifyObservers(int eventType, ArrayList<MessageItemModel> messages) {
        for (int i=0; i< observers.size(); i++) {
            observers.get(i).onObserve(eventType, messages);
        }
    }

    @Override
    public void onMessage(MessageItemModel message) {
        _model.addMessages(message, this);
    }

    @Override
    public void onChatroom(String roomName)
    {

    }

    @Override
    public void onChatEnded() {
        notifyObservers(MyUtils.EXIT_ROOM,null);
    }
}
