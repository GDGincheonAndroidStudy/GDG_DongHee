package me.dong.gdg_testsample.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Dong on 2016-02-13.
 */
public class ImageLoaderHelper {

    public static final String TAG = ImageLoaderHelper.class.getSimpleName();

    RequestQueue mRequestQueue;
    ImageLoader mImageLoader;
    ImageLoader.ImageCache mImageCache = new ImageLoader.ImageCache() {

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






}
