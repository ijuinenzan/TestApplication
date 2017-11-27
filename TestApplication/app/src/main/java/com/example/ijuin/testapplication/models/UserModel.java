package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ASUS on 11/17/2017.
 */

public class UserModel extends BaseObservable implements Serializable {
    private FieldModel<String> _imageUrl;
    private FieldModel<String> _displayName;
    private FieldModel<Integer> _yearBorn;
    private FieldModel<Boolean> _gender;
    private FieldModel<String> _city;
    private FieldModel<String> _country;
    private FieldModel<Float> _weight;
    private FieldModel<Float> _height;
    private FieldModel<String> _phoneNumber;
    private FieldModel<String> _facebook;
    private FieldModel<String> _twitter;
    private FieldModel<String> _address;
    private FieldModel<String> _job;
    private String _state;
    private Boolean _isFindingMale;
    private Boolean _isFindingFemale;
    private Integer _minTargetYearBorn;
    private Integer _maxTargetYearBorn;

    @Bindable
    public FieldModel<String> getImageUrl()
    {
        return _imageUrl;
    }
    public void setImageUrl(FieldModel<String> _imageUrl)
    {
        this._imageUrl = _imageUrl;
    }
    public FieldModel<String> getDisplayName() {
        return _displayName;
    }
    public void setDisplayName(FieldModel<String> _displayName) {
        this._displayName = _displayName;
    }
    public FieldModel<Integer> getYearBorn() {
        return _yearBorn;
    }
    public void setYearBorn(FieldModel<Integer> _yearBorn) {
        this._yearBorn = _yearBorn;
    }
    public FieldModel<Boolean> getGender() {
        return _gender;
    }
    public void setGender(FieldModel<Boolean> _gender) {
        this._gender = _gender;
    }
    public FieldModel<String> getCity() {
        return _city;
    }
    public void setCity(FieldModel<String> _city) {
        this._city = _city;
    }
    public FieldModel<String> getCountry() {
        return _country;
    }
    public void setCountry(FieldModel<String> _country) {
        this._country = _country;
    }
    public FieldModel<Float> getWeight() {
        return _weight;
    }
    public void setWeight(FieldModel<Float> _weight) {
        this._weight = _weight;
    }
    public FieldModel<Float> getHeight() {
        return _height;
    }
    public void setHeight(FieldModel<Float> _height) {
        this._height = _height;
    }
    public FieldModel<String> getPhoneNumber() {
        return _phoneNumber;
    }
    public void setPhoneNumber(FieldModel<String> _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }
    public FieldModel<String> getFacebook() {
        return _facebook;
    }
    public void setFacebook(FieldModel<String> facebook) {
        this._facebook = facebook;
    }
    public FieldModel<String> getTwitter() {
        return _twitter;
    }
    public void setTwitter(FieldModel<String> _twitter) {
        this._twitter = _twitter;
    }
    public FieldModel<String> getAddress() {
        return _address;
    }
    public void setAddress(FieldModel<String> _address) {
        this._address = _address;
    }
    public FieldModel<String> getJob() {
        return _job;
    }
    public void setJob(FieldModel<String> _job) {
        this._job = _job;
    }

    public String getState() {
        return _state;
    }

    public void setState(String _state) {
        this._state = _state;
    }

    public Boolean getIsFindingFemale() {
        return _isFindingFemale;
    }

    public void setIsFindingFemale(Boolean _isFindingFemale) {
        this._isFindingFemale = _isFindingFemale;
    }

    public Boolean getIsFindingMale() {
        return _isFindingMale;
    }

    public void setIsFindingMale(Boolean _isFindingMale) {
        this._isFindingMale = _isFindingMale;
    }

    public Integer getMaxTargetYearBorn() {
        return _maxTargetYearBorn;
    }

    public void setMaxTargetYearBorn(Integer _maxTargetYearBorn) {
        this._maxTargetYearBorn = _maxTargetYearBorn;
    }

    public Integer getMinTargetYearBorn() {
        return _minTargetYearBorn;
    }

    public void setMinTargetYearBorn(Integer _minTargetYearBorn) {
        this._minTargetYearBorn = _minTargetYearBorn;
    }
}
