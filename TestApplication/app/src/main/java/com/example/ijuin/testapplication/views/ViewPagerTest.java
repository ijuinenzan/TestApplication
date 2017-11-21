package com.example.ijuin.testapplication.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by Khang Le on 11/21/2017.
 */

public class ViewPagerTest extends AppCompatActivity
{
    private PagerAdapter _pagerAdapter;
    private ViewPager _viewPager;
    private TabLayout tabLayout;
    private PagerSlidingTabStrip _customTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager__test);

        addControls();
    }

    public void addControls()
    {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        _pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        _viewPager = (ViewPager) findViewById(R.id.viewPagerContainer);
        _viewPager.setAdapter(_pagerAdapter);
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
            TextView txtSearchView = (TextView) view.findViewById(R.id.txtSearch);
            txtSearchView.setText("hello Search View");
            return view;
        }

    }

    public static class ProfileFragment extends Fragment {

        public ProfileFragment() {        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.profile_fragment, container, false);

            return view;
        }

    }




    // ============================================================================================
    // Pager Adapter tuong tu nhu RecyclerView Adapter
    // ============================================================================================
    public class PagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        public PagerAdapter(FragmentManager fm)
        {
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
            }
            return frag;
        }


        @Override
        public int getCount()
        {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position) {
                case 0:
                    return "Day la Search View";
                case 1:
                    return "Day la Profile View";
            }
            return null;
        }

        @Override
        public int getPageIconResId(int position)
        {
            // List<Tab> tabs;
            // return tabs.get(position).icon;

            switch (position)
            {
                case 0 :
                    return R.drawable.ic_search_heart;
                case 1:
                    return R.drawable.ic_profile;
            }
            return -1;
        }

    }
}
