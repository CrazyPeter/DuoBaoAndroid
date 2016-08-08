package com.fozoto.duobao.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by qingyan on 16-8-2.
 */

public class DuobaoApplication extends Application{

    private static RequestQueue queue = null;

    @Override
    public void onCreate() {
        super.onCreate();

        queue = Volley.newRequestQueue(getApplicationContext());

    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }
}
