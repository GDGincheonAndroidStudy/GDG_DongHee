package me.dong.gdg_testsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.network.BackendHelper;
import me.dong.gdg_testsample.utils.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ibSearchStrClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText(null);
            }
        });

        etSearch = (EditText) findViewById(R.id.editText_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearch.getText().length() == 0) {
                    ibSearchStrClear.setVisibility(View.GONE);
                } else {
                    ibSearchStrClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnSearch = (Button) findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strSearch = etSearch.getText().toString();
                    if (strSearch.equals("")) {
                        Toast.makeText(MainActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else {

                        strKeyWord = URLEncoder.encode(strSearch, "UTF-8");

                        Log.d(TAG, "encoding keyword : " + strKeyWord);

                        Call<JsonObject> call = requestHelper.productSearch(strKeyWord);

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
                                            Product product = new Gson().fromJson(jaProductList.get(i), Product.class);
                                            productArrayList.add(product);
                                        }
                                        mProductRecyclerViewAdapter.setProductList(productArrayList);
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
        rvProduct = (RecyclerView) findViewById(R.id.recyclerView_product);
        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.addItemDecoration(new SpacesItemDecoration(24));

        mProductRecyclerViewAdapter = new ProductRecyclerViewAdapter(this);
        rvProduct.setAdapter(mProductRecyclerViewAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RealmSearchActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
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
