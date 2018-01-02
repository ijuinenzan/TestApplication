package com.example.ijuin.testapplication.utils;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.views.ChatAdapter;
import com.github.foolish314159.mediaplayerview.MediaPlayerView;

import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by ijuin on 11/12/2017.
 */

public class MyUtils
{
    public static String MESSAGE_AUTHENTICATION_FAILED = "Firebase authentication failed, please check your internet connection";

    public static String TEXT_TYPE = "TEXT";
    public static String VIDEO_TYPE = "VIDEO";
    public static String AUDIO_TYPE = "AUDIO";
    public static String LOCATION_TYPE = "LOCATION";
    public static String IMAGE_TYPE = "IMAGE";

    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int UPDATE_MESSAGES=3;
    public static final int SELECT_PICTURE = 4;
    public static final int LOG_OUT = 5;
    public static final int CHAT_ROOM_FOUND = 6;
    public static final int SELECT_FILE_FROM_GALLERY = 7;
    public static final int REQUEST_CAMERA = 8;
    public static final int TAKE_PICTURE = 9;
    public static final int EXIT_ROOM=10;

    public static Boolean MALE = true;

    public static Boolean FEMALE = false;

    @BindingAdapter({"app:chat_messages"})
    public static void updateMessages(RecyclerView recyclerView, ArrayList<MessageItemModel> messages)
    {
        ChatAdapter chatAdapter = ((ChatAdapter)recyclerView.getAdapter());
        chatAdapter.setChatList(messages);
        recyclerView.scrollToPosition(messages.size()-1);
    }

    @BindingAdapter({"app:image_url"})
    public static void loadImage(ImageView imageView,String url)
    {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.skipMemoryCacheOf(true)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imageView);
    }

    @BindingAdapter({"app:audio_url"})
    public static void loadAudio(MediaPlayerView player, String url)
    {
        try
        {
            player.setupPlayer(url);
        }
        catch(Exception e)
        {

        }
    }

    @BindingAdapter({"app:video_url"})
    public static void loadVideo(final VideoView videoView, String url)
    {
        videoView.setVideoURI(Uri.parse(url));
        //videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediacontroller = new MediaController(videoView.getContext());
                        mediacontroller.setAnchorView(videoView);
                        videoView.setMediaController(mediacontroller);
                    }
                });
            }
        });

        videoView.start();
    }

    @BindingAdapter({"app:location"})
    public static void loadLocation(final ImageButton imageButton, String location)
    {
        // get from fb
        final double latitude = Double.parseDouble(location.split(" ")[0]);
        final double longitude = Double.parseDouble(location.split(" ")[1]);
        imageButton.post(new Runnable() {
            @Override
            public void run() {
                int height = imageButton.getMeasuredHeight();
                int width = imageButton.getMeasuredWidth();

                MapBuilder mapBuilder = new MapBuilder().center(latitude, longitude).dimensions(width, height).zoom(25);

                mapBuilder.setKey("");

                mapBuilder.addMarker(new MarkerBuilder().position(latitude, longitude));

                String url = mapBuilder.build();

                GetImageAsyncTask asyncTask = new GetImageAsyncTask(imageButton);
                asyncTask.execute(url);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f (%s)", latitude, longitude, latitude, longitude, "Mark");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                imageButton.getContext().startActivity(intent);
            }
        });
    }




}

class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    ImageButton _imageButton;

    GetImageAsyncTask(ImageButton imageButton)
    {
        _imageButton = imageButton;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL newUrl = new URL(params[0]);
            Bitmap bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);;
        _imageButton.setImageBitmap(bitmap);
    }
}
