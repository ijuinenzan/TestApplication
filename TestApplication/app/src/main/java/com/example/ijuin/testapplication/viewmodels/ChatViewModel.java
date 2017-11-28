package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.ModelCallBacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.MessageModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatViewModel extends BaseObservable implements FirebaseCallbacks,ModelCallBacks {
    private MessageModel _model;
    public ArrayList<Observer> observers;


    public ChatViewModel(String roomName) {
        _model=new MessageModel();
        observers=new ArrayList<>();
    }


    public void sendMessageToFirebase(String message) {
        if (!message.trim().equals("")){
        }
    }

    public void setListener() {

    }

    public void onDestory() {
    }

    @Override
    public void onNewMessage(DataSnapshot dataSnapshot) {

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

}
