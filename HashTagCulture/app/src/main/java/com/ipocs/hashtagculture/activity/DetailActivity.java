package com.ipocs.hashtagculture.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ipocs.hashtagculture.R;
import com.ipocs.hashtagculture.model.DetailCulture;
import com.ipocs.hashtagculture.utils.Constants;
import com.ipocs.hashtagculture.utils.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailActivity extends BaseActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.imageView_backdrop)
    SimpleDraweeView ivBackdrop;

    @Bind(R.id.imageView_detail_poster)
    SimpleDraweeView ivDetailPoster;

    @Bind(R.id. textView_title_content)
    TextView tvTitle;

    @Bind(R.id.textView_period_start)
    TextView tvPeriodStart;

    @Bind(R.id.textView_period_end)
    TextView tvPeriodEnd;

    @Bind(R.id.textView_place_content)
    TextView tvPlace;

    @Bind(R.id.textView_placeAddr_content)
    TextView tvPlaceAddr;

    @Bind(R.id.textView_placeUrl_content)
    TextView tvPlaceUrl;

    @Bind(R.id.textView_price_content)
    TextView tvPrice;

    @Bind(R.id.textView_homePage_content)
    TextView tvHomePage;

    @Bind(R.id.textView_category)
    TextView tvCategory;

    @Bind(R.id.textView_area)
    TextView tvArea;

    int id;
    int hostFragmentCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        id = intent.getIntExtra("id", 0);
        hostFragmentCode = intent.getIntExtra("hostFragmentCode", 0);

        Log.e(TAG, " id" + id + " code" + hostFragmentCode);
        if (id != 0 && hostFragmentCode != 0) {
            getDetailInfo(id);
        }

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.MyCustomCollapsingToolbarTextAppearance);
    }

    private void loadBackdrop(DetailCulture detailCulture) {

        Log.e(TAG, " " + detailCulture.getMainPosterUrl());
        Log.e(TAG, " " + detailCulture.getDetailPosterUrl());

        ivBackdrop.getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);

        ivDetailPoster.getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);

        if (detailCulture.getMainPosterUrl() != null) {    //이미지 url
            Uri uri = Uri.parse(detailCulture.getMainPosterUrl());
            ivBackdrop.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("http://www.culture.go.kr/upload/rdf/1435908782387o%EB%B3%91%EC%82%AC%EC%9D%B4%EC%95%BC%EA%B8%B0_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg");
            ivBackdrop.setImageURI(uri);
        }

        if (detailCulture.getDetailPosterUrl() != null) {    //이미지 url
            Uri uri = Uri.parse(detailCulture.getDetailPosterUrl());
            ivDetailPoster.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("http://www.culture.go.kr/upload/rdf/1435908782387o%EB%B3%91%EC%82%AC%EC%9D%B4%EC%95%BC%EA%B8%B0_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg");
            ivDetailPoster.setImageURI(uri);
        }
    }

    //Todo: DB에서 Data load
    private void loadData(DetailCulture detailCulture) {
        collapsingToolbar.setTitle(detailCulture.getTitle());
        tvTitle.setText(detailCulture.getTitle());
        tvPeriodStart.setText(TimeUtils.UnixTimeStampToStringDate(detailCulture.getStartDate()));
        tvPeriodEnd.setText(TimeUtils.UnixTimeStampToStringDate(detailCulture.getEndDate()));
        tvPlace.setText(detailCulture.getPlace());
        tvPlaceAddr.setText(detailCulture.getPlaceAddr());
        tvPrice.setText(detailCulture.getPrice());
        tvHomePage.setText(detailCulture.getHomePage());
        tvCategory.setText("#" + detailCulture.getCategory());
        tvArea.setText("#" + detailCulture.getArea());
        tvPlaceUrl.setText(detailCulture.getPlaceUrl());
    }

    public void getDetailInfo(Integer id) {
        switch (hostFragmentCode) {
            case Constants.REQUEST_CODE_PERFORMANCE_FRAGMENT:
                getPerformanceDetail(id);
                break;
            case Constants.REQUEST_CODE_EXHIBIT_FRAGMENT:
                getExhibitDetail(id);
                break;
            case Constants.REQUEST_CODE_FESTIVAL_FRAGMENT:
                break;
        }
    }

    public void getPerformanceDetail(Integer id) {

        Call<JsonObject> call = requestHelper.getPerformanceDetail(id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                JsonObject jsonObject = response.body();

                if (jsonObject != null) {
                    DetailCulture detailCulture = new Gson().fromJson(jsonObject, DetailCulture.class);

                    loadBackdrop(detailCulture); //인텐트로 받은 객체 넘기기
                    loadData(detailCulture);  //인텐트로 받은 객체 넘기기
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });

    }

    public void getExhibitDetail(Integer id) {

        Call<JsonObject> call = requestHelper.getExhibitDetail(id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                JsonObject jsonObject = response.body();

                if (jsonObject != null) {
                    DetailCulture detailCulture = new Gson().fromJson(jsonObject, DetailCulture.class);

                    loadBackdrop(detailCulture); //인텐트로 받은 객체 넘기기
                    loadData(detailCulture);  //인텐트로 받은 객체 넘기기
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, " Throwable is " + t);
            }
        });
    }
}
