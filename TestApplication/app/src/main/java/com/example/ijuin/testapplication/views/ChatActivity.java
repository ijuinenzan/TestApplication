package com.example.ijuin.testapplication.views;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityChatBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.viewmodels.ChatViewModel;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatActivity extends AppCompatActivity implements Observer<ArrayList<MessageItemModel>> , View.OnClickListener
{
    //region DECLARE VARIABLE
    private ActivityChatBinding mBinding;
    private ChatViewModel mViewModel;

    // Floating Action Button
    FloatingActionButton _fabPlus, _fabLocation, _fabCamera, _fabGallery;
    boolean _isOpen = false;

    // Animation
    private Animation _animOpen, _animClose, _animClockwise, _animAntiClockwise;

    // Emojicon EditText
    private EditText _edtEmoji;

    // Button
    private Button _btnVideo;
    private Button _btnRecorder;

    private VideoView _videoView;
    private boolean _isRecordering = false;  // check audio is recordering or not
    private boolean _isPlay = false;  // check audio is played or not


    // Audio
    private SeekBar _seekbar;
    private TextView _startTimeAudio;
    private TextView _endTimeAudio;
    private Button _btnPlayAudio;
    private Button _btnStartStopRecorder;
    private MediaRecorder _recorder;
    private MediaPlayer _player;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mViewModel = new ChatViewModel();
        mBinding.setViewModel(mViewModel);
        mBinding.setActivity(this);
        mViewModel.addObserver(this);

        _fabPlus = (FloatingActionButton) findViewById(R.id.fab_plus);
        _fabLocation = (FloatingActionButton) findViewById(R.id.fab_location);
        _fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        _fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);

        _animOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_open);
        _animClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_close);
        _animClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_clockwise);
        _animAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_anticlockwise);

        _videoView = (VideoView) findViewById(R.id.videoview);
        _btnRecorder = (Button) findViewById(R.id.btn_recorder);
        _btnVideo = (Button) findViewById(R.id.btn_video);
        _btnStartStopRecorder = (Button) findViewById(R.id.btn_start_stop_recorder);
        _edtEmoji = (EditText) findViewById(R.id.editEmojicon);
        _seekbar = (SeekBar) findViewById(R.id.seekbar_audio);
        _startTimeAudio = (TextView) findViewById(R.id.txt_current_time);
        _endTimeAudio = (TextView) findViewById(R.id.txt_end_time);
        _btnPlayAudio = (Button) findViewById(R.id.btn_play_pause_audio);



        _fabPlus.setOnClickListener(this);
        _fabLocation.setOnClickListener(this);
        _fabCamera.setOnClickListener(this);
        _fabGallery.setOnClickListener(this);
        _btnVideo.setOnClickListener(this);
        _btnRecorder.setOnClickListener(this);
        _btnStartStopRecorder.setOnClickListener(this);
        _btnPlayAudio.setOnClickListener(this);


        _player = new MediaPlayer();

        _seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int seekValue = _seekbar.getProgress();
                float currentTimeFloat = (seekValue*702)/100;
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

        _edtEmoji.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    _btnRecorder.setVisibility(View.GONE);
                    _btnVideo.setVisibility(View.GONE);
                    _btnStartStopRecorder.setVisibility(View.GONE);
                    _isRecordering = true;
                }
                else
                {
                    _btnRecorder.setVisibility(View.VISIBLE);
                    _btnVideo.setVisibility(View.VISIBLE);
                }

            }
        });



//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
//        mViewModel= new ChatViewModel(getIntent().getStringExtra(MyUtils.EXTRA_ROOM_NAME));
//        mBinding.setViewModel(mViewModel);
//        mBinding.setActivity(this);
//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mViewModel.addObserver(this);
//        mViewModel.setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.removeObserver(this);
        mViewModel.onDestroy();
    }



    public void playVideo()
    {
        try {
            int id = R.raw.khanh;
            _videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));

        } catch (Exception e) {
            e.printStackTrace();
        }

        _videoView.requestFocus();
        _videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediacontroller = new MediaController(ChatActivity.this);
                        mediacontroller.setAnchorView(_videoView);
                        _videoView.setMediaController(mediacontroller);
                    }
                });
            }
        });

        _videoView.start();
    }

    public void startRecorder() throws Exception
    {
        if(_recorder!=null)
        {
            _recorder.release();
        }

        _recorder = new MediaRecorder();

        // Initial
        _recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        // Intialized
        _recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        // DataSourceConfigured
        _recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        String filePath = writeAudioToFile("audio", "temp");
        _recorder.setOutputFile(filePath);
        _recorder.prepare();

        // Prepared
        _recorder.start();   // Recording is now started

    }

    public void stopRecorder()
    {
        _recorder.stop();
        _recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        _recorder.release(); // Now the object cannot be reused
    }

    public void startPlayback() throws Exception
    {
        if(_player!=null)
        {
            _player.stop();
            _player.release();
        }
        _player = new MediaPlayer();
        String filepath = writeAudioToFile("audio", "temp");
        _player.setDataSource(filepath);
        _player.prepare();
        _player.start();
    }

    public void stopPlayback()
    {
        _player.stop();
        _player.release();
    }

    private String writeAudioToFile(String fileDirName, String fileName) {
        ContextWrapper cw = new ContextWrapper(this);

        File directory = cw.getDir(fileDirName, Context.MODE_PRIVATE);

        File mypath = new File(directory,fileName + ".3gpp");

        return directory.getAbsolutePath() + mypath;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i == _fabPlus.getId())
        {
            _edtEmoji.clearFocus();
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
        else if (i == _btnVideo.getId())
        {
            playVideo();
        }
        else if (i == _btnRecorder.getId())
        {
            _btnStartStopRecorder.setVisibility(View.VISIBLE);
        }
        else if (i == _btnStartStopRecorder.getId())
        {
            if (ActivityCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},10);

            }
            else
            {
                if( _isRecordering)
                {
                    stopRecorder();
                    _btnStartStopRecorder.setBackgroundResource(R.drawable.ic_start_record_48dp);
                    _isRecordering = false;
                }
                else
                {
                    try
                    {
                        startRecorder();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    _btnStartStopRecorder.setBackgroundResource(R.drawable.ic_stop_48dp);
                    _isRecordering = true;
                }
            }

        }
        else if (i == _btnPlayAudio.getId())
        {
            if (_isPlay)
            {
                stopPlayback();
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
    }

    @Override
    public void onObserve(int event, ArrayList<MessageItemModel> eventMessage) {

//        ChatAdapter chatAdapter=new ChatAdapter(this,eventMessage);
//        mBinding.recyclerView.setAdapter(chatAdapter);
//        mBinding.recyclerView.scrollToPosition(eventMessage.size()-1);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        _btnRecorder.setVisibility(View.VISIBLE);
        _btnVideo.setVisibility(View.VISIBLE);
    }
}
