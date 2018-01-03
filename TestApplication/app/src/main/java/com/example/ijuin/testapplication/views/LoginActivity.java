package com.example.ijuin.testapplication.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityLoginBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.LoginViewModel;

import com.facebook.AccessToken;
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

public class LoginActivity extends AppCompatActivity implements Observer<Object>  {

    //region DECLARE VARIABLE
    private LoginViewModel mViewModel;
    private Button _facebookFirebaseLoginButton;
    private LoginButton _facebookLoginButon;
    private Button _twitterFirebaseLoginButton;
    private TwitterLoginButton _twitterLoginButton;
    private CallbackManager _callbackManager;
    private Button _btnAnonymousLogin;
    private boolean _isPressedBack = false;

    // ask permisstion to draw over other app for bubble chatheads
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    // Login
    private ProgressBar _pBar;
    private TextView _txtLogin;
    private DisplayMetrics dm;
    private RelativeLayout _layoutLogin;
    private ValueAnimator _valueAnimator;
    private RelativeLayout.LayoutParams _button_login_lp;
    private RelativeLayout.LayoutParams _old_button_login_lp;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this))
        {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

        addControls();

        if(AccessToken.getCurrentAccessToken() != null)
        {
            mViewModel.loginWithFacebook(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getUserId());
        }
    }

    private void addControls()
    {
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

        _facebookFirebaseLoginButton = findViewById(R.id.btnFirebaseFacebookLogin);
        _facebookLoginButon = findViewById(R.id.btnFacebookLogin);

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
                mViewModel.loginWithFacebook(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId());
                _pBar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                _txtLogin.animate().setStartDelay(100).setDuration(500).alpha(0).start();

                loginResult.getAccessToken().getUserId();

                // Scale button Login smaller
                _button_login_lp.width=Math.round(200);
                _layoutLogin.setLayoutParams(_button_login_lp);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        _twitterFirebaseLoginButton = findViewById(R.id.btnFirebaseTwitterLogin);
        _twitterLoginButton = findViewById(R.id.btnTwitterLogin);


        _twitterFirebaseLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _twitterLoginButton.performClick();
            }
        });

        _twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mViewModel.loginWithTwitter(result.data.getAuthToken().token, result.data.getAuthToken().secret, result.data.getUserName());
                _pBar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                _txtLogin.animate().setStartDelay(100).setDuration(500).alpha(0).start();

                // Scale button Login smaller

                _button_login_lp.width=Math.round(200);
                _layoutLogin.setLayoutParams(_button_login_lp);
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });



        _btnAnonymousLogin = findViewById(R.id.btnAnonymousLogin);

        //region new Login page
        _pBar = findViewById(R.id.mainProgressBar);
        _txtLogin = findViewById(R.id.button_label);

        dm=getResources().getDisplayMetrics();
        _layoutLogin = findViewById(R.id.button_login);
        _layoutLogin.setTag(0);
        _button_login_lp = (RelativeLayout.LayoutParams) _layoutLogin.getLayoutParams();
        _old_button_login_lp = _button_login_lp;
        _pBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        _valueAnimator=new ValueAnimator();
        _valueAnimator.setDuration(1500);
        _valueAnimator.setInterpolator(new DecelerateInterpolator());
        _valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator p1) {
                RelativeLayout.LayoutParams button_login_lp= (RelativeLayout.LayoutParams) _layoutLogin.getLayoutParams();
                button_login_lp.width=Math.round(200);
                _layoutLogin.setLayoutParams(button_login_lp);
            }
        });

        _layoutLogin.animate().translationX(dm.widthPixels+_layoutLogin.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        _layoutLogin.animate().translationX(0).setStartDelay(200).setDuration(300).setInterpolator(new OvershootInterpolator()).start();
        //endregion
    }

    private void startMainActivity() {
        if((int)_layoutLogin.getTag()==1)
        {
            return;
        }
        else if((int)_layoutLogin.getTag()==2)
        {
            _layoutLogin.animate().x(dm.widthPixels/2).y(dm.heightPixels/2).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN)).setListener(null).setDuration(1000).setStartDelay(0).start();
            _layoutLogin.animate().setStartDelay(600).setDuration(1000).scaleX(40).scaleY(40).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN_OUT)).start();
        }
        _layoutLogin.setTag(1);
        _valueAnimator.setFloatValues(_layoutLogin.getMeasuredWidth(), _layoutLogin.getMeasuredHeight());
        _valueAnimator.start();
        _pBar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
        _txtLogin.animate().setStartDelay(100).setDuration(500).alpha(0).start();
        _layoutLogin.animate().setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(4000).setDuration(1000).scaleX(30).scaleY(30).setListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator p1)
            {
                _pBar.animate().setStartDelay(0).setDuration(0).alpha(0).start();
                _btnAnonymousLogin.setAlpha(0);
                _twitterFirebaseLoginButton.setAlpha(0);
                _facebookFirebaseLoginButton.setAlpha(0);
            }

            @Override
            public void onAnimationEnd(Animator p1) {
                try
                {
                    // getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, frag_dashboard).disallowAddToBackStack().commitAllowingStateLoss();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                catch(Exception e){}

                _layoutLogin.animate().setStartDelay(0).alpha(1).setDuration(1000).scaleX(1).scaleY(1).setListener(new Animator.AnimatorListener(){

                    @Override
                    public void onAnimationStart(Animator p1) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onAnimationEnd(Animator p1) {
                        _txtLogin.animate().alpha(1);
                        _txtLogin.setText(R.string.login);
                        _old_button_login_lp.width = -1;
                        _layoutLogin.setLayoutParams(_old_button_login_lp);
                    }

                    @Override
                    public void onAnimationCancel(Animator p1) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onAnimationRepeat(Animator p1) {
                        // TODO: Implement this method
                    }
                }).start();
            }

            @Override
            public void onAnimationCancel(Animator p1) {
                // TODO: Implement this method
            }

            @Override
            public void onAnimationRepeat(Animator p1) {
                // TODO: Implement this method
            }
        }).start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.addObserver(this);
        _btnAnonymousLogin.setAlpha(1);
        _twitterFirebaseLoginButton.setAlpha(1);
        _facebookFirebaseLoginButton.setAlpha(1);
        _layoutLogin.setTag(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.removeObserver(this);
    }

    @Override
    public void onBackPressed() {
        if(!_isPressedBack)
        {
            Toast.makeText(this,"Press Back again to EXIT",Toast.LENGTH_LONG).show();
            _isPressedBack = true;
        }
        else
        {
            super.onBackPressed();
        }

        new CountDownTimer(3000,1000)
        {
            @Override
            public void onTick(long l)
            { }

            @Override
            public void onFinish()
            {
                _isPressedBack = false;
            }
        }.start();
    }

    @Override
    public void onObserve(int event, Object content) {
        if(event == MyUtils.SHOW_TOAST)
        {
            Toast.makeText(this,content.toString(),Toast.LENGTH_SHORT).show();
        }
        else if(event == MyUtils.OPEN_ACTIVITY)
        {
            startMainActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
        _twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK)
            {
                addControls();
            }
            else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

}
