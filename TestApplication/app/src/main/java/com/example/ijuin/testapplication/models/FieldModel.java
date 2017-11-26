package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * Created by ASUS on 11/22/2017.
 */

public class FieldModel <T> extends BaseObservable implements Serializable {
    private T _value;
    private Boolean _isPublic;

    public FieldModel (T value, Boolean isPublic)
    {
        _value = value;
        _isPublic = isPublic;
    }

    @Bindable
    public T getValue() {
        return _value;
    }
    public void setValue(T _value) {
        this._value = _value;
    }
    public Boolean getIsPublic() {
        return _isPublic;
    }
    public void setIsPublic(Boolean _isPublic) {
        this._isPublic = _isPublic;
    }
}
