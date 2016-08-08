package com.fozoto.duobao.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.fozoto.duobao.app.DuobaoApplication;

/**
 * Created by qingyan on 16-7-21.
 */

public class VolleyTool {
    private static VolleyTool mInstance = null;
    private RequestQueue mRequestQueue = null;
    private ImageLoader mImageLoader = null;

    private VolleyTool(Context context) {
        mRequestQueue = DuobaoApplication.getHttpQueue();
        mImageLoader = ImageLoaderUtils.getmImageLoader(mRequestQueue);
    }

    public static VolleyTool getInstance (Context context) {
        if (mInstance == null) {
            mInstance = new VolleyTool(context);
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public void release() {
        this.mRequestQueue = null;
        this.mImageLoader = null;
        mInstance = null;
    }
}
