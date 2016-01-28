package com.ipocs.hashtagculture.fragment;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ipocs.hashtagculture.adapter.RecommendAdapter;
import com.ipocs.hashtagculture.model.Culture;
import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.utils.Constants;
import com.ipocs.hashtagculture.utils.SpacesItemDecoration;
import com.ipocs.hashtagculture.utils.TimeUtils;
import com.ipocs.hashtagculture.widget.SelectTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RecommendFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = RecommendFragment.class.getSimpleName();

    @Bind(R.id.recyclerView_recommend)
    RecyclerView rvRecommend;

    @Bind(R.id.imageView_tag_show)
    ImageView ivTagShow;

    @Bind(R.id.linearLayout_tag)
    LinearLayout linearTag;

    @Bind(R.id.relativeLayout_tag)
    RelativeLayout relativeTag;

    @Bind(R.id.textView_tag_music)
    SelectTextView tvTagMusic;

    @Bind(R.id.textView_tag_concert)
    SelectTextView tvTagConcert;

    @Bind(R.id.textView_tag_theater)
    SelectTextView tvTagTheater;

    @Bind(R.id.textView_tag_gukak)
    SelectTextView tvTagGukak;

    @Bind(R.id.textView_tag_art)
    SelectTextView tvTagArt;

    @Bind(R.id.textView_tag_etc)
    SelectTextView tvTagEtc;

    boolean tagShowFlag;

    ArrayList<Culture> mCultureArrayList;
    RecommendAdapter mRecommendAdapter;

    ArrayList<SelectTextView> mSelectTextViews;

    List<String> categorys;
    List<String> locations;

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCultureArrayList = new ArrayList<>();
        mSelectTextViews = new ArrayList<>();

        categorys = new ArrayList<>();
        locations = new ArrayList<>();
        locations.add("서울");

        mSelectTextViews.add(tvTagMusic);
        mSelectTextViews.add(tvTagConcert);
        mSelectTextViews.add(tvTagTheater);
        mSelectTextViews.add(tvTagGukak);
        mSelectTextViews.add(tvTagArt);
        mSelectTextViews.add(tvTagEtc);

        tagShowFlag = true;

        mRecommendAdapter = new RecommendAdapter(getActivity(), mCultureArrayList, super.getCategoryCode());

        rvRecommend.setAdapter(mRecommendAdapter);
        rvRecommend.setHasFixedSize(true);
        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecommend.addItemDecoration(new SpacesItemDecoration(24));

        Log.e("fragment", TAG + " onViewCreated");

        relativeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tagShowFlag = !tagShowFlag;

                if (tagShowFlag) {
                    linearTag.setVisibility(View.GONE);
                    ivTagShow.setImageResource(android.R.drawable.arrow_down_float);
                } else {
                    linearTag.setVisibility(View.VISIBLE);
                    ivTagShow.setImageResource(android.R.drawable.arrow_up_float);
                }
            }
        });
    }

    public void setData(ArrayList<Culture> cultureArrayList) {
        Log.e(TAG, " " + cultureArrayList);
        Log.e(TAG, " " + mRecommendAdapter);
        mRecommendAdapter.setData(cultureArrayList);
    }

    @OnClick({R.id.textView_tag_music, R.id.textView_tag_concert, R.id.textView_tag_theater, R.id.textView_tag_gukak, R.id.textView_tag_art, R.id.textView_tag_etc})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView_tag_music:
                tvTagMusic.setSelect(!tvTagMusic.getSelect());
                break;
            case R.id.textView_tag_concert:
                tvTagConcert.setSelect(!tvTagConcert.getSelect());
                break;
            case R.id.textView_tag_theater:
                tvTagTheater.setSelect(!tvTagTheater.getSelect());
                break;
            case R.id.textView_tag_gukak:
                tvTagGukak.setSelect(!tvTagGukak.getSelect());
                break;
            case R.id.textView_tag_art:
                tvTagArt.setSelect(!tvTagArt.getSelect());
                break;
            case R.id.textView_tag_etc:
                tvTagEtc.setSelect(!tvTagEtc.getSelect());
                break;
        }

        categorys = new ArrayList<>();

        for(SelectTextView selectTextView : mSelectTextViews){
            if(selectTextView.getSelect()){
                selectTextView.setBackgroundResource(R.drawable.category_shape_select);
                categorys.add(selectTextView.getText().toString());
            }else{
                selectTextView.setBackgroundResource(R.drawable.category_shape_normal);
            }
        }

        getRecommendInformance();
    }

    public void getRecommendInformance() {
        long currentTime = TimeUtils.getTodayTime();

        Log.e(TAG, " " + currentTime + " " + (currentTime +  (60 * 60 * 24 * 30)));

        switch (super.getCategoryCode()) {
            case Constants.REQUEST_CODE_PERFORMANCE_FRAGMENT:
                getPerformanceRecommend(categorys, locations, 1316009600, 1520329600);
                break;
            case Constants.REQUEST_CODE_EXHIBIT_FRAGMENT:
                getExhibitRecommend(categorys, locations, 1316009600, 1520329600);
                break;
            case Constants.REQUEST_CODE_FESTIVAL_FRAGMENT:
                break;
        }
    }

    //추천 공연 정보
    public void getPerformanceRecommend(List<String> categories, List<String> locations, long startDate, long endDate) {

        Call<JsonArray> call = requestHelper.getPerformanceRecommend(categories, locations, startDate, endDate);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Response<JsonArray> response, Retrofit retrofit) {

                JsonArray jsonArray = response.body();

                if(jsonArray != null) {

                    mCultureArrayList = new ArrayList<Culture>();

                    Log.d(TAG, " jsonArray is " + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        Culture culture = new Gson().fromJson(jsonArray.get(i), Culture.class);
                        mCultureArrayList.add(culture);
                    }
                   setData(mCultureArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });
    }

    //추천 전시 정보
    public void getExhibitRecommend(List<String> categories, List<String> locations, long startDate, long endDate) {

        Call<JsonArray> call = requestHelper.getExhibitRecommend(categories, locations, startDate, endDate);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Response<JsonArray> response, Retrofit retrofit) {

                JsonArray jsonArray = response.body();

                if(jsonArray != null) {

                    mCultureArrayList = new ArrayList<Culture>();

                    Log.d(TAG, " jsonArray is " + jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        Culture culture = new Gson().fromJson(jsonArray.get(i), Culture.class);
                        mCultureArrayList.add(culture);
                    }
                    setData(mCultureArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", TAG + " onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("fragment", TAG + " onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("fragment", TAG + " onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("fragment", TAG + " onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", TAG + " onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("fragment", TAG + " onPause");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment", TAG + " onPause");
    }
}
