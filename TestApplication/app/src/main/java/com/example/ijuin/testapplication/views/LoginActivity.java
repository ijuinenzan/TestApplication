package com.example.ijuin.testapplication.views;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.example.ijuin.testapplication.viewmodels.LoginViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements Observer<String> {

    private LoginViewModel mViewModel;
    private Dialog mChatRoomDialog;

    private Button _facebookFirebaseLoginButon;
    private LoginButton _facebookLoginButon;

    private CallbackManager _callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        mViewModel= new LoginViewModel();
        activityLoginBinding.setViewModel(mViewModel);
        activityLoginBinding.setActivity(this);

        _facebookFirebaseLoginButon = (Button) findViewById(R.id.btnFirebaseFacebookLogin);
        _facebookLoginButon = (LoginButton)findViewById(R.id.btnFacebookLogin);

        _facebookFirebaseLoginButon.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
