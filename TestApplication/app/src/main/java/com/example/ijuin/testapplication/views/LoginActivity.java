package com.example.ijuin.testapplication.views;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityLoginBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.ConnectionDetector;
import com.example.ijuin.testapplication.viewmodels.LoginViewModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity implements Observer<String> {

    private LoginViewModel mViewModel;
    private Dialog mChatRoomDialog;
    public ConnectionDetector _checkInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        mViewModel= new LoginViewModel();
        _checkInternet = new ConnectionDetector(this);
        activityLoginBinding.setViewModel(mViewModel);
        activityLoginBinding.setActivity(this);

        Button btnFirebaseTwitterLogin = (Button) findViewById(R.id.btnFirebaseTwitterLogin);
        final TwitterLoginButton btnLoginTwitter = (TwitterLoginButton) findViewById(R.id.btnTwitterLogin);

        btnFirebaseTwitterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginTwitter.setCallback(new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        // TwitterSession will contain a token, secret, username, and user ID of the user
                        // and becomes the active session and is automatically persisted.
//                        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                        TwitterAuthToken authToken = session.getAuthToken();
                        mViewModel.loginTwitter(result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    } });
            }
        });


    }

    public void startChatActivity(String roomName) {
        mChatRoomDialog.dismiss();
        mChatRoomDialog=null;

        Intent intent=new Intent(this,ChatActivity.class);
        intent.putExtra(MyUtils.EXTRA_ROOM_NAME,roomName);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.removeObserver(this);
    }

    @Override
    public void onObserve(int event, String eventString) {

        if (event == MyUtils.SHOW_TOAST) {
            Toast.makeText(this,eventString,Toast.LENGTH_SHORT).show();
        } else if (event == MyUtils.OPEN_ACTIVITY) {
            startChatActivity(eventString);
        }
    }

}
