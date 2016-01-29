package me.dong.gdg_testsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import me.dong.gdg_testsample.model.Product;

/**
 * Created by Dong on 2016-01-15.
 */
public class SearchListAdapter extends BaseAdapter {

    public static final String TAG = SearchListAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Product> mProductArrayList;

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

    public SearchListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
    }

    @Override
    public int getCount() {
        return mProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if(view == null){
            view = mLayoutInflater.inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();

            holder.ivProductImg = (NetworkImageView)view.findViewById(R.id.imageView_product_image);
            holder.tvProductTitle = (TextView)view.findViewById(R.id.textView_product_title);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        Product product = mProductArrayList.get(position);
        holder.ivProductImg.setImageUrl(product.getImageUrl(), mImageLoader);
        holder.tvProductTitle.setText(product.getName());

        return view;
    }

    public void setProductList(ArrayList<Product> productList){
        mProductArrayList = productList;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        NetworkImageView ivProductImg;
        TextView tvProductTitle;
    }
}
