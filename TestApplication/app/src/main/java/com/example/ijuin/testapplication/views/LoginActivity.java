package com.example.ijuin.testapplication.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
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
import com.example.ijuin.testapplication.models.UserModel;
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

public class LoginActivity extends AppCompatActivity implements Observer<Object>  {

    //region DECLARE VARIABLE
    private LoginViewModel mViewModel;
    private Button _facebookFirebaseLoginButton;
    private LoginButton _facebookLoginButon;
    private Button _twitterFirebaseLoginButton;
    private TwitterLoginButton _twitterLoginButton;
    private CallbackManager _callbackManager;
    private Button _btnAnonymousLogin;

    // Login
    ProgressBar _pBar;
    ImageView _imgView;
    TextView _txtLogin;
    private DisplayMetrics dm;
    RelativeLayout _layoutLogin;
    private ValueAnimator _valueAnimator;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);



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
                _pBar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                _txtLogin.animate().setStartDelay(100).setDuration(500).alpha(0).start();
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
                _pBar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                _txtLogin.animate().setStartDelay(100).setDuration(500).alpha(0).start();
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });



        _btnAnonymousLogin = (Button)  findViewById(R.id.btnAnonymousLogin);

        //region new Login page
        _pBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        _imgView = (ImageView) findViewById(R.id.button_icon);
        _txtLogin = (TextView) findViewById(R.id.button_label);

        dm=getResources().getDisplayMetrics();
        _layoutLogin = (RelativeLayout) findViewById(R.id.button_login);
        _layoutLogin.setTag(0);
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

    public void startMainActivity(UserModel user) {
        final UserModel userModel = user;
        if((int)_layoutLogin.getTag()==1)
        {
            return;
        }
        else if((int)_layoutLogin.getTag()==2)
        {
            _layoutLogin.animate().x(dm.widthPixels/2).y(dm.heightPixels/2).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN)).setListener(null).setDuration(1000).setStartDelay(0).start();
            _layoutLogin.animate().setStartDelay(600).setDuration(1000).scaleX(40).scaleY(40).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN_OUT)).start();
            _imgView.animate().alpha(0).rotation(90).setStartDelay(0).setDuration(800).start();
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
                    intent.putExtra("User", userModel);
                    startActivity(intent);
                }
                catch(Exception e){}

                _layoutLogin.animate().setStartDelay(0).alpha(1).setDuration(1000).scaleX(1).scaleY(1).x(dm.widthPixels-_layoutLogin.getMeasuredWidth()-100).y(dm.heightPixels-_layoutLogin.getMeasuredHeight()-100).setListener(new Animator.AnimatorListener(){

                    @Override
                    public void onAnimationStart(Animator p1) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onAnimationEnd(Animator p1) {
                        _imgView.animate().setDuration(0).setStartDelay(0).rotation(85).alpha(1).start();
                        _imgView.animate().setDuration(2000).setInterpolator(new BounceInterpolator()).setStartDelay(0).rotation(0).start();
                        _layoutLogin.setTag(2);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.removeObserver(this);
    }

    @Override
    public void onObserve(int event, Object content) {
        if(event == MyUtils.SHOW_TOAST)
        {
            Toast.makeText(this,content.toString(),Toast.LENGTH_SHORT).show();
        }
        else if(event == MyUtils.OPEN_ACTIVITY)
        {
            startMainActivity((UserModel)content);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
        _twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
