package com.example.ijuin.testapplication.views;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.RowChatAdapterBinding;
import com.example.ijuin.testapplication.models.MessageItemModel;

import java.util.ArrayList;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BindingHolder> {
    private ArrayList<MessageItemModel> chatList;

    public ChatAdapter(Context context, ArrayList<MessageItemModel> chatList) {
        this.chatList =chatList;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowChatAdapterBinding rowChatAdapterBinding = RowChatAdapterBinding.inflate(layoutInflater, parent, false);
        return new BindingHolder(rowChatAdapterBinding);
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, final int position) {
        holder.bind(chatList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class BindingHolder extends RecyclerView.ViewHolder {
        private RowChatAdapterBinding _binding;

        BindingHolder(RowChatAdapterBinding binding) {
            super(binding.getRoot());
            _binding = binding;
        }

        public void bind(MessageItemModel message)
        {
            _binding.setVariable(BR.chatMessage, message);
            _binding.setChatMessage(message);
            _binding.executePendingBindings();
        }
    }
}

