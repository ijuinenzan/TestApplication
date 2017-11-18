package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.Observer;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/17/2017.
 */

public class SearchViewModel extends BaseObservable
{
    private boolean isFinding;
    private int _selectedGender;
    private int _selectedMaxAge;
    private int _selectedMinAge;
    private int _selectedLocation;


    public ArrayList<Observer> observers;



    public SearchViewModel() {
        _selectedGender = 0;
        observers=new ArrayList<>();
        isFinding = false;
    }



    public void findPartner()
    {
        setFinding(true);

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
    public int get_selectedGender() {
        return _selectedGender;
    }

    public void set_selectedGender(int _selectedGender) {
        this._selectedGender = _selectedGender;
        notifyPropertyChanged(BR._selectedGender);
    }

    @Bindable
    public boolean isFinding() {
        return isFinding;
    }

    public void setFinding(boolean finding) {
        isFinding = finding;
        notifyPropertyChanged(BR.finding);
    }

    @Bindable
    public int get_selectedMaxAge() {
        return _selectedMaxAge;
    }

    public void set_selectedMaxAge(int _selectedMaxAge) {
        this._selectedMaxAge = _selectedMaxAge;
        notifyPropertyChanged(BR._selectedMaxAge);
    }

    @Bindable
    public int get_selectedMinAge() {
        return _selectedMinAge;
    }

    public void set_selectedMinAge(int _selectedMinAge) {
        this._selectedMinAge = _selectedMinAge;
        notifyPropertyChanged(BR._selectedMinAge);
    }

    @Bindable
    public int get_selectedLocation() {
        return _selectedLocation;
    }

    public void set_selectedLocation(int _selectedLocation) {
        this._selectedLocation = _selectedLocation;
        notifyPropertyChanged(BR._selectedLocation);
    }
}