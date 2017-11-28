package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.FieldModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.utils.TextWatcherAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/22/2017.
 */

public class ProfileViewModel extends BaseObservable {

    private UserModel _user;
    private UserModel newUser;
    public ArrayList<Observer> observers;

    public ProfileViewModel(UserModel user) {
        observers=new ArrayList<>();
        _user = user;
        newUser = new UserModel();

        newUser.setDisplayName(new FieldModel<String>(_user.getDisplayName().getValue(),_user.getDisplayName().getIsPublic()));
        newUser.setYearBorn(new FieldModel<Integer>(_user.getYearBorn().getValue(),_user.getYearBorn().getIsPublic()));
        newUser.setGender(new FieldModel<Boolean>(_user.getGender().getValue(),_user.getGender().getIsPublic()));
        newUser.setCity(new FieldModel<String>(_user.getCity().getValue(),_user.getCity().getIsPublic()));
        newUser.setCountry(new FieldModel<String>(_user.getCountry().getValue(),_user.getCountry().getIsPublic()));
        newUser.setWeight(new FieldModel<Float>(_user.getWeight().getValue(),_user.getWeight().getIsPublic()));
        newUser.setHeight(new FieldModel<Float>(_user.getHeight().getValue(),_user.getHeight().getIsPublic()));
        newUser.setPhoneNumber(new FieldModel<String>(_user.getPhoneNumber().getValue(),_user.getPhoneNumber().getIsPublic()));
        newUser.setFacebook(new FieldModel<String>(_user.getFacebook().getValue(),_user.getFacebook().getIsPublic()));
        newUser.setTwitter(new FieldModel<String>(_user.getTwitter().getValue(),_user.getTwitter().getIsPublic()));
        newUser.setAddress(new FieldModel<String>(_user.getAddress().getValue(),_user.getAddress().getIsPublic()));
        newUser.setJob(new FieldModel<String>(_user.getJob().getValue(),_user.getJob().getIsPublic()));
        newUser.setImageUrl(new FieldModel<String>(_user.getImageUrl().getValue(),_user.getImageUrl().getIsPublic()));
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

    public void notifyObservers(int eventType, String message) {
        for (int i=0; i< observers.size(); i++) {
            observers.get(i).onObserve(eventType, message);
        }
    }

    @Bindable
    public UserModel getUser() {
        return newUser;
    }

    public void Change()
    {
        notifyObservers(MyUtils.CHANGE_PICTURE, "");
    }

    public void Save()
    {
        _user.setDisplayName(newUser.getDisplayName());
        _user.setYearBorn(newUser.getYearBorn());
        _user.setGender(newUser.getGender());
        _user.setCity(newUser.getCity());
        _user.setCountry(newUser.getCountry());
        _user.setWeight(newUser.getWeight());
        _user.setHeight(newUser.getHeight());
        _user.setPhoneNumber(newUser.getPhoneNumber());
        _user.setFacebook(newUser.getFacebook());
        _user.setTwitter(newUser.getTwitter());
        _user.setAddress(newUser.getAddress());
        _user.setJob(newUser.getJob());
        _user.setImageUrl(newUser.getImageUrl());

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).setValue(_user);
    }

    public void LogOut()
    {

    }
}
