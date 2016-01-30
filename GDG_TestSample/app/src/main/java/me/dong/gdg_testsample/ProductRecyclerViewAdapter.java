package me.dong.gdg_testsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.ui.ProductItemView;

/**
 * Created by Dong on 2016-01-29.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductViweHolder> {

    public static final String TAG = ProductRecyclerViewAdapter.class.getSimpleName();

    private ArrayList<Product> mProductArrayList;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private ImageLoader.ImageCache mImageCache = new ImageLoader.ImageCache() {

        LruCache<String, Bitmap> cache = new LruCache<>(20);

        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = cache.get(url);
            Log.d(TAG, "cached : " + (bitmap != null));
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    };

    public ProductRecyclerViewAdapter(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
        mProductArrayList = new ArrayList<>();
    }

    @Override
    public ProductViweHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductViweHolder vh = new ProductViweHolder(new ProductItemView(parent.getContext()));
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductViweHolder holder, int position) {
        Product product = mProductArrayList.get(position);
        holder.productItemView.bind(product, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public void setProductList(ArrayList<Product> productList) {
        mProductArrayList = productList;
        notifyDataSetChanged();
    }
}
