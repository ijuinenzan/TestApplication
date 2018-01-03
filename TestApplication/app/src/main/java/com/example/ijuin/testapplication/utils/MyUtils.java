package com.example.ijuin.testapplication.utils;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.models.MessageItemModel;
import com.example.ijuin.testapplication.views.ChatActivity;
import com.example.ijuin.testapplication.views.ChatAdapter;
import com.github.foolish314159.mediaplayerview.MediaPlayerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by ijuin on 11/12/2017.
 */

public class MyUtils
{
    public static final String MESSAGE_AUTHENTICATION_FAILED = "Firebase authentication failed, please check your internet connection";

    public static final String GREET_TYPE = "GREET";
    public static final String TEXT_TYPE = "TEXT";
    public static final String VIDEO_TYPE = "VIDEO";
    public static final String AUDIO_TYPE = "AUDIO";
    public static final String LOCATION_TYPE = "LOCATION";
    public static final String IMAGE_TYPE = "IMAGE";
    public static final String SETTINGS = "SETTINGS";
    public static final String INFO_REQUEST_TYPE = "INFO-REQUEST";
    public static final String INFO_ACCEPT_TYPE = "INFO-ACCEPT";

    public static final int SELECT_FILE = 410;
    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int SELECT_PICTURE = 4;
    public static final int LOG_OUT = 5;
    public static final int CHAT_ROOM_FOUND = 6;
    public static final int SELECT_FILE_FROM_GALLERY = 7;
    public static final int REQUEST_CAMERA = 8;
    public static final int TAKE_PICTURE = 9;
    public static final int EXIT_ROOM=10;

    public static final Boolean MALE = true;

    public static final Boolean FEMALE = false;

    @BindingAdapter({"app:chat_messages"})
    public static void updateMessages(RecyclerView recyclerView, ArrayList<MessageItemModel> messages)
    {
        ChatAdapter chatAdapter = ((ChatAdapter)recyclerView.getAdapter());
        chatAdapter.setChatList(messages);
        recyclerView.scrollToPosition(messages.size()-1);
    }


    @BindingAdapter({"app:profile_image_url"})
    public static void loadProfileImage(ImageView imageView,String url)
    {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.skipMemoryCacheOf(true)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imageView);
    }

    @BindingAdapter({"app:image_url"})
    public static void loadImage(ImageView imageView,final String url)
    {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.skipMemoryCacheOf(true)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imageView);
    }

    @BindingAdapter({"app:audio_url"})
    public static void loadAudio(MediaPlayerView player, String url)
    {
        try
        {
            player.setupPlayer(url);
            player.setUrl(url);
        }
        catch(Exception e)
        {

        }
    }


    @BindingAdapter({"app:video_url"})
    public static void loadVideo(final JZVideoPlayerStandard jzVideoPlayerStandard, String url)
    {
        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
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
    private static final ArrayList<String> selectedFields = new ArrayList<>();
    public static void acceptInfo(final View v)
    {
        new MaterialDialog.Builder(v.getContext())
                .iconRes(R.drawable.ic_launcher)
                .limitIconToDefaultSize()
                .title("title")
                .items(R.array.list_info)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        selectedFields.clear();
                        String temp = "";
                        for(int i = 0; i <= which.length - 1; i++) {
                            switch (which[i]) {
                                case 0: {
                                    temp = "Name:";
                                    break;
                                }
                                case 1: {
                                    temp = "Yearborn:";
                                    break;
                                }
                                case 2: {
                                    temp = "Gender:";
                                    break;
                                }
                                case 3: {
                                    temp = "Phone:";
                                    break;
                                }
                                case 4: {
                                    temp = "Address:";
                                    break;
                                }
                                case 5: {
                                    temp = "Company:";
                                    break;
                                }
                                case 6: {
                                    temp = "City:";
                                    break;
                                }
                                case 7: {
                                    temp = "Country:";
                                    break;
                                }
                                case 8: {
                                    temp = "Weight:";
                                    break;
                                }
                                case 9: {
                                    temp = "Height:";
                                    break;
                                }
                                case 10: {
                                    temp = "Link Facebook:";
                                    break;
                                }
                                case 11: {
                                    temp = "Link Twitter:";
                                    break;
                                }
                                default:
                                    break;

                            }
                            selectedFields.add(temp);
                        }

                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((ChatActivity)v.getContext()).getViewModel().sendInfoAccept(selectedFields);
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("choose")
                .show();
    }


}

class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private final ImageButton _imageButton;

    GetImageAsyncTask(ImageButton imageButton)
    {
        _imageButton = imageButton;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL newUrl = new URL(params[0]);
            return BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        _imageButton.setImageBitmap(bitmap);
    }
}
