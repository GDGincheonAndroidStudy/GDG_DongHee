package me.dong.gdg_testsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import io.realm.Realm;
import io.realm.RealmViewHolder;
import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.ui.ProductItemView;

/**
 * Created by Dong on 2016-01-30.
 */
public class RealmSearchRecyclerViewAdapter extends RealmSearchAdapter<Product, ProductRealmViewHolder> {

    public static final String TAG = RealmSearchRecyclerViewAdapter.class.getSimpleName();

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

    public RealmSearchRecyclerViewAdapter(Context context, Realm realm, String filterKey) {
        super(context, realm, filterKey);
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
    }

    @Override
    public ProductRealmViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        ProductRealmViewHolder vh = new ProductRealmViewHolder(new ProductItemView(viewGroup.getContext()));
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ProductRealmViewHolder viewHolder, int position) {
        Product product = realmResults.get(position);
        viewHolder.productItemView.bind(product, mImageLoader);
    }
}
