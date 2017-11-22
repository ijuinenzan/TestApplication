package com.example.ijuin.testapplication.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityLoginBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.LoginViewModel;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity implements Observer<String> {

    private LoginViewModel mViewModel;

    private Button _facebookFirebaseLoginButton;
    private LoginButton _facebookLoginButon;

    private Button _twitterFirebaseLoginButton;
    private TwitterLoginButton _twitterLoginButton;

    private CallbackManager _callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_login);



        ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        mViewModel= new LoginViewModel();
        activityLoginBinding.setViewModel(mViewModel);
        activityLoginBinding.setActivity(this);


        _facebookFirebaseLoginButton = (Button) findViewById(R.id.btnFirebaseFacebookLogin);
        _facebookLoginButon = (LoginButton)findViewById(R.id.btnFacebookLogin);

        _facebookFirebaseLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _facebookLoginButon.performClick();
            }
        });

        _callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mViewModel.loginWithFacebook(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        _twitterFirebaseLoginButton = (Button) findViewById(R.id.btnFirebaseTwitterLogin);
        _twitterLoginButton = (TwitterLoginButton)findViewById(R.id.btnTwitterLogin);


        _twitterFirebaseLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _twitterLoginButton.performClick();
            }
        });

        _twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mViewModel.loginWithTwitter(result.data.getAuthToken().token, result.data.getAuthToken().secret);
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });

    }

    public void startMainActivity() {

        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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
            startMainActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
        _twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

}
