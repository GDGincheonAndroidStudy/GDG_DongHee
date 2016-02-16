package me.dong.gdg_testsample.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.internal.IOException;
import me.dong.gdg_testsample.MyApplication;
import me.dong.gdg_testsample.ProductRecyclerViewAdapter;
import me.dong.gdg_testsample.R;
import me.dong.gdg_testsample.RealmSearchActivity;
import me.dong.gdg_testsample.model.MainModel;
import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.ui.ProductItemView;
import me.dong.gdg_testsample.utils.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dong on 2016-02-13.
 */
//ViewModel: model과 View를 가진다.
//프리젠테이션 로직: 화면상의 디자인 구성을 위한 로직
//양방향 바인딩: ???
public class MainViewModel {

    public static final String TAG = MainViewModel.class.getSimpleName();

    private Activity activity;
    private MainModel mainModel;

    private EditText etSearch;
    private ImageButton ibSearchStrClear;
    private Button btnSearch;
    private RecyclerView rvProduct;
    private ProductRecyclerViewAdapter mProductRecyclerViewAdapter;


    public MainViewModel(Activity activity) {
        this.activity = activity;
        this.mainModel = new MainModel();
        initView(activity);
    }

    private void initView(final Activity activity) {

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);

        ibSearchStrClear = (ImageButton) activity.findViewById(R.id.imageButton_searchStrClear);
        ibSearchStrClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainModel.setStrSearchKeyword("");
                etSearch.setText(mainModel.getStrSearchKeyword());
//                etSearch.setText(null);
            }
        });

        etSearch = (EditText) activity.findViewById(R.id.editText_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mainModel.getStrSearchKeyword().length() == 0) {
                    ibSearchStrClear.setVisibility(View.GONE);
                } else {
                    ibSearchStrClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSearch = (Button) activity.findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strSearch = etSearch.getText().toString();
                    if (strSearch.equals("")) {
                        Toast.makeText(activity, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else {

//                        strKeyWord = URLEncoder.encode(strSearch, "UTF-8");


                        mainModel.setStrSearchKeyword(URLEncoder.encode(strSearch, "UTF-8"));

                        Log.d(TAG, "encoding keyword : " + mainModel.getStrSearchKeyword());

                        Call<JsonObject> call = requestHelper.productSearch(mainModel.getStrSearchKeyword());

                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Response<JsonObject> response) {

                                JsonObject joRoot = response.body();
                                Log.d(TAG, " joRoot " + joRoot);

                                //respone 객체에 내용이 없을 시 예외처리
                                if (joRoot != null) {
                                    Log.d(TAG, " joRoot is " + joRoot);
                                    ArrayList<Product> productArrayList = new ArrayList<>();

                                    JsonObject joProductSearchResponse = joRoot.getAsJsonObject("ProductSearchResponse");
                                    Log.d(TAG, "ProductSearchResponse : " + joProductSearchResponse);

                                    JsonObject joProducts = joProductSearchResponse.getAsJsonObject("Products");

                                    JsonArray jaProductList = joProducts.getAsJsonArray("Product");

                                    //검색어가 이상할 시 상품 Array가 없이 나와 예외처리
                                    if (jaProductList != null) {
                                        for (int i = 0; i < jaProductList.size(); i++) {
                                            Log.d(TAG, "gson : " + i);
                                            Product product = ((MyApplication) activity.getApplicationContext()).mGson.fromJson(jaProductList.get(i), Product.class);
                                            productArrayList.add(product);
                                        }
                                        mProductRecyclerViewAdapter.setProductList(productArrayList);
                                        Log.d(TAG, "gson end :");
                                        /*
                                        데이터 저장
                                         */
                                        Realm realm = null;
                                        try {
                                            //이 스레드에서 Realm인스턴스 얻기
                                            realm = Realm.getInstance(activity);
                                            Log.d(TAG, "realm : ");

                                            //데이터를 손쉽게 영속적으로 만들기
                                            realm.beginTransaction();
//                                            realm.copyToRealm(productArrayList);
                                            realm.copyToRealmOrUpdate(productArrayList);
                                            realm.commitTransaction();
                                        } catch (IOException e) {
                                            if (realm != null) {
                                                realm.cancelTransaction();
                                            }
                                        } finally {
                                            if (realm != null) {
                                                realm.close();
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e(TAG, " Throwable is " + t);
                            }
                        });
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        //recyclerView initialization
        rvProduct = (RecyclerView) activity.findViewById(R.id.recyclerView_product);
        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(new LinearLayoutManager(activity));
        rvProduct.addItemDecoration(new SpacesItemDecoration(24));

        mProductRecyclerViewAdapter = new ProductRecyclerViewAdapter(activity);
        rvProduct.setAdapter(mProductRecyclerViewAdapter);

        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RealmSearchActivity.class);
                activity.startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }
}
