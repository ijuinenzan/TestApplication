package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ijuin.testapplication.BR;

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
    private boolean _isFindingMale;
    private boolean _isFindingFemale;
    private Integer _minAge;
    private Integer _maxAge;

    @Bindable
    public FieldModel<String> getImageUrl()
    {
        return _imageUrl;
    }
    public void setImageUrl(FieldModel<String> _imageUrl)
    {
        this._imageUrl = _imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }
    @Bindable
    public FieldModel<String> getDisplayName() {
        return _displayName;
    }
    public void setDisplayName(FieldModel<String> _displayName) {
        this._displayName = _displayName;
        notifyPropertyChanged(BR.displayName);
    }
    @Bindable
    public FieldModel<Integer> getYearBorn() {
        return _yearBorn;
    }
    public void setYearBorn(FieldModel<Integer> _yearBorn) {
        this._yearBorn = _yearBorn;
        notifyPropertyChanged(BR.yearBorn);
    }
    @Bindable
    public FieldModel<Boolean> getGender() {
        return _gender;
    }
    public void setGender(FieldModel<Boolean> _gender) {
        this._gender = _gender;
        notifyPropertyChanged(BR.gender);
    }
    @Bindable
    public FieldModel<String> getCity() {
        return _city;
    }
    public void setCity(FieldModel<String> _city) {
        this._city = _city;
        notifyPropertyChanged(BR.city);
    }
    @Bindable
    public FieldModel<String> getCountry() {
        return _country;
    }
    public void setCountry(FieldModel<String> _country) {
        this._country = _country;
        notifyPropertyChanged(BR.country);
    }
    @Bindable
    public FieldModel<Float> getWeight() {
        return _weight;
    }
    public void setWeight(FieldModel<Float> _weight) {
        this._weight = _weight;
        notifyPropertyChanged(BR.weight);
    }
    @Bindable
    public FieldModel<Float> getHeight() {
        return _height;
    }
    public void setHeight(FieldModel<Float> _height) {
        this._height = _height;
        notifyPropertyChanged(BR.height);
    }
    @Bindable
    public FieldModel<String> getPhoneNumber() {
        return _phoneNumber;
    }
    public void setPhoneNumber(FieldModel<String> _phoneNumber) {
        this._phoneNumber = _phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }
    @Bindable
    public FieldModel<String> getFacebook() {
        return _facebook;
    }
    public void setFacebook(FieldModel<String> facebook) {
        this._facebook = facebook;
        notifyPropertyChanged(BR.facebook);
    }
    @Bindable
    public FieldModel<String> getTwitter() {
        return _twitter;
    }
    public void setTwitter(FieldModel<String> _twitter) {
        this._twitter = _twitter;
        notifyPropertyChanged(BR.twitter);
    }
    @Bindable
    public FieldModel<String> getAddress() {
        return _address;
    }
    public void setAddress(FieldModel<String> _address) {
        this._address = _address;
        notifyPropertyChanged(BR.address);
    }
    @Bindable
    public FieldModel<String> getJob() {
        return _job;
    }
    public void setJob(FieldModel<String> _job) {
        this._job = _job;
        notifyPropertyChanged(BR.job);
    }

    @Bindable
    public String getState() {
        return _state;
    }

    public void setState(String _state) {
        this._state = _state;
        notifyPropertyChanged(BR.state);
        notifyPropertyChanged(BR.finding);
    }

    @Bindable
    public Boolean getIsFindingFemale() {
        return _isFindingFemale;
    }

    public void setIsFindingFemale(boolean _isFindingFemale) {
        this._isFindingFemale = _isFindingFemale;
        notifyPropertyChanged(BR.isFindingFemale);
    }

    @Bindable
    public Boolean getIsFindingMale() {
        return _isFindingMale;
    }

    public void setIsFindingMale(boolean _isFindingMale) {
        this._isFindingMale = _isFindingMale;
        notifyPropertyChanged(BR.isFindingMale);
    }

    @Bindable
    public Integer getMaxAge() {
        return _maxAge;
    }

    public void setMaxAge(Integer _maxAge) {
        this._maxAge = _maxAge;
        notifyPropertyChanged(BR.maxAge);
    }

    @Bindable
    public Integer getMinAge() {
        return _minAge;
    }

    public void setMinAge(Integer _minAge) {
        this._minAge = _minAge;
        notifyPropertyChanged(BR.minAge);
    }
}
