package com.example.ijuin.testapplication.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.utils.TextWatcherAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.BR;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by ijuin on 11/12/2017.
 */

public class LoginViewModel extends BaseObservable
{
    private boolean _isAuthDone;
    private boolean _isAuthInProgress;

    private ObservableField<String> _email  = new ObservableField<>();
    private ObservableField<String> _password = new ObservableField<>();

    private TextWatcher _emailWatcher = new TextWatcherAdapter(){
    @Override public void afterTextChanged(Editable s)
    {
            _email.set(s.toString());
    }
};
    private TextWatcher _passwordWatcher = new TextWatcherAdapter(){
        @Override public void afterTextChanged(Editable s)
        {
                _password.set(s.toString());
        }
    };

    public ArrayList<Observer> observers;

    public LoginViewModel() {
        observers=new ArrayList<>();
        _email.set("");
        _password.set("");
    }

    @Bindable
    public boolean isAuthDone() {
        return _isAuthDone;
    }

    public void setAuthDone(boolean authDone) {
        _isAuthDone = authDone;
        notifyPropertyChanged(BR.authDone);
    }

    @Bindable
    public boolean isAuthInProgress() {
        return _isAuthInProgress;
    }

    public void setAuthInProgress(boolean authInProgress) {
        _isAuthInProgress = authInProgress;
        notifyPropertyChanged(BR.authInProgress);
    }

    @Bindable
    public ObservableField<String> getEmail()
    {
        return _email;
    }

    @Bindable
    public ObservableField<String> getPassword()
    {
        return _password;
    }

    @Bindable
    public TextWatcher getEmailWatcher()
    {
        return _emailWatcher;
    }

    public TextWatcher getPasswordWatcher()
    {
        return _passwordWatcher;
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

    public void loginWithEmail() {
        setAuthInProgress(true);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(_email.get(), _password.get())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (!task.isSuccessful()) {
                            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                        } else {
                            setAuthDone(true);
                        }
                    }
        });
    }

    public void register()
    {
        setAuthInProgress(true);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(_email.get(), _password.get())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setAuthInProgress(false);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.sendEmailVerification();
                    }
                });
    }


    public void loginTwitter (TwitterSession session)
    {
        setAuthInProgress(true);
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (task.isSuccessful())
                        {
                            setAuthDone(true);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        }
                    }
                });
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
}
