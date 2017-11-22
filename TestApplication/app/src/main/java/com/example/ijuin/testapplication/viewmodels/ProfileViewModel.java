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

    private UserModel user;
    public ArrayList<Observer> observers;

    public ProfileViewModel() {
        observers=new ArrayList<>();
        user = new UserModel();
        user.set_displayName(new FieldModel<String>("KIENG", true));
        user.set_gender(new FieldModel<Integer>(MyUtils.MALE, true));
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
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    public void Change()
    {

    }

    public void LogOut()
    {

    }
}
