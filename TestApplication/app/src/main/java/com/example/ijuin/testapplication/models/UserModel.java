package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ASUS on 11/17/2017.
 */

public class UserModel extends BaseObservable {
    private FieldModel<String> _displayName;
    private FieldModel<String> _yearBorn;
    private FieldModel<Integer> _gender;
    private FieldModel<String> _city;
    private FieldModel<String> _country;
    private FieldModel<String> _weight;
    private FieldModel<String> _height;
    private FieldModel<String> _phoneNumber;
    private FieldModel<String> _facebook;
    private FieldModel<String> _twitter;
    private FieldModel<String> _address;
    private FieldModel<String> _job;

    @Bindable
    public FieldModel<String> get_displayName() {
        return _displayName;
    }
    public void set_displayName(FieldModel<String> _displayName) {
        this._displayName = _displayName;
    }
    public FieldModel<String> get_yearBorn() {
        return _yearBorn;
    }
    public void set_yearBorn(FieldModel<String> _yearBorn) {
        this._yearBorn = _yearBorn;
    }
    public FieldModel<Integer> get_gender() {
        return _gender;
    }
    public void set_gender(FieldModel<Integer> _gender) {
        this._gender = _gender;
    }
    public FieldModel<String> get_city() {
        return _city;
    }
    public void set_city(FieldModel<String> _city) {
        this._city = _city;
    }
    public FieldModel<String> get_country() {
        return _country;
    }
    public void set_country(FieldModel<String> _country) {
        this._country = _country;
    }
    public FieldModel<String> get_weight() {
        return _weight;
    }
    public void set_weight(FieldModel<String> _weight) {
        this._weight = _weight;
    }
    public FieldModel<String> get_height() {
        return _height;
    }
    public void set_height(FieldModel<String> _height) {
        this._height = _height;
    }
    public FieldModel<String> get_phoneNumber() {
        return _phoneNumber;
    }
    public void set_phoneNumber(FieldModel<String> _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }
    public FieldModel<String> get_facebook() {
        return _facebook;
    }
    public void set_facebook(FieldModel<String> _facebook) {
        this._facebook = _facebook;
    }
    public FieldModel<String> get_twitter() {
        return _twitter;
    }
    public void set_twitter(FieldModel<String> _twitter) {
        this._twitter = _twitter;
    }
    public FieldModel<String> get_address() {
        return _address;
    }
    public void set_address(FieldModel<String> _address) {
        this._address = _address;
    }
    public FieldModel<String> get_job() {
        return _job;
    }
    public void set_job(FieldModel<String> _job) {
        this._job = _job;
    }
}
