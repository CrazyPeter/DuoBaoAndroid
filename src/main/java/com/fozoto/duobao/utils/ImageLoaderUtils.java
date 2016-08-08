package com.fozoto.duobao.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by qingyan on 16-7-21.
 */

public class ImageLoaderUtils {
    private static ImageLoader mImageLoader;


    public static ImageLoader getmImageLoader(RequestQueue queue) {
        // 单例模式
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {

                private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
                        (int) Runtime.getRuntime().maxMemory() / 8) {
                    @Override
                    protected int sizeOf(String key, Bitmap value) {
                        return value.getRowBytes() * value.getHeight();
                    }

                    @Override
                    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                        super.entryRemoved(evicted, key, oldValue, newValue);
                        //如果为true为存满后 挤出来的
                        if (evicted) {
                            oldValue.recycle();
                            oldValue = null;
                        }
                    }
                };

                @Override
                public Bitmap getBitmap(String s) {
                    return cache.get(s);
                }

                @Override
                public void putBitmap(String s, Bitmap bitmap) {
                    cache.put(s, bitmap);
                }
            });
        }

        return mImageLoader;
    }

    public void letImageLoaderToGC() {
        mImageLoader = null;
    }
}
