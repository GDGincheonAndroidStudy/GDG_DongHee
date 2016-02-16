package me.dong.gdg_testsample.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by Dong on 2016-02-16.
 */
public class BaseViewModel {

    protected Activity activity;

    public BaseViewModel(@NonNull Activity activity){
        this.activity = activity;

    }
}
