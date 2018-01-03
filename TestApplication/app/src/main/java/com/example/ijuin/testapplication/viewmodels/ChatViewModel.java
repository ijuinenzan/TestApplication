package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.factories.MessageFactory;
import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatViewModel extends BaseObservable implements FirebaseCallbacks {
    private final ArrayList<MessageItemModel> _messages;
    private String _message;
    public final ArrayList<Observer> _observers;


    public ChatViewModel()
    {
        _messages = new ArrayList<>();
        _observers =new ArrayList<>();
        _message = "";

        FirebaseManager.getInstance().addCallback(this);
        setListener();
    }

    @Bindable
    public String getTypingMessage()
    {
        return _message;
    }

    public void setTypingMessage(String message)
    {
        _message = message;
        notifyPropertyChanged(BR.typingMessage);
    }

    @Bindable
    public ArrayList<MessageItemModel> getMessages()
    {
        return _messages;
    }


    public void sendMessage()
    {
        if (!_message.trim().equals(""))
        {
            FirebaseManager.getInstance().sendMessage(MessageFactory.createTextMessage(_message));
            setTypingMessage("");
        }
    }

    public void sendLocation(Double latitude, Double longitude)
    {
        FirebaseManager.getInstance().sendMessage(MessageFactory.createLocationMessage(latitude,longitude));
    }

    public void sendVideo(Uri uri)
    {
        FirebaseManager.getInstance().sendVideoMessage(uri);
    }
    public void sendAudio(Uri uri)
    {
        FirebaseManager.getInstance().sendAudioMessage(uri);
    }

    public void sendImageUri(Uri uri)
    {
        FirebaseManager.getInstance().sendImageUri(uri);
    }

    public void sendImageBitmap(Bitmap bitmap)
    {
        FirebaseManager.getInstance().sendImageBitmap(bitmap);
    }

    public void sendInfoRequest()
    {
        FirebaseManager.getInstance().sendMessage(MessageFactory.createInfoRequestMessage());
    }

    public void sendInfoAccept(ArrayList<String> selectFields)
    {
        FirebaseManager.getInstance().sendMessage(MessageFactory.createInfoAcceptMessage(selectFields));
    }

    public void setListener()
    {
        FirebaseManager.getInstance().addMessageListener();
    }

    public void onDestroy() {
        FirebaseManager.getInstance().removeCallback(this);
        FirebaseManager.getInstance().removeMessageListener();
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

    public void notifyObservers(int eventType) {
        for (int i = 0; i< _observers.size(); i++) {
            _observers.get(i).onObserve(eventType, null);
        }
    }

    @Override
    public void onMessage(MessageItemModel message) {
        for(MessageItemModel messageIterator: _messages)
        {
            if(messageIterator.getMessageKey().equals(message.getMessageKey()))
            {
                messageIterator.setSenderId(message.getSenderId());
                messageIterator.setTimeStamp(message.getTimeStamp());
                messageIterator.setType(message.getType());
                messageIterator.setMessage(message.getMessage());
                notifyPropertyChanged(BR.messages);
                return;
            }
        }
        _messages.add(message);
        notifyPropertyChanged(BR.messages);
    }

    @Override
    public void onChatroom(String roomName)
    {
    }

    public void exitRoom()
    {
        notifyObservers(MyUtils.EXIT_ROOM);
        FirebaseManager.getInstance().exitRoom();
        FirebaseManager.getInstance().removeMessageListener();
        FirebaseManager.getInstance().removeCallback(this);
    }

    @Override
    public void onChatEnded() {
        exitRoom();
    }

    @Override
    public void onUserUpdated(UserModel user)
    {

    }
}
