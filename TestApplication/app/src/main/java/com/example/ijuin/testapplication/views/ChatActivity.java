package com.example.ijuin.testapplication.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityChatBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.ChatViewModel;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatActivity extends AppCompatActivity implements Observer<ArrayList<MessageItemModel>>
{
    private ActivityChatBinding mBinding;
    private ChatViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mViewModel= new ChatViewModel(getIntent().getStringExtra(MyUtils.EXTRA_ROOM_NAME));
        mBinding.setViewModel(mViewModel);
        mBinding.setActivity(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewModel.addObserver(this);
        mViewModel.setListener();
    }

    public void sendMessage() {
        mViewModel.sendMessageToFirebase(mBinding.edittextChatMessage.getText().toString());
        mBinding.edittextChatMessage.getText().clear();
    }

    @Override
    public void onObserve(int event, ArrayList<MessageItemModel> eventMessage) {
        ChatAdapter chatAdapter=new ChatAdapter(this,eventMessage);
        mBinding.recyclerView.setAdapter(chatAdapter);
        mBinding.recyclerView.scrollToPosition(eventMessage.size()-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.removeObserver(this);
        mViewModel.onDestory();
    }

}
