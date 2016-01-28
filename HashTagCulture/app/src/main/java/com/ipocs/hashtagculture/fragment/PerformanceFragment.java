package com.ipocs.hashtagculture.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.model.Culture;
import com.ipocs.hashtagculture.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class PerformanceFragment extends BaseFragment {

    public static final String TAG = PerformanceFragment.class.getSimpleName();

    @Bind(R.id.tabLayout_menu)
    TabLayout mTabLayoutMenu;

    @Bind(R.id.fragment_container_performance)
    FrameLayout fLayoutFragmentContainer;

    FragmentManager fm;
    ArrayList<BaseFragment> mBaseFragments;

    ArrayList<Culture> cultureArrayList;

    public static PerformanceFragment newInstance() {
        PerformanceFragment fragment = new PerformanceFragment();
        return fragment;
    }

    public PerformanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performance, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayoutMenu.addTab(mTabLayoutMenu.newTab().setText("추천"));
        mTabLayoutMenu.addTab(mTabLayoutMenu.newTab().setText("전체"));
        mTabLayoutMenu.addTab(mTabLayoutMenu.newTab().setText("톡"));

        mTabLayoutMenu.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, " " + tab.getPosition());

                Fragment oldFragment = fm.findFragmentById(R.id.fragment_container_performance);

                switch (tab.getPosition()) {
                    case 0:
                        if (!(oldFragment instanceof RecommendFragment)) {
                            fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(0)).commit();
                        }
                        break;
                    case 1:
                        if (!(oldFragment instanceof EntireFragment)) {
                            fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(1)).commit();
                            getPerformanceEntire();
                        }
                        break;
                    case 2:
                        if (!(oldFragment instanceof TalkFragment)) {
                            fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(2)).commit();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_performance);

        if (fragment == null) {
            createFragment();
            Log.e("fragment", TAG + " createFragment if");

            switch (mTabLayoutMenu.getSelectedTabPosition()) {
                case 0:
                    fm.beginTransaction().add(R.id.fragment_container_performance, mBaseFragments.get(0)).commit();
                    break;
                case 1:
                    fm.beginTransaction().add(R.id.fragment_container_performance, mBaseFragments.get(1)).commit();
                    getPerformanceEntire();
                    break;
                case 2:
                    fm.beginTransaction().add(R.id.fragment_container_performance, mBaseFragments.get(2)).commit();
                    break;
            }
        } else {
            Log.e("fragment", TAG + " createFragment else");
            Log.e("fragment", TAG + " " + fragment.getClass().getSimpleName());
            switch (mTabLayoutMenu.getSelectedTabPosition()) {
                case 0:
                    Log.e("fragment", TAG + " createFragment else 0");
                    if (!(fragment instanceof RecommendFragment)) {
                        fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(0)).commit();
                    }
                    break;
                case 1:
                    Log.e("fragment", TAG + " createFragment else 1");
                    if (!(fragment instanceof EntireFragment)) {
                        fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(1)).commit();
                        getPerformanceEntire();
                    }
                    break;
                case 2:
                    if (!(fragment instanceof TalkFragment)) {
                        fm.beginTransaction().replace(R.id.fragment_container_performance, mBaseFragments.get(2)).commit();
                    }
                    break;
            }
        }
        Log.e("fragment", TAG + " onViewCreated");
    }

    public void createFragment() {

        mBaseFragments = new ArrayList<>();

        RecommendFragment recommendFragment = RecommendFragment.newInstance();
        recommendFragment.setCategoryCode(Constants.REQUEST_CODE_PERFORMANCE_FRAGMENT);
        recommendFragment.setTitle("추천");
        EntireFragment entireFragment = EntireFragment.newInstance();
        entireFragment.setCategoryCode(Constants.REQUEST_CODE_PERFORMANCE_FRAGMENT);
        entireFragment.setTitle("전체");
        TalkFragment talkFragment = TalkFragment.newInstance();
        talkFragment.setCategoryCode(Constants.REQUEST_CODE_PERFORMANCE_FRAGMENT);
        talkFragment.setTitle("톡");

        mBaseFragments.add(recommendFragment);
        mBaseFragments.add(entireFragment);
        mBaseFragments.add(talkFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", TAG + " onAttach");
    }

    @Override
    public void onDestroyView() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_performance);
        if (fragment != null) {
            Log.e("fragment", TAG + " onDestroyView " + fragment.getClass().getSimpleName());
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
        super.onDestroyView();
        Log.e("fragment", TAG + " onDestroyView");
    }

    public void removeFragment() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_performance);
        if (fragment != null) {
            Log.e("fragment", TAG + " onDestroyView " + fragment.getClass().getSimpleName());
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onDetach() {
        Log.e("fragment", TAG + " onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.e("fragment", TAG + " onDestroy");
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", TAG + " onResume");

        switch (mTabLayoutMenu.getSelectedTabPosition()) {
            case 0:
                break;
            case 1:
                getPerformanceEntire();
                break;
            case 2:

                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("fragment", TAG + " onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e("fragment", TAG + " onViewStateRestored");
    }

    public void getPerformanceEntire() {

        Call<JsonArray> call = requestHelper.getPerformanceEntire();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Response<JsonArray> response, Retrofit retrofit) {

                JsonArray jsonArray = response.body();

                if (jsonArray != null) {

                    cultureArrayList = new ArrayList<>();

                    Log.d(TAG, " jsonArray is " + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        Culture culture = new Gson().fromJson(jsonArray.get(i), Culture.class);
                        cultureArrayList.add(culture);
                    }
                }

                Log.e(TAG, " " + cultureArrayList);
                ((EntireFragment) mBaseFragments.get(1)).setData(cultureArrayList);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });
    }

    public void getPerformanceRecommend(List<String> categories, List<String> locations, long startDate, long endDate) {

        Call<JsonArray> call = requestHelper.getPerformanceRecommend(categories, locations, startDate, endDate);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Response<JsonArray> response, Retrofit retrofit) {

                JsonArray jsonArray = response.body();

                if (jsonArray != null) {

                    cultureArrayList = new ArrayList<>();

                    Log.d(TAG, " jsonArray is " + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        Culture culture = new Gson().fromJson(jsonArray.get(i), Culture.class);
                        cultureArrayList.add(culture);
                    }
                }

                Log.e(TAG, " " + cultureArrayList);
                ((RecommendFragment) mBaseFragments.get(0)).setData(cultureArrayList);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });
    }
}
