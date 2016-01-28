package com.ipocs.hashtagculture.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.fragment.ExhibitFragment;
import com.ipocs.hashtagculture.fragment.FestivalFragment;
import com.ipocs.hashtagculture.fragment.PerformanceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.tabLayout_Category)
    TabLayout mTabLayoutCategory;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_person_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        mTabLayoutCategory.setupWithViewPager(mViewPager);
//        mTabLayoutCategory.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e(TAG, " " + tab.getPosition());
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });

        String deviceId = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        setPreferenceUid(deviceId);
        Log.e(TAG, " 1 " + deviceId);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = telephonyManager.getDeviceId();
        Log.e(TAG, " 2 " + deviceid);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PerformanceFragment.newInstance(), "공연");
        viewPagerAdapter.addFragment(ExhibitFragment.newInstance(), "전시");
        viewPagerAdapter.addFragment(FestivalFragment.newInstance(), "축제");
        viewPager.setAdapter(viewPagerAdapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, " onDestroy");

//        for(int i=0; i<viewPagerAdapter.getCount(); i++){
//            Log.e(TAG, " onDestroy" + i);
//            viewPagerAdapter.destroyItem(mViewPager, i, viewPagerAdapter.getItem(i));
//        }

        if(viewPagerAdapter.getItem(0) != null){
            ((PerformanceFragment)viewPagerAdapter.getItem(0)).removeFragment();
        }
        if(viewPagerAdapter.getItem(1) != null){
            ((ExhibitFragment)viewPagerAdapter.getItem(1)).removeFragment();
        }
        if(viewPagerAdapter.getItem(2) != null){
            ((FestivalFragment)viewPagerAdapter.getItem(2)).removeFragment();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, " onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, " onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, " onSaveInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, " onSaveInstanceState  outPersistentState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, " onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    private long mExitModeTime = 0L;

    @Override
    public void onBackPressed() {

        //Back키를 누르면 먼저 토스트를 통해 알리고, 만일 사용자가 3초 이내에 다시 누르면 종료
        if(mExitModeTime != 0 && SystemClock.uptimeMillis() - mExitModeTime < 3000){
            finish();
        }else{
            Toast.makeText(this, "이전키를 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            mExitModeTime = SystemClock.uptimeMillis();
        }
    }
}
