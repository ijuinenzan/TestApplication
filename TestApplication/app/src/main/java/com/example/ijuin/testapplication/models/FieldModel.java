package com.example.ijuin.testapplication.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ASUS on 11/22/2017.
 */

public class FieldModel <T> extends BaseObservable {
    private T _value;
    private Boolean _isPublic;

    public FieldModel (T value, Boolean isPublic)
    {
        _value = value;
        _isPublic = isPublic;
    }

    @Bindable
    public T get_value() {
        return _value;
    }
    public void set_value(T _value) {
        this._value = _value;
    }
    public Boolean get_isPublic() {
        return _isPublic;
    }
    public void set_isPublic(Boolean _isPublic) {
        this._isPublic = _isPublic;
    }
}
