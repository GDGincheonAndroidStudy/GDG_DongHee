package me.dong.gdg_testsample.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.realm.RealmObject;
import me.dong.gdg_testsample.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Dong on 2016-01-29.
 */
public class BackendHelper {

    private static BackendHelper instance;
    private BackendService service;

    //logging
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC);

    OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    //realm config
    Gson mGson = new GsonBuilder()
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

    //singleton pattern
    public static BackendHelper getInstance() {
        synchronized (BackendHelper.class) {
            if (instance == null) {
                instance = new BackendHelper();
            }
            return instance;
        }
    }

    private BackendHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();

        service = retrofit.create(BackendService.class);
    }

    //Todo: api 구현
    //상품 검색
    public rx.Observable<JsonObject> productSearch(String searchKeyword) {
        return service.productSearch(Constants.apiVersion, searchKeyword);
    }

    //상품 정보 조회
    public Call<JsonObject> productInfoSearch(Integer productCode) {
        return service.productInfoSearch(Constants.apiVersion, productCode);
    }
}
