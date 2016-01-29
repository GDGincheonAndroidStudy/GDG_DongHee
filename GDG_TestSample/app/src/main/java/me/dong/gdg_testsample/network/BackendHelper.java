package me.dong.gdg_testsample.network;

import com.google.gson.JsonObject;

import me.dong.gdg_testsample.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

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
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();

        service = retrofit.create(BackendService.class);
    }

    //Todo: api 구현
    //상품 검색
    public Call<JsonObject> productSearch(String searchKeyword) {
        return service.productSearch(Constants.apiVersion, searchKeyword);
    }

    //상품 정보 조회
    public Call<JsonObject> productInfoSearch(Integer productCode) {
        return service.productInfoSearch(Constants.apiVersion, productCode);
    }
}
