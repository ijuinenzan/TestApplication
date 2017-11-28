package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.MyUtils;

import java.util.ArrayList;

import okhttp3.internal.Util;

/**
 * Created by ASUS on 11/17/2017.
 */

public class SearchViewModel extends BaseObservable
{
    private boolean isFinding;
    private boolean _selectedGender;
    private boolean _selectedGender2;
    private float _selectedMaxAge;
    private float _selectedMinAge;
    private int _selectedLocation;


    public ArrayList<Observer> observers;



    public SearchViewModel() {
        _selectedGender = MyUtils.MALE;
        _selectedGender2 = MyUtils.FEMALE;
        observers=new ArrayList<>();
        isFinding = false;
        set_selectedMaxAge(25);
        set_selectedMinAge(15);
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
    public boolean get_selectedGender() {
        return _selectedGender;
    }

    public void set_selectedGender(boolean _selectedGender) {
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
    public float get_selectedMaxAge() {
        return _selectedMaxAge;
    }

    public void set_selectedMaxAge(float _selectedMaxAge) {
        this._selectedMaxAge = _selectedMaxAge;
        notifyPropertyChanged(BR._selectedMaxAge);
    }

    @Bindable
    public float get_selectedMinAge() {
        return _selectedMinAge;
    }

    public void set_selectedMinAge(float _selectedMinAge) {
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

    @Bindable
    public boolean is_selectedGender2() {
        return _selectedGender2;
    }

    public void set_selectedGender2(boolean _selectedGender2) {
        this._selectedGender2 = _selectedGender2;
        notifyPropertyChanged(BR._selectedGender2);
    }
}