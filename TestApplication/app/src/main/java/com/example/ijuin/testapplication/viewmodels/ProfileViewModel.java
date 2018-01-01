package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

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

    private UserModel _newUser;
    public ArrayList<Observer> observers;

    public ProfileViewModel() {
        observers=new ArrayList<>();
        _newUser = new UserModel();

        UserModel currentUser = FirebaseManager.getInstance().getUser();

        _newUser.setDisplayName(new FieldModel<String>(currentUser.getDisplayName().getValue(),currentUser.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<Integer>(currentUser.getYearBorn().getValue(),currentUser.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<Boolean>(currentUser.getGender().getValue(),currentUser.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<String>(currentUser.getCity().getValue(),currentUser.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<String>(currentUser.getCountry().getValue(),currentUser.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<Float>(currentUser.getWeight().getValue(),currentUser.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<Float>(currentUser.getHeight().getValue(),currentUser.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<String>(currentUser.getPhoneNumber().getValue(),currentUser.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<String>(currentUser.getFacebook().getValue(),currentUser.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<String>(currentUser.getTwitter().getValue(),currentUser.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<String>(currentUser.getAddress().getValue(),currentUser.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<String>(currentUser.getJob().getValue(),currentUser.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<String>(currentUser.getImageUrl().getValue(),currentUser.getImageUrl().getIsPublic()));
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
        notifyObservers(MyUtils.SELECT_PICTURE, "");
    }

    public void TakePicture()
    {
        notifyObservers(MyUtils.TAKE_PICTURE, "");
    }

    public void Save()
    {
        UserModel currentUser = FirebaseManager.getInstance().getUser();

        currentUser.setDisplayName(_newUser.getDisplayName());
        currentUser.setYearBorn(_newUser.getYearBorn());
        currentUser.setGender(_newUser.getGender());
        currentUser.setCity(_newUser.getCity());
        currentUser.setCountry(_newUser.getCountry());
        currentUser.setWeight(_newUser.getWeight());
        currentUser.setHeight(_newUser.getHeight());
        currentUser.setPhoneNumber(_newUser.getPhoneNumber());
        currentUser.setFacebook(_newUser.getFacebook());
        currentUser.setTwitter(_newUser.getTwitter());
        currentUser.setAddress(_newUser.getAddress());
        currentUser.setJob(_newUser.getJob());
        currentUser.setImageUrl(_newUser.getImageUrl());

        FirebaseManager.getInstance().updateUser(currentUser);

        _newUser.setDisplayName(new FieldModel<String>(currentUser.getDisplayName().getValue(),currentUser.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<Integer>(currentUser.getYearBorn().getValue(),currentUser.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<Boolean>(currentUser.getGender().getValue(),currentUser.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<String>(currentUser.getCity().getValue(),currentUser.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<String>(currentUser.getCountry().getValue(),currentUser.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<Float>(currentUser.getWeight().getValue(),currentUser.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<Float>(currentUser.getHeight().getValue(),currentUser.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<String>(currentUser.getPhoneNumber().getValue(),currentUser.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<String>(currentUser.getFacebook().getValue(),currentUser.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<String>(currentUser.getTwitter().getValue(),currentUser.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<String>(currentUser.getAddress().getValue(),currentUser.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<String>(currentUser.getJob().getValue(),currentUser.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<String>(currentUser.getImageUrl().getValue(),currentUser.getImageUrl().getIsPublic()));
    }

    public void LogOut()
    {
        FirebaseManager.getInstance().signOut();
        notifyObservers(MyUtils.LOG_OUT, null);
    }
}
