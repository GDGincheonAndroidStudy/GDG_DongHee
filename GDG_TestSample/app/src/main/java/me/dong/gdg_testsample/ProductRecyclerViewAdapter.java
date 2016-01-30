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
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    public static final String TAG = ProductRecyclerViewAdapter.class.getSimpleName();

    private ArrayList<Product> mProductArrayList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
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
        mLayoutInflater = mLayoutInflater.from(mContext);
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
        mProductArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(new ProductItemView(parent.getContext()));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ProductItemView productItemView;

        public ViewHolder(ProductItemView itemView) {
            super(itemView);
            this.productItemView = itemView;
        }
    }
}
