package com.example.ijuin.testapplication.viewmodels;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Khang Le on 11/13/2017.
 */

public class ConnectionDetector
{
    Context _context;

    public ConnectionDetector(Context context)
    {
        this._context = context;
    }

    public boolean isConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null)
            {
                if(info.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }

        return false;
    }
}
