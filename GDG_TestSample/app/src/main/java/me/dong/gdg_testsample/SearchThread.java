package me.dong.gdg_testsample;

import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.dong.gdg_testsample.model.Product;

/**
 * Created by Dong on 2016-01-16.
 */
public class SearchThread extends Thread {

    public static final String TAG = SearchThread.class.getSimpleName();

    Handler mHandler;
    SearchListAdapter adapter;
    String strUrl;
    ArrayList<Product> mProductList;

    public SearchThread(String strUrl, SearchListAdapter adapter) {
        mHandler = new Handler();
        this.strUrl = strUrl;
        this.adapter = adapter;
        this.mProductList = new ArrayList<>();
    }

    @Override
    public void run() {
        Log.d(TAG, " urlStr : " + strUrl);

        try {
            URL url = new URL(strUrl);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();

            if(is == null){
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer buffer = new StringBuffer();

            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String jsonStr = buffer.toString();
            JSONObject root = new JSONObject(jsonStr);
            Log.d(TAG, "jsonStr : " + jsonStr);

            JSONObject productSearchResponse = root.getJSONObject("ProductSearchResponse");
            Log.d(TAG, "ProductSearchResponse : " + productSearchResponse);

            JSONObject products = productSearchResponse.getJSONObject("Products");

            JSONArray productList = products.getJSONArray("Product");

            for (int i = 0; i < productList.length(); i++) {
                JSONObject product = productList.getJSONObject(i);
                String name = product.getString("ProductName");
                String imgUrl = product.getString("ProductImage");
                String detailPageUrl = product.getString("DetailPageUrl");
                Integer productCode = product.getInt("ProductCode");

                Log.d(TAG, " name : " + name);
                Log.d(TAG, " imgurl : " + imgUrl);
                Log.d(TAG, " detailurl : " + detailPageUrl);
                Log.d(TAG, " detailurl : " + detailPageUrl);

                Product productInfo = new Product(name, imgUrl, detailPageUrl, productCode);
                mProductList.add(productInfo);
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setProductList(mProductList);
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            Log.e("Error", " message", e);
            e.printStackTrace();
        }
    }
}
