package com.example.ijuin.testapplication.views;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.AboutUsFragmentBinding;
import com.example.ijuin.testapplication.databinding.ActivityMainBinding;
import com.example.ijuin.testapplication.databinding.ProfileFragmentBinding;

import com.example.ijuin.testapplication.databinding.SearchFragmentBinding;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.MainViewModel;
import com.example.ijuin.testapplication.viewmodels.ProfileViewModel;

import com.example.ijuin.testapplication.viewmodels.SearchViewModel;

import com.example.ijuin.testapplication.views.particle.ParticleView;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.IOException;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.example.ijuin.testapplication.utils.MyUtils.REQUEST_CAMERA;
import static com.example.ijuin.testapplication.utils.MyUtils.SELECT_FILE;
import static com.example.ijuin.testapplication.utils.MyUtils.SELECT_FILE_FROM_GALLERY;
import static com.example.ijuin.testapplication.utils.MyUtils.SETTINGS;

/**
 * Created by Khang Le on 11/21/2017.
 */

public class MainActivity extends AppCompatActivity implements Observer<String>
{

//region DECLARE VARIABLE
    private PagerAdapter _pagerAdapter;

    private MainViewModel _viewModel;

    private MediaPlayer _mediaPlayer;
//endregion

    private MainViewModel getViewModel()
    {
        return _viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        int color = settings.getInt("bg_color", android.R.color.white);


        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        _viewModel = new MainViewModel();
        binding.setViewModel(_viewModel);
        binding.setActivity(this);
        _viewModel.addObserver(this);
        _mediaPlayer = MediaPlayer.create(this,R.raw.anh_nang_cua_anh);
        _mediaPlayer.start();
        setBackground(color);
        addControls();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _viewModel.removeObserver(this);
        _viewModel.onDestroy();
    }

    private void setBackground(int color)
    {
        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("bg_color", color);
        editor.commit();
        _viewModel.setBackgroundColor(color);
    }

    private void back()
    {
        super.onBackPressed();
    }

    private void addControls()
    {
        _pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewPagerContainer);
        viewPager.setAdapter(_pagerAdapter) ;

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.findViewById(R.id.profileFragment);
            }
        };

        viewPager.setPageTransformer(true,transformer);

        PagerSlidingTabStrip customTab = findViewById(R.id.tabs);
        customTab.setViewPager(viewPager);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == REQUEST_CAMERA)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                for(int i = 0; i < _pagerAdapter.getCount(); ++i)
                {
                    if(_pagerAdapter.getItem(i) instanceof ProfileFragment)
                    {
                        ((ProfileFragment)_pagerAdapter.getItem(i)).mViewModel.uploadProfileImage(bitmap);
                    }
                }
            }
            else if (requestCode == SELECT_FILE_FROM_GALLERY)
            {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    for(int i = 0; i < _pagerAdapter.getCount(); ++i)
                    {
                        if(_pagerAdapter.getItem(i) instanceof ProfileFragment)
                        {
                            ((ProfileFragment)_pagerAdapter.getItem(i)).mViewModel.uploadProfileImage(bitmap);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_FILE)
            {
                Uri uri = data.getData();
                SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("image_uri", uri.toString());
                editor.commit();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
    }

    @Override
    public void onObserve(int event, final String chatRoom) {
        Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
        chatIntent.putExtra("RoomName", chatRoom);
        startActivity(chatIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _mediaPlayer.stop();
    }




    public static class SearchFragment extends Fragment {

        public SearchFragment() {        }

        private SearchViewModel mViewModel;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
           SearchFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.search_fragment, container, false);
            View view = binding.getRoot();

            mViewModel= new SearchViewModel();
            binding.setViewModel(mViewModel);

            final BubbleThumbRangeSeekbar rangeSeekbar = view.findViewById(R.id.rangeSeekbar);

            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue)
                {
                    mViewModel.setMinAge(minValue.intValue());
                    mViewModel.setMaxAge(maxValue.intValue());
                }
            });

            binding.setMainViewModel(((MainActivity)getActivity()).getViewModel());

            return view;
        }
    }





    public static class ProfileFragment extends Fragment implements Observer<Object> {

        public ProfileFragment() {        }

        private ProfileViewModel mViewModel;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ProfileFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.profile_fragment, container, false);
            View view = binding.getRoot();

            mViewModel= new ProfileViewModel();
            mViewModel.addObserver(this);
            binding.setViewModel(mViewModel);
            binding.setMainViewModel(((MainActivity)getActivity()).getViewModel());


            return view;
        }

        @Override
        public void onObserve(int event, Object eventMessage) {
            if(event == MyUtils.LOG_OUT)
            {
                ((MainActivity)getActivity()).back();
            }
            else if (event == MyUtils.SELECT_PICTURE)
            {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select File From Gallery"), SELECT_FILE_FROM_GALLERY);
            }
            else if (event == MyUtils.TAKE_PICTURE)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(intent, REQUEST_CAMERA);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mViewModel.removeObserver(this);
            mViewModel.onDestroy();
        }
    }




    public static class AboutUsFragment extends Fragment {

        public AboutUsFragment() {        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.about_us_fragment, container, false);

            AboutUsFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.about_us_fragment, container, false);
            binding.setMainViewModel(((MainActivity)getActivity()).getViewModel());

            return view;
        }

    }




    public static class SettingFragment extends Fragment {

        public SettingFragment() {        }
        FancyButton _btnColor;
        FancyButton _btnImage;
        ColorPicker _colorPicker;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            final View view = inflater.inflate(R.layout.setting_fragment, container, false);

            _btnColor = view.findViewById(R.id.btn_bg_color);
            _btnImage = view.findViewById(R.id.btn_change_bg_image);

            _colorPicker = new ColorPicker(getActivity(), 255, 255, 255, 255);

            _btnColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    _colorPicker.show();
                    _colorPicker.setCallback(new ColorPickerCallback() {
                        public void onColorChosen(@ColorInt int color) {
                            ((MainActivity)getActivity()).setBackground(color);
                            _colorPicker.hide();
                        }
                    });
                }
            });


            _btnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
            });

            return view;
        }
    }




    // ============================================================================================
    // Pager Adapter tuong tu nhu RecyclerView Adapter
    // ============================================================================================
    public class PagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        ProfileFragment _profileFragment;
        SearchFragment _searchFragment;
        AboutUsFragment _aboutUsFragment;
        SettingFragment _settingFragment;
        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    if(_profileFragment == null)
                    {
                        _profileFragment = new ProfileFragment();
                    }
                    frag = _profileFragment;
                    break;
                case 1:
                    if(_searchFragment == null)
                    {
                        _searchFragment = new SearchFragment();
                    }
                    frag = _searchFragment;
                    break;
                case 2:
                    if(_settingFragment == null)
                    {
                        _settingFragment = new SettingFragment();
                    }
                    frag = _settingFragment;
                    break;
                case 3:
                    if(_aboutUsFragment == null)
                    {
                        _aboutUsFragment = new AboutUsFragment();
                    }
                    frag = _aboutUsFragment;
                    break;
            }
            return frag;
        }


        @Override
        public int getCount() {
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }

        @Override
        public int getPageIconResId(int position) {
            switch (position) {
                case 0:
                    return R.drawable.ic_profile;
                case 1:
                    return R.drawable.ic_heart;
                case 2:
                    return R.drawable.ic_settings;
                case 3:
                    return R.drawable.ic_logo;
                default:
                    return -1;
            }
        }

    }



}
