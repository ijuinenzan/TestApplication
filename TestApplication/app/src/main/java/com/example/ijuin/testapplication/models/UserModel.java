package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ASUS on 11/17/2017.
 */

public class UserModel extends BaseObservable {
    private String _displayName;
    private String _email;
    private String _linkFb;
    private String _firstName;
    private String _lastName;
    private String _localId;
    private String _photoUrl;
    private int _state;
    private int _gender;
    private int _age;
    private int _targetGender;

    @Bindable
    public String get_displayName() {
        return _displayName;
    }
    public void set_displayName(String _displayName) {
        this._displayName = _displayName;
    }
    public String get_email() {
        return _email;
    }
    public void set_email(String _email) {
        this._email = _email;
    }
    public String get_linkFb() {
        return _linkFb;
    }
    public void set_linkFb(String _linkFb) {
        this._linkFb = _linkFb;
    }
    public String get_firstName() {
        return _firstName;
    }
    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }
    public String get_lastName() {
        return _lastName;
    }
    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }
    public String get_localId() {
        return _localId;
    }
    public void set_localId(String _localId) {
        this._localId = _localId;
    }
    public String get_photoUrl() {
        return _photoUrl;
    }
    public void set_photoUrl(String _photoUrl) {
        this._photoUrl = _photoUrl;
    }
    public int get_state() {
        return _state;
    }
    public void set_state(int _state) {
        this._state = _state;
    }
    public int get_gender() {
        return _gender;
    }
    public void set_gender(int _gender) {
        this._gender = _gender;
    }
    public int get_age() {
        return _age;
    }
    public void set_age(int _age) {
        this._age = _age;
    }
    public int get_targetGender() {
        return _targetGender;
    }
    public void set_targetGender(int _targetGender) {
        this._targetGender = _targetGender;
    }
}
