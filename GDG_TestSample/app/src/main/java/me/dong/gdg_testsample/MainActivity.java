package me.dong.gdg_testsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.network.BackendHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected static BackendHelper requestHelper;

    //UTF-8로 인코딩한 검색어
    private String strKeyWord;

    private EditText etSearch;
    private ImageButton ibSearchStrClear;
    private Button btnSearch;
    private ListView mListView;

    SearchListAdapter mSearchListAdapter;
    ArrayList<Product> mProductArrayList;

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

                                if (joRoot != null) {
                                    Log.d(TAG, " joRoot is " + joRoot);
                                    ArrayList<Product> productArrayList = new ArrayList<>();

                                    JsonObject joProductSearchResponse = joRoot.getAsJsonObject("ProductSearchResponse");
                                    Log.d(TAG, "ProductSearchResponse : " + joProductSearchResponse);

                                    JsonObject joProducts = joProductSearchResponse.getAsJsonObject("Products");

                                    JsonArray jaProductList = joProducts.getAsJsonArray("Product");

                                    for (int i = 0; i < jaProductList.size(); i++) {
                                        Product product = new Gson().fromJson(jaProductList.get(i), Product.class);
                                        productArrayList.add(product);
                                    }
                                    mSearchListAdapter.setProductList(productArrayList);
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

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(mOnItemClickListener);

        mProductArrayList = new ArrayList<>();
        mSearchListAdapter = new SearchListAdapter(this);
        mSearchListAdapter.setProductList(mProductArrayList);
        mListView.setAdapter(mSearchListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "ListItem Click");
            Intent intent = new Intent(MainActivity.this, DetailPageActivity.class);
            Log.d(TAG, " " + position + " " + id);
            String detailProduct = ((Product)mSearchListAdapter.getItem(position)).getDetailPageUrl();
            intent.putExtra("detailProductUrl", detailProduct);
            startActivity(intent);
        }
    };

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
