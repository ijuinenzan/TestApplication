package com.example.ijuin.testapplication.views;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ijuin.testapplication.BR;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.RowChatAdapterBinding;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.utils.MapBuilder;
import com.example.ijuin.testapplication.utils.MarkerBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BindingHolder> {


    private ArrayList<MessageItemModel> chatList;
    private Context _context;
    private View _view;

    public ChatAdapter(Context context, ArrayList<MessageItemModel> chatList) {
        this.chatList =chatList;
        this._context =context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _view = LayoutInflater.from(_context).inflate(R.layout.row_chat_adapter, parent, false);
        RowChatAdapterBinding binding= DataBindingUtil.bind(_view);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, final int position) {
        holder.getBinding().setVariable(BR.chatMessage, chatList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }



    class BindingHolder extends RecyclerView.ViewHolder {

        private RowChatAdapterBinding binding;

        BindingHolder(RowChatAdapterBinding binding) {
            super(binding.getRoot());
            setBinding(binding);
        }

        public void setBinding(RowChatAdapterBinding binding) {
            this.binding = binding;
        }

        public RowChatAdapterBinding getBinding() {
            return binding;
        }
    }

    // ================================================================================
    // ================================= MEDIA ========================================
    // ================================================================================


    private void onCreate()
    {
        _startTimeAudio = (TextView) _view.findViewById(R.id.txt_current_time);
        _endTimeAudio = (TextView) _view.findViewById(R.id.txt_end_time);
        _btnPlayAudio = (Button) _view.findViewById(R.id.btn_play_pause_audio);
        _seekbar = (SeekBar) _view.findViewById(R.id.seekbar_audio);

        _player = new MediaPlayer();

        _btnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPlayAudio();
            }
        });


        _seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int seekValue = _seekbar.getProgress();
                float currentTimeFloat = seekValue/100;
                int currentTimeInt = (int)(currentTimeFloat);
                int timeInMinute = currentTimeInt/60;
                if (timeInMinute == 0)
                {
                    if(seekValue == 0)
                    {
                        _startTimeAudio.setText("0:00");
                    }
                    else if (currentTimeInt < 10)
                    {
                        _startTimeAudio.setText("0:0"+currentTimeInt);
                    }
                    else if (currentTimeInt >= 10 && currentTimeInt < 60)
                    {
                        _startTimeAudio.setText("0:"+currentTimeInt);
                    }
                }
                else
                {
                    if ((currentTimeInt - timeInMinute*60) < 10)
                    {
                        _startTimeAudio.setText(timeInMinute + ":0" + (currentTimeInt - timeInMinute * 60));
                    }
                    else if ((currentTimeInt - timeInMinute*60) >= 10 && (currentTimeInt - timeInMinute*60) < 60)
                    {
                        _startTimeAudio.setText(timeInMinute + ":" + (currentTimeInt - timeInMinute * 60));
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        _seekbar.setProgress(90);
    }


    // region VIDEO
    private VideoView _videoView;
    public void playVideo()
    {

    }
    // endregion



    // region AUDIO
    private MediaPlayer _player;
    private TextView _startTimeAudio;
    private TextView _endTimeAudio;
    private Button _btnPlayAudio;
    private SeekBar _seekbar;
    private boolean _isPlay = false;  // check audio is played or not


    private String createFile(String fileDirName) {
        ContextWrapper cw = new ContextWrapper(_context);

        File directory = cw.getDir(fileDirName, Context.MODE_PRIVATE);
        if (!directory.exists())
        {
            // the directory was created
            if (directory.mkdir()) {
                //Toast.makeText(this, "Created directory", Toast.LENGTH_LONG).show();
            }
        }
        return directory.getAbsolutePath();
    }

    public void startPlayback() throws Exception
    {
        if(_player!=null)
        {
            _player.stop();
            _player.release();
        }
        _player = new MediaPlayer();
        String filepath = createFile("audio");
        _player.setDataSource(filepath + "/audiorecordtest.3gp");
        _player.prepare();
        _player.start();
        setEndTimeAudio(_player.getDuration());
    }


    public void setEndTimeAudio(int timeInMilliSeconds)
    {
        int timeInSeconds = timeInMilliSeconds/1000;
        int timeInMinute = timeInSeconds/60;

        if (timeInSeconds < 10)
        {
            _endTimeAudio.setText(String.valueOf(timeInMinute) +":0" + String.valueOf(timeInSeconds-timeInMinute*60));
        }
        else
        {
            _endTimeAudio.setText(String.valueOf(timeInMinute) +":" + String.valueOf(timeInSeconds-timeInMinute*60));
        }
    }

    public void stopPlayback()
    {
        _player.stop();
        _player.release();
    }

    public void pauseAndResume()
    {
        if(_isPlay)
        {
            _player.pause();
        }
        else
        {
            _player.reset();
            // can not resume ???
        }
    }

    private void onClickPlayAudio()
    {
        if (_isPlay)
        {
            pauseAndResume();
            _btnPlayAudio.setBackgroundResource(R.drawable.ic_play_48dp);
            _isPlay = false;
        }
        else
        {
            try {
                startPlayback();
            } catch (Exception e) {
                e.printStackTrace();
            }
            _btnPlayAudio.setBackgroundResource(R.drawable.ic_pause_48dp);
            _isPlay = true;
        }
    }
    // endregion








    // endregion
    // ================================================================================
    // ================================================================================
    // ================================================================================
}

