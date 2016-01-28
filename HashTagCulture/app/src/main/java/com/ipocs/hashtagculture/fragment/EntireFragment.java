package com.ipocs.hashtagculture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.adapter.EntireAdapter;
import com.ipocs.hashtagculture.model.Culture;
import com.ipocs.hashtagculture.utils.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EntireFragment extends BaseFragment {

    public static final String TAG = EntireFragment.class.getSimpleName();

    @Bind(R.id.recyclerView_entire)
    RecyclerView rvEntire;

    ArrayList<Culture> mCultureArrayList;
    EntireAdapter mEntireAdapter;

    public static EntireFragment newInstance() {
        EntireFragment fragment = new EntireFragment();
        return fragment;
    }

    public EntireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entire, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCultureArrayList = new ArrayList<>();

//        for(int i=0; i<20; i++){
//            ArrayList<String> strings = new ArrayList<>();
//            Culture culture = new Culture("한국 피아노 협회공연","fdfd","2015-12-22","2015-12-25",strings, false);
//            mCultureArrayList.add(culture);
//        }

        mEntireAdapter = new EntireAdapter(getActivity(), mCultureArrayList, super.getCategoryCode());

        rvEntire.setAdapter(mEntireAdapter);
        rvEntire.setHasFixedSize(true);
//        rvEntire.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvEntire.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        rvEntire.addItemDecoration(new SpacesItemDecoration(24));

        Log.e("fragment", TAG + " onViewCreated");
    }

    public void setData(ArrayList<Culture> cultureArrayList){
        Log.e(TAG, " " + cultureArrayList);
        Log.e(TAG, " " + mEntireAdapter);
        mEntireAdapter.setData(cultureArrayList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("fragment", TAG + " onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("fragment", TAG +  " onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("fragment", TAG +  " onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("fragment", TAG +  " onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", TAG +  " onResume");
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
