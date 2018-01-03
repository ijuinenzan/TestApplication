package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.FieldModel;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/22/2017.
 */

public class ProfileViewModel extends BaseObservable implements FirebaseCallbacks {

    private final UserModel _newUser;
    private final ArrayList<Observer> observers;

    public ProfileViewModel() {
        observers=new ArrayList<>();
        _newUser = new UserModel();

        UserModel currentUser = FirebaseManager.getInstance().getUser();

        _newUser.setDisplayName(new FieldModel<>(currentUser.getDisplayName().getValue(), currentUser.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<>(currentUser.getYearBorn().getValue(), currentUser.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<>(currentUser.getGender().getValue(), currentUser.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<>(currentUser.getCity().getValue(), currentUser.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<>(currentUser.getCountry().getValue(), currentUser.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<>(currentUser.getWeight().getValue(), currentUser.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<>(currentUser.getHeight().getValue(), currentUser.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<>(currentUser.getPhoneNumber().getValue(), currentUser.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<>(currentUser.getFacebook().getValue(), currentUser.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<>(currentUser.getTwitter().getValue(), currentUser.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<>(currentUser.getAddress().getValue(), currentUser.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<>(currentUser.getJob().getValue(), currentUser.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<>(currentUser.getImageUrl().getValue(), currentUser.getImageUrl().getIsPublic()));

        FirebaseManager.getInstance().addCallback(this);
        FirebaseManager.getInstance().addUserListener();
    }

    public void onDestroy()
    {
        FirebaseManager.getInstance().removeUserListener();
        FirebaseManager.getInstance().removeCallback(this);
    }

    public void addObserver(Observer client) {
        if (!observers.contains(client)) {
            observers.add(client);
        }
    }

    public void uploadProfileImage(Bitmap bitmap)
    {
        FirebaseManager.getInstance().uploadProfileImage(bitmap);
    }

    public void removeObserver(Observer clientToRemove) {
        if (observers.contains(clientToRemove)) {
            observers.remove(clientToRemove);
        }
    }

    private void notifyObservers(int eventType, String message) {
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

        _newUser.setDisplayName(new FieldModel<>(currentUser.getDisplayName().getValue(), currentUser.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<>(currentUser.getYearBorn().getValue(), currentUser.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<>(currentUser.getGender().getValue(), currentUser.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<>(currentUser.getCity().getValue(), currentUser.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<>(currentUser.getCountry().getValue(), currentUser.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<>(currentUser.getWeight().getValue(), currentUser.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<>(currentUser.getHeight().getValue(), currentUser.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<>(currentUser.getPhoneNumber().getValue(), currentUser.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<>(currentUser.getFacebook().getValue(), currentUser.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<>(currentUser.getTwitter().getValue(), currentUser.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<>(currentUser.getAddress().getValue(), currentUser.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<>(currentUser.getJob().getValue(), currentUser.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<>(currentUser.getImageUrl().getValue(), currentUser.getImageUrl().getIsPublic()));
    }

    public void LogOut()
    {
        FirebaseManager.getInstance().signOut();
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);
        notifyObservers(MyUtils.LOG_OUT, null);
    }

    @Override
    public void onMessage(MessageItemModel message) {

    }

    @Override
    public void onChatroom(String roomName) {

    }

    @Override
    public void onChatEnded() {

    }

    @Override
    public void onUserUpdated(UserModel user) {
        UserModel currentUser = FirebaseManager.getInstance().getUser();

        _newUser.setDisplayName(new FieldModel<>(currentUser.getDisplayName().getValue(), currentUser.getDisplayName().getIsPublic()));
        _newUser.setYearBorn(new FieldModel<>(currentUser.getYearBorn().getValue(), currentUser.getYearBorn().getIsPublic()));
        _newUser.setGender(new FieldModel<>(currentUser.getGender().getValue(), currentUser.getGender().getIsPublic()));
        _newUser.setCity(new FieldModel<>(currentUser.getCity().getValue(), currentUser.getCity().getIsPublic()));
        _newUser.setCountry(new FieldModel<>(currentUser.getCountry().getValue(), currentUser.getCountry().getIsPublic()));
        _newUser.setWeight(new FieldModel<>(currentUser.getWeight().getValue(), currentUser.getWeight().getIsPublic()));
        _newUser.setHeight(new FieldModel<>(currentUser.getHeight().getValue(), currentUser.getHeight().getIsPublic()));
        _newUser.setPhoneNumber(new FieldModel<>(currentUser.getPhoneNumber().getValue(), currentUser.getPhoneNumber().getIsPublic()));
        _newUser.setFacebook(new FieldModel<>(currentUser.getFacebook().getValue(), currentUser.getFacebook().getIsPublic()));
        _newUser.setTwitter(new FieldModel<>(currentUser.getTwitter().getValue(), currentUser.getTwitter().getIsPublic()));
        _newUser.setAddress(new FieldModel<>(currentUser.getAddress().getValue(), currentUser.getAddress().getIsPublic()));
        _newUser.setJob(new FieldModel<>(currentUser.getJob().getValue(), currentUser.getJob().getIsPublic()));
        _newUser.setImageUrl(new FieldModel<>(currentUser.getImageUrl().getValue(), currentUser.getImageUrl().getIsPublic()));
    }
}
