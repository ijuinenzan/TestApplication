package com.example.ijuin.testapplication.views;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityChatBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.viewmodels.ChatViewModel;

import java.util.ArrayList;


/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatActivity extends AppCompatActivity implements Observer<ArrayList<MessageItemModel>> , View.OnClickListener
{
    private ActivityChatBinding mBinding;
    private ChatViewModel mViewModel;

    // Floating Action Button
    FloatingActionButton _fabPlus, _fabLocation, _fabCamera, _fabGallery;
    boolean _isOpen = false;

    // Animation
    Animation _animOpen, _animClose, _animClockwise, _animAntiClockwise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        _fabPlus = (FloatingActionButton) findViewById(R.id.fab_plus);
        _fabLocation = (FloatingActionButton) findViewById(R.id.fab_location);
        _fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        _fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);

        _animOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_open);
        _animClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_close);
        _animClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_clockwise);
        _animAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_anticlockwise);

        _fabPlus.setOnClickListener(this);
        _fabLocation.setOnClickListener(this);
        _fabCamera.setOnClickListener(this);
        _fabGallery.setOnClickListener(this);


//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
//        mViewModel= new ChatViewModel(getIntent().getStringExtra(MyUtils.EXTRA_ROOM_NAME));
//        mBinding.setViewModel(mViewModel);
//        mBinding.setActivity(this);
//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mViewModel.addObserver(this);
//        mViewModel.setListener();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i == _fabPlus.getId())
        {
            if (_isOpen)
            {
                _fabPlus.startAnimation(_animAntiClockwise);

                _fabLocation.startAnimation(_animClose);
                _fabLocation.setClickable(false);
                _fabLocation.setVisibility(View.INVISIBLE);

                _fabCamera.startAnimation(_animClose);
                _fabCamera.setClickable(false);
                _fabCamera.setVisibility(View.INVISIBLE);

                _fabGallery.startAnimation(_animClose);
                _fabGallery.setClickable(false);
                _fabGallery.setVisibility(View.INVISIBLE);

                _isOpen = false;
            }
            else
            {
                _fabPlus.startAnimation(_animClockwise);

                _fabLocation.startAnimation(_animOpen);
                _fabLocation.setClickable(true);
                _fabLocation.setVisibility(View.VISIBLE);

                _fabCamera.startAnimation(_animOpen);
                _fabCamera.setClickable(true);
                _fabCamera.setVisibility(View.VISIBLE);

                _fabGallery.startAnimation(_animOpen);
                _fabGallery.setClickable(true);
                _fabGallery.setVisibility(View.VISIBLE);

                _isOpen = true;
            }
        }
        else if (i == _fabLocation.getId())
        {
            Toast.makeText(this,"LOCATION",Toast.LENGTH_LONG).show();
            //TODO: Click Location
        }
        else if (i == _fabCamera.getId())
        {
            Toast.makeText(this,"CAMERA",Toast.LENGTH_LONG).show();
            //TODO: Click Camera
        }
        else if (i == _fabGallery.getId())
        {
            Toast.makeText(this,"GALLERY",Toast.LENGTH_LONG).show();
            //TODO: Click Gallery
        }
    }

    public void sendMessage() {
        Toast.makeText(this,"SENT",Toast.LENGTH_LONG).show();
//        mViewModel.sendMessageToFirebase(mBinding.edittextChatMessage.getText().toString());
//        mBinding.edittextChatMessage.getText().clear();
    }

    @Override
    public void onObserve(int event, ArrayList<MessageItemModel> eventMessage) {

//        ChatAdapter chatAdapter=new ChatAdapter(this,eventMessage);
//        mBinding.recyclerView.setAdapter(chatAdapter);
//        mBinding.recyclerView.scrollToPosition(eventMessage.size()-1);
    }

    @Override
    protected void onDestroy() {
       super.onDestroy();
//        mViewModel.removeObserver(this);
//        mViewModel.onDestory();
    }



}
