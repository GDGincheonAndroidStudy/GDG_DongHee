package me.dong.gdg_testsample.network;


import com.google.gson.JsonObject;

import me.dong.gdg_testsample.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dong on 2016-01-29.
 */
public interface BackendService {

//    @Headers("Accept: application/json")

//    @GET("/" + Constants.PRODUCT_SEARCH)
//    Call<JsonObject> productSearch(@Query(Constants.VERSION) String version, @Query(Constants.SEARCHKEYWORD) String searchKeyword);

//    @GET("/" + Constants.PRODUCT_SEARCH + "/" + "{" + Constants.PRODUCT_CODE + "}")
//    Call<JsonObject> productInfoSearch(@Query(Constants.VERSION) String version, @Path(Constants.PRODUCT_CODE) Integer productCode);

//    @Headers({
//            "Accept: application/json",
//            "appKey: fd7afb31-c3e6-3340-846c-fb33aef15d52"
//    })

    @Headers({
            "Accept: application/json",
            "appKey: " + Constants.myKEY
    })
    //상품 검색
//    @GET("products")
//    Call<JsonObject> productSearch(@Query("version") String version,
//                                   @Query("searchKeyword") String searchKeyword);
    @GET("products")
    rx.Observable<JsonObject> productSearch(@Query("version") String version,
                                   @Query("searchKeyword") String searchKeyword);

    //상품정보 조회
    @Headers("Accept: application/json")
    @GET("products/{productCode}")
    Call<JsonObject> productInfoSearch(@Query("version") String version,
                                       @Path("productCode") Integer productCode);


}
