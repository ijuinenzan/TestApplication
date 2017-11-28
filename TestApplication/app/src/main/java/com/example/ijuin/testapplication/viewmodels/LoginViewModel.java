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

import com.example.ijuin.testapplication.factories.UserFactory;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.utils.TextWatcherAdapter;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.BR;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private String _email  = "";
    private String _password = "";


    private DatabaseReference _userReference;

    public ArrayList<Observer> observers;

    public LoginViewModel() {
        observers=new ArrayList<>();

        _userReference = FirebaseDatabase.getInstance().getReference("users");
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
    public String getEmail()
    {
        return _email;
    }

    public void setEmail(String value)
    {
        _email = value;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String value)
    {
        _password = value;
        notifyPropertyChanged(BR.password);
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
                            _userReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()))
                                    {
                                        DataSnapshot childRef = dataSnapshot.child(FirebaseAuth.getInstance().getUid());
                                        UserModel user = childRef.getValue(UserModel.class);

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                    else
                                    {
                                        UserModel user = UserFactory.createNewUser();
                                        _userReference.child(FirebaseAuth.getInstance().getUid()).setValue(UserFactory.createNewUser());

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
    }

    public void loginWithFacebook(String token)
    {
        AuthCredential credential = FacebookAuthProvider.getCredential(token);

        setAuthInProgress(true);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (!task.isSuccessful()) {
                            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                        } else {
                            _userReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()))
                                    {
                                        DataSnapshot childRef = dataSnapshot.child(FirebaseAuth.getInstance().getUid());
                                        UserModel user = childRef.getValue(UserModel.class);

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                    else
                                    {
                                        UserModel user = UserFactory.createNewUser();
                                        _userReference.child(FirebaseAuth.getInstance().getUid()).setValue(UserFactory.createNewUser());

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });

    }


    public void loginWithEmail() {
        setAuthInProgress(true);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (!task.isSuccessful()) {
                            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                        } else {
                            _userReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()))
                                    {
                                        DataSnapshot childRef = dataSnapshot.child(FirebaseAuth.getInstance().getUid());
                                        UserModel user = childRef.getValue(UserModel.class);

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                    else
                                    {
                                        UserModel user = UserFactory.createNewUser();
                                        _userReference.child(FirebaseAuth.getInstance().getUid()).setValue(UserFactory.createNewUser());

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
        });
    }

    public void registerWithEmail()
    {
        setAuthInProgress(true);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setAuthInProgress(false);

                        if(task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user != null)
                            {
                                user.sendEmailVerification();
                            }
                        }
                    }
                });
    }

    public void loginWithTwitter (String token, String secret) {
        setAuthInProgress(true);
        AuthCredential credential = TwitterAuthProvider.getCredential(
                token,
                secret);

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        setAuthInProgress(false);
                        if (!task.isSuccessful()) {
                            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                        } else {
                            _userReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()))
                                    {
                                        DataSnapshot childRef = dataSnapshot.child(FirebaseAuth.getInstance().getUid());
                                        UserModel user = childRef.getValue(UserModel.class);

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                    else
                                    {
                                        UserModel user = UserFactory.createNewUser();
                                        _userReference.child(FirebaseAuth.getInstance().getUid()).setValue(UserFactory.createNewUser());

                                        setAuthDone(true);

                                        _userReference.removeEventListener(this);

                                        notifyObservers(MyUtils.OPEN_ACTIVITY, user);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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

    public void notifyObservers(int eventType, UserModel userModel) {
        for (int i=0; i< observers.size(); i++) {
            observers.get(i).onObserve(eventType, userModel);
        }
    }


    public void notifyObservers(int eventType, String message) {
        for (int i=0; i< observers.size(); i++) {
            observers.get(i).onObserve(eventType, message);
        }
    }
}
