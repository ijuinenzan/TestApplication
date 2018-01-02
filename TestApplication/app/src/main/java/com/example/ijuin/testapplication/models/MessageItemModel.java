package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ijuin on 11/11/2017.
 */

public class MessageItemModel extends BaseObservable
{
    //region DECLARE VARIABLE
    private String _messageKey;
    private String _timeStamp;
    private String _message;
    private String _type;
    private String _senderId;
    //endregion

    @Bindable
    public String getMessageKey() {
        return _messageKey;
    }

    @Bindable
    public String getTimeStamp() {
        return _timeStamp;
    }

    @Bindable
    public String getMessage() {
        return _message;
    }

    @Bindable
    public String getType() {
        return _type;
    }

    @Bindable
    public String getSenderId() {
        return _senderId;
    }

    public void setMessageKey(String messageKey) {
        this._messageKey = messageKey;
        notifyPropertyChanged(BR.messageKey);
    }

    public void setTimeStamp(String timeStamp) {
        this._timeStamp = timeStamp;
        notifyPropertyChanged(BR.timeStamp);
    }

    public void setMessage(String message) {
        this._message = message;
        notifyPropertyChanged(BR.message);
    }

    public void setSenderId(String senderId) {
        this._senderId = senderId;
        notifyPropertyChanged(BR.senderId);
        notifyPropertyChanged(BR.isMine);
    }

    public void setType(String type) {
        this._type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public boolean getIsMine(){
        return _senderId.equals(FirebaseAuth.getInstance().getUid());
    }


}
