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
        View view = mLayoutInflater.inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = mProductArrayList.get(position);

        holder.tvProductTitle.setText(product.getName());
        holder.ivProductImg.setImageUrl(product.getImageUrl(), mImageLoader);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ListItem Click");
                Intent intent = new Intent(mContext, DetailPageActivity.class);
                String detailProduct = product.getDetailPageUrl();
                intent.putExtra("detailProductUrl", detailProduct);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public void setProductList(ArrayList<Product> productList){
        mProductArrayList = productList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView ivProductImg;
        TextView tvProductTitle;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ivProductImg = (NetworkImageView) itemView.findViewById(R.id.imageView_product_image);
            tvProductTitle = (TextView) itemView.findViewById(R.id.textView_product_title);
        }
    }
}
