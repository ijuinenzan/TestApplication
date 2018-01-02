package com.example.ijuin.testapplication.views;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.astuetz.PagerSlidingTabStrip;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.AboutUsFragmentBinding;
import com.example.ijuin.testapplication.databinding.ActivityMainBinding;
import com.example.ijuin.testapplication.databinding.ProfileFragmentBinding;

import com.example.ijuin.testapplication.databinding.SearchFragmentBinding;

import com.example.ijuin.testapplication.interfaces.Observer;
import com.example.ijuin.testapplication.utils.FirebaseManager;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.example.ijuin.testapplication.viewmodels.MainViewModel;
import com.example.ijuin.testapplication.viewmodels.ProfileViewModel;

import com.example.ijuin.testapplication.viewmodels.SearchViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
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
    private ViewPager _viewPager;
    private TabLayout tabLayout;
    private PagerSlidingTabStrip _customTab;

    private ActivityMainBinding _binding;
    private MainViewModel _viewModel;

    private MediaPlayer _mediaPlayer;
//endregion

    public MainViewModel getViewModel()
    {
        return _viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        int color = settings.getInt("bg_color", android.R.color.white);


        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        _viewModel = new MainViewModel();
        _binding.setViewModel(_viewModel);
        _binding.setActivity(this);
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

    public void setBackground(int color)
    {
        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("bg_color", color);
        editor.commit();
        _viewModel.setBackgroundColor(color);
    }

    public void back()
    {
        super.onBackPressed();
    }

    public void addControls()
    {
        _pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        _viewPager = (ViewPager) findViewById(R.id.viewPagerContainer);
        _viewPager.setAdapter(_pagerAdapter) ;

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.findViewById(R.id.profileFragment);
            }
        };

        _viewPager.setPageTransformer(true,transformer);

        _customTab = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        _customTab.setViewPager(_viewPager);

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
    public void onObserve(int event, String chatRoom) {
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
           SearchFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.search_fragment, container, false);
            View view = binding.getRoot();

            mViewModel= new SearchViewModel();
            binding.setViewModel(mViewModel);

            final BubbleThumbRangeSeekbar rangeSeekbar = (BubbleThumbRangeSeekbar) view.findViewById(R.id.rangeSeekbar);

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
        private Button _btnChangeProfileImg;
        private ImageView _imgProfile;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ProfileFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.profile_fragment, container, false);
            View view = binding.getRoot();

            _imgProfile = (ImageView) view.findViewById(R.id.img_UserIcon);
            _btnChangeProfileImg = (Button) view.findViewById(R.id.btn_ChangeUserImg);

            mViewModel= new ProfileViewModel();
            mViewModel.addObserver(this);
            binding.setViewModel(mViewModel);
            binding.setMainViewModel(((MainActivity)getActivity()).getViewModel());


            return view;
        }

        // ============================================================================================
        //  Create a directory with name is "imageDir" then get a file with name "khangdeptrai" in "imageDir" directory and save a bitmap to this link
        //  PARAMS: a bitmap
        //  RETURN: a path link to "imageDir"
        // ============================================================================================
        private String saveImage(Bitmap bitmapImage)
        {
            ContextWrapper cw = new ContextWrapper(getContext()); // or getApplicationContext()
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,"khangdeptrai.jpg");

            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return directory.getAbsolutePath();
        }


        // ============================================================================================
        //  To load an image from specified path
        //  PARAMS: a (String) path
        //  RETURN: non
        // ============================================================================================
        private void loadImage(String path)
        {
            try
            {
                File f=new File(path, "khangdeptrai.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                _imgProfile.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

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
                getActivity().startActivityForResult(intent.createChooser(intent, "Select File From Gallery"), SELECT_FILE_FROM_GALLERY);
            }
            else if (event == MyUtils.TAKE_PICTURE)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ((MainActivity)getActivity()).startActivityForResult(intent, REQUEST_CAMERA);
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        TextView _txt;
        Button _sound;
        Button _btnColor;
        Button _btnImage;
        ImageView _imgView;
        private int selectedColor;
        View _main;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            SharedPreferences settings = getActivity().getSharedPreferences(SETTINGS, 0);
            int color = settings.getInt("bg_color", android.R.color.white);

            final View view = inflater.inflate(R.layout.setting_fragment, container, false);
            _txt = (TextView) view.findViewById(R.id.content);
            _sound = (Button) view.findViewById(R.id.btn_change_sound);
            _btnColor = (Button) view.findViewById(R.id.btn_bg_color);
            _btnImage = (Button) view.findViewById(R.id.btn_change_bg_image);
            _imgView = (ImageView) view.findViewById(R.id.bg_img_selected) ;
            _main = (View) view.findViewById(R.id.mainPercentRelativeLayout);
            _main.setBackgroundColor(color);


            _sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    _txt.setText(readFromFile_string(writeToFile("Khang Dep Trai","settings", "users"),"users"));
                }
            });

            _btnColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int[] arr_colors = getResources().getIntArray(R.array.arr_colors);
                    selectedColor = R.color.red;
                    ColorPickerDialog colorPickerDialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                            arr_colors,
                            selectedColor,
                            5, // Number of columns
                            ColorPickerDialog.SIZE_SMALL,
                            true // True or False to enable or disable the serpentine effect
                            //0, // stroke width
                            //Color.BLACK // stroke color
                    );

                    colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

                        @Override
                        public void onColorSelected(int color) {
                            selectedColor = color;
                            ((MainActivity)getActivity()).setBackground(color);
                            _main.setBackgroundColor(selectedColor);
                        }
                    });

                    colorPickerDialog.show(getActivity().getFragmentManager(), "color_dialog_test");
                }
            });


            _btnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
            });



            return view;
        }


//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (resultCode == Activity.RESULT_OK)
//            {
//                if (requestCode == SELECT_FILE)
//                {
//                    Uri selectedImageUri = data.getData();
//                    _imgView.setImageURI(selectedImageUri);
//                }
//            }
//        }

        private String writeToFile(String content, String fileDirName, String fileName) {
            ContextWrapper cw = new ContextWrapper(getContext());

            File directory = cw.getDir(fileDirName, Context.MODE_PRIVATE);

            File mypath = new File(directory,fileName + ".txt");

            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(mypath);
                fos.write(content.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return directory.getAbsolutePath();
        }


        private String readFromFile_string(String filePath, String fileName) {

            String content = "";
            FileInputStream fis = null;
            try
            {
                File f = new File(filePath, fileName+".txt");
                fis = new FileInputStream(f);
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                content = new String(buffer);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return content;
        }

        private int readFromFile_int(String filePath, String fileName) {

            String content = "";
            FileInputStream fis = null;
            try
            {
                File f = new File(filePath, fileName+".txt");
                fis = new FileInputStream(f);
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                content = new String(buffer);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return Integer.parseInt(content);
        }

    }




    // ============================================================================================
    // Pager Adapter tuong tu nhu RecyclerView Adapter
    // ============================================================================================
    public class PagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    frag = new ProfileFragment();
                    break;
                case 1:
                    frag = new SearchFragment();
                    break;
                case 2:
                    frag = new SettingFragment();
                    break;
                case 3:
                    frag = new AboutUsFragment();
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
            // List<Tab> tabs;
            // return tabs.get(position).icon;

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
