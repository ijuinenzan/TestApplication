package com.example.ijuin.testapplication.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.utils.MyUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BindingHolder> {
    private ArrayList<MessageItemModel> _chatList;

    public ChatAdapter(ArrayList<MessageItemModel> chatList)
    {
        this._chatList =chatList;
    }
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, final int position) {
        holder.bind(_chatList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        String messageType = _chatList.get(position).getType();

        if(messageType.equals(MyUtils.TEXT_TYPE))
        {
            return R.layout.text_chat_adapter;
        }
        else if(messageType.equals(MyUtils.LOCATION_TYPE))
        {
            return R.layout.location_chat_adapter;
        }
        else if(messageType.equals(MyUtils.AUDIO_TYPE))
        {
            return R.layout.audio_chat_adapter;
        }
        else if(messageType.equals(MyUtils.IMAGE_TYPE))
        {
            return R.layout.image_chat_adapter;
        }
        else if(messageType.equals(MyUtils.VIDEO_TYPE))
        {
            return R.layout.video_chat_adapter;
        }
        else if(messageType.equals(MyUtils.INFO_REQUEST_TYPE))
        {
            return R.layout.info_request_chat_adapter;
        }
        else if(messageType.equals(MyUtils.INFO_ACCEPT_TYPE))
        {
            return R.layout.info_accept_chat_adapter;
        }
        else if(messageType.equals(MyUtils.GREET_TYPE))
        {
            return R.layout.greet_chat_adapter;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return _chatList.size();
    }

    public void setChatList(final ArrayList<MessageItemModel> chatList){
        _chatList = chatList;

        Collections.sort(_chatList, new Comparator<MessageItemModel>() {
            @Override
            public int compare(MessageItemModel message1, MessageItemModel message2) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
                DateTime dt1 = formatter.parseDateTime(message1.getTimeStamp());
                DateTime dt2 = formatter.parseDateTime(message2.getTimeStamp());
                return dt1.compareTo(dt2);
            }
        });
        notifyDataSetChanged();
    }

    class BindingHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding _binding;

        BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            _binding = binding;
        }

        public void bind(MessageItemModel message)
        {
            _binding.setVariable(BR.chatMessage, message);
            _binding.executePendingBindings();
        }
    }
}

