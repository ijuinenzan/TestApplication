package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.FirebaseManager;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/17/2017.
 */

public class SearchViewModel extends BaseObservable
{
    public ArrayList<Observer> observers;

    private UserModel _newUser;


    public SearchViewModel() {
        observers=new ArrayList<>();

        UserModel currentUser = FirebaseManager.getInstance().getUser();

        _newUser = new UserModel();
        _newUser.setState(currentUser.getState());
        _newUser.setIsFindingFemale(currentUser.getIsFindingFemale());
        _newUser.setIsFindingMale(currentUser.getIsFindingMale());
        _newUser.setMinAge(currentUser.getMinAge());
        _newUser.setMaxAge(currentUser.getMaxAge());
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
        else if(_newUser.getState().equals("Not Finding"))
        {
            _newUser.setState("Finding");
            notifyPropertyChanged(BR.finding);
        }

        UserModel currentUser = FirebaseManager.getInstance().getUser();


        currentUser.setState(_newUser.getState());
        currentUser.setIsFindingFemale(_newUser.getIsFindingFemale());
        currentUser.setIsFindingMale(_newUser.getIsFindingMale());
        currentUser.setMinAge(_newUser.getMinAge());
        currentUser.setMaxAge(_newUser.getMaxAge());

        FirebaseManager.getInstance().updateUser(currentUser);

        _newUser.setState(currentUser.getState());
        _newUser.setIsFindingFemale(currentUser.getIsFindingFemale());
        _newUser.setIsFindingMale(currentUser.getIsFindingMale());
        _newUser.setMinAge(currentUser.getMinAge());
        _newUser.setMaxAge(currentUser.getMaxAge());

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
        return FirebaseManager.getInstance().getUser().getState().equals("Finding");
    }
}