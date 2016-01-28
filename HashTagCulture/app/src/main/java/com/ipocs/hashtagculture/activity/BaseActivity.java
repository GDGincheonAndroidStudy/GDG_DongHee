package com.ipocs.hashtagculture.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ipocs.hashtagculture.utils.BackendHelper;
import com.ipocs.hashtagculture.utils.Constants;

/**
 * Created by Dong on 2015-12-01.
 */
public class BaseActivity extends AppCompatActivity {

    protected static BackendHelper requestHelper;
    protected static SharedPreferences sSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (requestHelper == null) {
            requestHelper = BackendHelper.getInstance();
        }

        if (sSharedPreferences == null) {
            sSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    //저장된 정보 가져오기
    public String getPreferenceUid() {
        return sSharedPreferences.getString(Constants.UID, "");
    }

    //정보 저장
    protected void setPreferenceUid(String uid) {
        sSharedPreferences.edit()
                .putString(Constants.UID, uid)
                .apply();
    }

    //저장된 정보 삭제
    protected void clearPerference() {
        sSharedPreferences.edit()
                .clear()
                .apply();
    }
}
