package com.example.ijuin.testapplication.models;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

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
    private String _senderId;

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

    //endregion

//    public MessageItemModel(DataSnapshot dataSnapshot)
//    {
//        HashMap<String, Object> object = (HashMap<String, Object>) dataSnapshot.getValue();
//        this._messageKey=dataSnapshot.getKey();
//        this._message=object.get("text").toString();
//        if (object.get("senderId").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//            _isMine = true;
//        }
//        else
//        {
//            _isMine = false;
//        }
//        this._timeStamp= MyUtils.convertTime(Long.parseLong(object.get("time").toString()));
//
//    }
//
//    @Bindable
//    public String getMessage() { return _message;   }
//
//    public void setMessage(String message) {
//        this._message = message;
//    }
//
//    public String getMsgKey() {
//        return _messageKey;
//    }
//
//    public void setMsgKey(String msgKey) {
//        this._messageKey = msgKey;
//    }
//
//    public String getTimeStamp() {
//        return _timeStamp;
//    }
//
//    public void setTimeStamp(String _timeStamp) {
//        this._timeStamp = _timeStamp;
//    }
//
//    public boolean isMine() {
//        return _isMine;
//    }
//
//    public void setMine(boolean mine) {
//        _isMine = mine;
//    }
//
//    public String getType() {return _type;}
//
//    public void setType(String type) {this._type = type;}
//
//    @Bindable
//    public Boolean getIsIcon() {
//        switch (getMessage()){
//            case ":)":
//                setSrcIcon("https://bloximages.chicago2.vip.townnews.com/willmarradio.com/content/tncms/assets/v3/editorial/8/73/873d38ba-8bf1-11e7-80ce-93f5c9c5517d/59a41515a69cd.image.jpg");
//                return true;
//            case "#anal":
//                setSrcIcon("https://thumb-v-ec.xhcdn.com/t/941/640/8_7058941.jpg");
//                return true;
//            case "#penis":
//                setSrcIcon("https://www.spreadshirt.co.uk/image-server/v1/mp/designs/14440405,width=178,height=178/penis.png");
//                return true;
//            case "#girl":
//                setSrcIcon("http://file.vforum.vn/hinh/2013/10/girl-xinh-facebook-48.jpg");
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    public void setIsIcon(Boolean _isIcon) {
//        this._isIcon = _isIcon;
//    }
//
//    @Bindable
//    public String getSrcIcon() {
//        return _srcIcon;
//    }
//
//    public void setSrcIcon(String _srcIcon) {
//        this._srcIcon = _srcIcon;
//    }


}
