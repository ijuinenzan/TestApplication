package com.example.ijuin.testapplication.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;
import com.example.ijuin.testapplication.R;
import com.example.ijuin.testapplication.databinding.ProfileFragmentBinding;
import com.example.ijuin.testapplication.factories.UserFactory;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.viewmodels.ProfileViewModel;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by Khang Le on 11/21/2017.
 */

public class MainActivity extends AppCompatActivity
{
    private PagerAdapter _pagerAdapter;
    private ViewPager _viewPager;
    private TabLayout tabLayout;
    private PagerSlidingTabStrip _customTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }



    public void addControls()
    {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        //_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(_viewPager);

        _customTab = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        _customTab.setViewPager(_viewPager);
    }


    public static class SearchFragment extends Fragment {

        public SearchFragment() {        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.search_fragment, container, false);
            setRangeSeekbar(view);
            return view;
        }
        private void setRangeSeekbar(View rootView)
        {
            // get seekbar from view
            final BubbleThumbRangeSeekbar rangeSeekbar = (BubbleThumbRangeSeekbar) rootView.findViewById(R.id.rangeSeekbar);

            // get min and max text view
            final TextView txtAgeMin = (TextView) rootView.findViewById(R.id.txtAgeMin);
            final TextView txtAgeMax = (TextView) rootView.findViewById(R.id.txtAgeMax);

            // set listener
            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue)
                {
                    txtAgeMin.setText(String.valueOf(minValue));
                    txtAgeMax.setText(String.valueOf(maxValue));
                }
            });
        }

    }

    public static class ProfileFragment extends Fragment {

        public ProfileFragment() {        }

        private ProfileViewModel mViewModel;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ProfileFragmentBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.profile_fragment, container, false);
            View view = binding.getRoot();

            Bundle extras = getActivity().getIntent().getExtras();

            mViewModel= new ProfileViewModel((UserModel)extras.getSerializable("User"));
            binding.setViewModel(mViewModel);

            return view;
        }

    }

    public static class AboutUsFragment extends Fragment {

        public AboutUsFragment() {        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.about_us_fragment, container, false);

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

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    frag = new SearchFragment();
                    break;
                case 1:
                    frag = new ProfileFragment();
                    break;
                case 2:
                    frag = new AboutUsFragment();
                    break;
            }
            return frag;
        }


        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day la Search View";
                case 1:
                    return "Day la Profile View";
            }
            return null;
        }

        @Override
        public int getPageIconResId(int position) {
            // List<Tab> tabs;
            // return tabs.get(position).icon;

            switch (position) {
                case 0:
                    return R.drawable.heart;
                case 1:
                    return R.drawable.profile;
                case 2:
                    return R.drawable.logo;
                default:
                    return -1;
            }
        }

    }
}
