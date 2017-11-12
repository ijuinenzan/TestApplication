package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

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
    private Boolean _isMine;
    //endregion

    public MessageItemModel(DataSnapshot dataSnapshot)
    {
        HashMap<String, Object> object = (HashMap<String, Object>) dataSnapshot.getValue();
        this._messageKey=dataSnapshot.getKey();
        this._message=object.get("text").toString();
        if (object.get("senderId").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            _isMine = true;
        }
        else
        {
            _isMine = false;
        }
        this._timeStamp= MyUtils.convertTime(Long.parseLong(object.get("time").toString()));

    }

    @Bindable
    public String getMessage() { return _message;   }

    public void setMessage(String message) {
        this._message = message;
    }

    public String getMsgKey() {
        return _messageKey;
    }

    public void setMsgKey(String msgKey) {
        this._messageKey = msgKey;
    }

    public String getTimeStamp() {
        return _timeStamp;
    }

    public void setTimeStamp(String _timeStamp) {
        this._timeStamp = _timeStamp;
    }

    public boolean isMine() {
        return _isMine;
    }

    public void setMine(boolean mine) {
        _isMine = mine;
    }

    public String getType() {return _type;}

    public void setType(String type) {this._type = type;}

}
