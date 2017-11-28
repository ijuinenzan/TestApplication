package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.FirebaseCallbacks;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/17/2017.
 */

public class SearchViewModel extends BaseObservable
{
    public ArrayList<Observer> observers;

    private UserModel _user;
    private UserModel _newUser;


    public SearchViewModel(UserModel user) {
        observers=new ArrayList<>();

        _user = user;
        _newUser = new UserModel();
        _newUser.setState(_user.getState());
        _newUser.setIsFindingFemale(_user.getIsFindingFemale());
        _newUser.setIsFindingMale(_user.getIsFindingMale());
        _newUser.setMinAge(_user.getMinAge());
        _newUser.setMaxAge(_user.getMaxAge());
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



    public void findPartner()
    {
        if(_newUser.getState().equals("Finding"))
        {
            _newUser.setState("Not Finding");
            notifyPropertyChanged(BR.finding);
        }
        else
        {
            _newUser.setState("Finding");
            notifyPropertyChanged(BR.finding);
        }

        _user.setState(_newUser.getState());
        _user.setIsFindingFemale(_newUser.getIsFindingFemale());
        _user.setIsFindingMale(_newUser.getIsFindingMale());
        _user.setMinAge(_newUser.getMinAge());
        _user.setMaxAge(_newUser.getMaxAge());

        FirebaseManager.getInstance().updateUser(_user);

        _newUser.setState(_user.getState());
        _newUser.setIsFindingFemale(_user.getIsFindingFemale());
        _newUser.setIsFindingMale(_user.getIsFindingMale());
        _newUser.setMinAge(_user.getMinAge());
        _newUser.setMaxAge(_user.getMaxAge());

    }

    @Bindable
    public String getState()
    {
        return _newUser.getState();
    }

    public void setState(String value)
    {
        _newUser.setState(value);
        notifyPropertyChanged(BR.state);
    }

    @Bindable
    public boolean getIsFindingMale()
    {
        return _newUser.getIsFindingMale();
    }

    public void setIsFindingMale(boolean value)
    {
        _newUser.setIsFindingMale(value);
        notifyPropertyChanged(BR.isFindingMale);
    }

    @Bindable
    public boolean getIsFindingFemale()
    {
        return _newUser.getIsFindingFemale();
    }

    public void setIsFindingFemale(boolean value)
    {
        _newUser.setIsFindingFemale(value);
        notifyPropertyChanged(BR.isFindingFemale);
    }

    @Bindable
    public int getMinAge()
    {
        return _newUser.getMinAge();
    }

    public void setMinAge(int value)
    {
        _newUser.setMinAge(value);
        notifyPropertyChanged(BR.minAge);
    }

    @Bindable
    public int getMaxAge()
    {
        return _newUser.getMaxAge();
    }

    public void setMaxAge(int value)
    {
        _newUser.setMaxAge(value);
        notifyPropertyChanged(BR.maxAge);
    }

    @Bindable
    public boolean isFinding()
    {
        return _newUser.getState().equals("Finding");
    }
}