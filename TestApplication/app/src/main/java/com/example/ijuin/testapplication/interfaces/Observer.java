package com.example.ijuin.testapplication.interfaces;

/**
 * Created by Khanh on 11/11/2017.
 */

public interface Observer<T>
{
    void onObserve(int event, T eventMessage);
}
