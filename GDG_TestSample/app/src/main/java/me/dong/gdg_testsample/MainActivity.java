package me.dong.gdg_testsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.internal.IOException;
import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.network.BackendHelper;
import me.dong.gdg_testsample.utils.SpacesItemDecoration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected static BackendHelper requestHelper;

    //UTF-8로 인코딩한 검색어
    private String strKeyWord;

    private EditText etSearch;
    private ImageButton ibSearchStrClear;
    private Button btnSearch;
    private RecyclerView rvProduct;
    private ProductRecyclerViewAdapter mProductRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (requestHelper == null) {
            requestHelper = BackendHelper.getInstance();
        }

        ibSearchStrClear = (ImageButton) findViewById(R.id.imageButton_searchStrClear);
//        ibSearchStrClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etSearch.setText(null);
//            }
//        });

        RxView
                .clicks(findViewById(R.id.imageButton_searchStrClear))
                .subscribe(aVoid -> {
                    etSearch.setText(null);
                });

        etSearch = (EditText) findViewById(R.id.editText_search);

        Observable<Boolean> textExist = RxTextView
                .textChanges(etSearch)
                .map(TextUtils::isEmpty);

        textExist.subscribe(aBoolean -> {
            if (aBoolean) {
                ibSearchStrClear.setVisibility(View.GONE);
            } else {
                ibSearchStrClear.setVisibility(View.VISIBLE);
            }
        });

//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (etSearch.getText().length() == 0) {
//                    ibSearchStrClear.setVisibility(View.GONE);
//                } else {
//                    ibSearchStrClear.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        RxView
                .clicks(findViewById(R.id.button_search))
                .subscribe(aVoid -> {
                    String strSearch = etSearch.getText().toString();

                    if (TextUtils.isEmpty(strSearch)) {
//                            Toast.makeText(MainActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();

                        Snackbar.make(getCurrentFocus(), "검색어를 입력하세요.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        try {
                            strKeyWord = URLEncoder.encode(strSearch, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "encoding keyword : " + strKeyWord);

                        Observable<JsonObject> rx = requestHelper.productSearch(strKeyWord);
                        rx.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(joRoot -> {

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
                                                Product product = ((MyApplication) getApplicationContext()).mGson.fromJson(jaProductList.get(i), Product.class);
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
                                                realm = Realm.getInstance(MainActivity.this);
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
                                }, throwable -> {
                                    Log.e(TAG, " Throwable is " + throwable);
                                });
                    }
                });

//        btnSearch = (Button) findViewById(R.id.button_search);
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    String strSearch = etSearch.getText().toString();
//                    if (strSearch.equals("")) {
////                        Toast.makeText(MainActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
//
//                        Snackbar.make(v, "검색어를 입력하세요.", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    } else {
//
//                        strKeyWord = URLEncoder.encode(strSearch, "UTF-8");
//
//                        Log.d(TAG, "encoding keyword : " + strKeyWord);
//
//                        rx.Observable<JsonObject> rx = requestHelper.productSearch(strKeyWord);
//                        rx.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Action1<JsonObject>() {
//                                    @Override
//                                    public void call(JsonObject jsonObject) {
//
//                                        JsonObject joRoot = jsonObject;
//                                        Log.d(TAG, " joRoot " + joRoot);
//
//                                        //respone 객체에 내용이 없을 시 예외처리
//                                        if (joRoot != null) {
//                                            Log.d(TAG, " joRoot is " + joRoot);
//                                            ArrayList<Product> productArrayList = new ArrayList<>();
//
//                                            JsonObject joProductSearchResponse = joRoot.getAsJsonObject("ProductSearchResponse");
//                                            Log.d(TAG, "ProductSearchResponse : " + joProductSearchResponse);
//
//                                            JsonObject joProducts = joProductSearchResponse.getAsJsonObject("Products");
//
//                                            JsonArray jaProductList = joProducts.getAsJsonArray("Product");
//
//                                            //검색어가 이상할 시 상품 Array가 없이 나와 예외처리
//                                            if (jaProductList != null) {
//                                                for (int i = 0; i < jaProductList.size(); i++) {
//                                                    Log.d(TAG, "gson : " + i);
//                                                    Product product = ((MyApplication) getApplicationContext()).mGson.fromJson(jaProductList.get(i), Product.class);
//                                                    productArrayList.add(product);
//                                                }
//                                                mProductRecyclerViewAdapter.setProductList(productArrayList);
//                                                Log.d(TAG, "gson end :");
//                                        /*
//                                        데이터 저장
//                                         */
//                                                Realm realm = null;
//                                                try {
//                                                    //이 스레드에서 Realm인스턴스 얻기
//                                                    realm = Realm.getInstance(MainActivity.this);
//                                                    Log.d(TAG, "realm : ");
//
//                                                    //데이터를 손쉽게 영속적으로 만들기
//                                                    realm.beginTransaction();
////                                            realm.copyToRealm(productArrayList);
//                                                    realm.copyToRealmOrUpdate(productArrayList);
//                                                    realm.commitTransaction();
//                                                } catch (IOException e) {
//                                                    if (realm != null) {
//                                                        realm.cancelTransaction();
//                                                    }
//                                                } finally {
//                                                    if (realm != null) {
//                                                        realm.close();
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }, new Action1<Throwable>() {
//                                    @Override
//                                    public void call(Throwable throwable) {
//                                        Log.e(TAG, " Throwable is " + throwable);
//                                    }
//                                });
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        //recyclerView initialization
        rvProduct = (RecyclerView) findViewById(R.id.recyclerView_product);
        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.addItemDecoration(new SpacesItemDecoration(24));

        mProductRecyclerViewAdapter = new ProductRecyclerViewAdapter(this);
        rvProduct.setAdapter(mProductRecyclerViewAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RealmSearchActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
