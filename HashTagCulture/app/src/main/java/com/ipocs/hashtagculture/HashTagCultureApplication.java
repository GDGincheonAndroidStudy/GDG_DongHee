package com.ipocs.hashtagculture;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Dong on 2015-12-10.
 */
public class HashTagCultureApplication extends Application {

    private static HashTagCultureApplication application;

    /**
     * Application singleton instance getter.
     * @return Application instance.
     */
    public static HashTagCultureApplication getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //Init Fresco
        Fresco.initialize(this);
    }
}
