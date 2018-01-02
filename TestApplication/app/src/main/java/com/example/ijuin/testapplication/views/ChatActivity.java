package com.example.ijuin.testapplication.views;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ActivityChatBinding;
import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.viewmodels.ChatViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import java.io.File;
import java.util.ArrayList;

import javax.security.auth.callback.CallbackHandler;

import static com.example.ijuin.testapplication.utils.MyUtils.EXIT_ROOM;


/**
 * Created by ijuin on 11/12/2017.
 */

public class ChatActivity extends AppCompatActivity implements Observer<ArrayList<MessageItemModel>>
{
    //region DECLARE VARIABLE
    private ActivityChatBinding mBinding;
    private ChatViewModel mViewModel;
    private final int PLACE_PICKER_REQUEST = 2000;
    private static final int REQUEST_LOCATION = 1997;
    private final int SELECT_FILE = 1234;
    private final int REQUEST_CAMERA = 2468;
    private final int REQUEST_VIDEO = 1357;


    // ====== Floating Action Button =============================================================
    FloatingActionButton _fabPlus, _fabLocation, _fabCamera, _fabGallery, _fabInfo;
    boolean _isOpen = false;
    // ===========================================================================================

    // ====== Animation ==========================================================================
    private Animation _animOpen, _animClose, _animClockwise, _animAntiClockwise;
    // ===========================================================================================

    // ====== Emojicon EditText ==================================================================
    private EditText _edtEmoji;
    // ===========================================================================================

    // ====== Button =============================================================================
    private Button _btnVideo;
    private Button _btnRecorder;
    // ===========================================================================================


    private boolean _isRecordering = false;  // check audio is recordering or not


    // ====== Audio ==============================================================================
    private Button _btnStartStopRecorder;
    private MediaRecorder _recorder;
    private File _audioRecordFile;
    // ===========================================================================================

    // ====== Location ===========================================================================
    private LocationManager _locationManager;
    // ===========================================================================================

    MaterialDialog md;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mViewModel = new ChatViewModel();
        mBinding.setViewModel(mViewModel);
        mBinding.setActivity(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(new ChatAdapter(this,  mViewModel.getMessages()));
        mViewModel.addObserver(this);

        _fabPlus = (FloatingActionButton) findViewById(R.id.fab_plus);
        _fabInfo = (FloatingActionButton) findViewById(R.id.fab_info);
        _fabLocation = (FloatingActionButton) findViewById(R.id.fab_location);
        _fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        _fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);

        _animOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_open);
        _animClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fab_close);
        _animClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_clockwise);
        _animAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate_anticlockwise);


        _btnRecorder = (Button) findViewById(R.id.btn_recorder);
        _btnVideo = (Button) findViewById(R.id.btn_video);
        _btnStartStopRecorder = (Button) findViewById(R.id.btn_start_stop_recorder);
        _edtEmoji = (EditText) findViewById(R.id.editEmojicon);

        _audioRecordFile = new File((new ContextWrapper(this)).getDir("audio", Context.MODE_PRIVATE).getAbsolutePath().concat("record.3gp"));


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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _recorder.stop();
        _recorder.release();
        mViewModel.removeObserver(this);
        mViewModel.onDestroy();
    }





    public void startRecorder() throws Exception
    {
        if(_recorder==null)
        {
            _recorder = new MediaRecorder();
        }

        // Initial
        _recorder.setAudioSource(MediaRecorder.AudioSource.MIC);


        // Intialized
        _recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        // DataSourceConfigured
        _recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        String filePath = createFile("audio");

        _recorder.setOutputFile(_audioRecordFile.getPath());

        _recorder.prepare();

        // Prepared
        _recorder.start();   // Recording is now started

    }

    public void stopRecorder()
    {
        _recorder.stop();
        _recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        mViewModel.sendAudio(Uri.fromFile(_audioRecordFile));
    }



    private String createFile(String fileDirName) {
        ContextWrapper cw = new ContextWrapper(this);

        File directory = cw.getDir(fileDirName, Context.MODE_PRIVATE);
        if (!directory.exists())
        {
            // the directory was created
            if (directory.mkdir()) {
                Toast.makeText(this, "Created directory", Toast.LENGTH_LONG).show();
            }
        }
        return directory.getAbsolutePath();
    }



    public void onClickPlus()
    {
        _edtEmoji.clearFocus();
        if (_isOpen)
        {
            _fabPlus.startAnimation(_animAntiClockwise);

            _fabInfo.startAnimation(_animClose);
            _fabInfo.setClickable(false);
            _fabInfo.setVisibility(View.INVISIBLE);

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

            _fabInfo.startAnimation(_animOpen);
            _fabInfo.setClickable(true);
            _fabInfo.setVisibility(View.VISIBLE);

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

    public void onClickStartStopRecorder()
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



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(this, data);
            }
        }
        else if (requestCode == REQUEST_CAMERA)
        {
            if (resultCode == RESULT_OK)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mViewModel.sendImageBitmap(bitmap);
            }
        }
        else if (requestCode == SELECT_FILE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri selectedImageUri = data.getData();
                mViewModel.sendImageUri(selectedImageUri);
            }
        }
        else if (requestCode == REQUEST_VIDEO)
        {
            if (resultCode == RESULT_OK)
            {
                Uri videoUri = data.getData();
                mViewModel.sendVideo(videoUri);
            }
        }
    }

    private void alertTurnOnLocation()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please TURN ON your location !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void alertExit()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        mViewModel.exitRoom();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void alertInfo()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to know your partner's infomation?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        mViewModel.sendInfoRequest();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void sendLocation()
    {
        _locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if(!_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            // failed
            alertTurnOnLocation();
        }
        else
        {
            // success
            if(ActivityCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
            else {
                Location location = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location location1 = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null)
                {
                    mViewModel.sendLocation(location.getLatitude(),location.getLongitude());
                }
                else if (location1 != null) {
                }
            }
        }

    }

    public void sendVideo()
    {
        ChatActivity.this.startActivityForResult(new Intent(MediaStore.ACTION_VIDEO_CAPTURE), REQUEST_VIDEO);
    }

    public void getImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        ChatActivity.this.startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void getImageFromCamera()
    {
        ChatActivity.this.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CAMERA);
    }

    public void onClickRecorder()
    {
        _btnStartStopRecorder.setVisibility(View.VISIBLE);
    }

    public void onClickInfo()
    {
        alertInfo();
    }

    public void onClickAcceptInfo()
    {
        md = new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_launcher)
                .limitIconToDefaultSize()
                .title("title")
                .items(R.array.list_info)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected
                         * (or the newly unselected check box to be unchecked).
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        md.setSelectedIndices(which);
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("choose")
                .show();


        Integer[] a = md.getSelectedIndices();

        mViewModel.sendInfoAccept(md.getSelectedIndices());
    }

    @Override
    public void onObserve(int event, ArrayList<MessageItemModel> eventMessage) {

        if(event == EXIT_ROOM)
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed()
    {
    }


}
