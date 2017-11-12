package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.BR;
/**
 * Created by ijuin on 11/12/2017.
 */

public class LoginViewModel extends BaseObservable
{
    private boolean isAuthDone;
    private boolean isAuthInProgress;
    private boolean isFinding;
    private int _selectedGender;


    public ArrayList<Observer> observers;



    public LoginViewModel() {
        _selectedGender = 0;
        observers=new ArrayList<>();
        isFinding = false;
    }

    @Bindable
    public boolean isAuthDone() {
        return isAuthDone;
    }

    public void setAuthDone(boolean authDone) {
        isAuthDone = authDone;
        notifyPropertyChanged(BR.authDone);
    }

    @Bindable
    public boolean isAuthInProgress() {
        return isAuthInProgress;
    }

    public void setAuthInProgress(boolean authInProgress) {
        isAuthInProgress = authInProgress;
        notifyPropertyChanged(BR.authInProgress);
    }

    public void firebaseAnonymousAuth() {
        setAuthInProgress(true);
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (!task.isSuccessful()) {
                            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                        } else {
                            setAuthDone(true);
                        }
                    }
                });
    }

    public void findPartner()
    {
        setFinding(true);

    }

    public void invalidateRoomName(String roomName) {

        if (roomName.trim().isEmpty()){
            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_INVALIDE_ROOM_NAME);
        } else {
            notifyObservers(MyUtils.OPEN_ACTIVITY, roomName);
        }
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
}
