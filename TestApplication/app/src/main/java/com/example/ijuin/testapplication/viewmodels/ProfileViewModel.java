package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.FieldModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.MyUtils;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/22/2017.
 */

public class ProfileViewModel extends BaseObservable {

    private UserModel _user;
    public ArrayList<Observer> observers;

    public ProfileViewModel(UserModel user) {
        observers=new ArrayList<>();
        _user = user;
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
        return _user;
    }

    public void Change()
    {

    }

    public void LogOut()
    {

    }
}
