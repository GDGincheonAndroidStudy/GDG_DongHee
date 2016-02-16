package me.dong.gdg_testsample;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by Dong on 2016-01-30.
 */
public class MyApplication extends Application {

    public Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        setRealmConfig();
        setGsonConfig();
    }

    private void setGsonConfig(){
        mGson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    public Gson getGson(){
        return mGson;
    }

    private void setRealmConfig(){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .name("gdgtest.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
