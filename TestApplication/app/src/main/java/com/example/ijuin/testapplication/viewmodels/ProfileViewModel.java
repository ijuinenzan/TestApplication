package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.FieldModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/22/2017.
 */

public class ProfileViewModel extends BaseObservable {

    private UserModel _user;
    private UserModel _newUser;
    public ArrayList<Observer> observers;

    public ProfileViewModel(UserModel user) {
        observers=new ArrayList<>();
        _user = user;
        _newUser = new UserModel();

        _newUser.setDisplayName(new FieldModel<String>(_user.getDisplayName().getValue(),_user.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<Integer>(_user.getYearBorn().getValue(),_user.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<Boolean>(_user.getGender().getValue(),_user.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<String>(_user.getCity().getValue(),_user.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<String>(_user.getCountry().getValue(),_user.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<Float>(_user.getWeight().getValue(),_user.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<Float>(_user.getHeight().getValue(),_user.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<String>(_user.getPhoneNumber().getValue(),_user.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<String>(_user.getFacebook().getValue(),_user.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<String>(_user.getTwitter().getValue(),_user.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<String>(_user.getAddress().getValue(),_user.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<String>(_user.getJob().getValue(),_user.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<String>(_user.getImageUrl().getValue(),_user.getImageUrl().getIsPublic()));
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
        return _newUser;
    }

    public void Change()
    {
        notifyObservers(MyUtils.CHANGE_PICTURE, "");
    }

    public void Save()
    {
        _user.setDisplayName(_newUser.getDisplayName());
        _user.setYearBorn(_newUser.getYearBorn());
        _user.setGender(_newUser.getGender());
        _user.setCity(_newUser.getCity());
        _user.setCountry(_newUser.getCountry());
        _user.setWeight(_newUser.getWeight());
        _user.setHeight(_newUser.getHeight());
        _user.setPhoneNumber(_newUser.getPhoneNumber());
        _user.setFacebook(_newUser.getFacebook());
        _user.setTwitter(_newUser.getTwitter());
        _user.setAddress(_newUser.getAddress());
        _user.setJob(_newUser.getJob());
        _user.setImageUrl(_newUser.getImageUrl());

        FirebaseManager.getInstance().updateUser(_user);

        _newUser.setDisplayName(new FieldModel<String>(_user.getDisplayName().getValue(),_user.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<Integer>(_user.getYearBorn().getValue(),_user.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<Boolean>(_user.getGender().getValue(),_user.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<String>(_user.getCity().getValue(),_user.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<String>(_user.getCountry().getValue(),_user.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<Float>(_user.getWeight().getValue(),_user.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<Float>(_user.getHeight().getValue(),_user.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<String>(_user.getPhoneNumber().getValue(),_user.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<String>(_user.getFacebook().getValue(),_user.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<String>(_user.getTwitter().getValue(),_user.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<String>(_user.getAddress().getValue(),_user.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<String>(_user.getJob().getValue(),_user.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<String>(_user.getImageUrl().getValue(),_user.getImageUrl().getIsPublic()));
    }

    public void LogOut()
    {
        FirebaseManager.getInstance().signOut();
    }
}
