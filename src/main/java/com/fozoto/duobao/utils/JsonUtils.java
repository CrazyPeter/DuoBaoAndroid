package com.fozoto.duobao.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qingyan on 16-7-21.
 */

public class JsonUtils {
    private final static String TAG = "JsonUtils";

    /**
     * 解析json字符串回调接口
     */
    public interface JsonStringCallBack {
        public void jsonStringCallBack(String jsonString);
    }

    /**
     * 解析json对象回调接口
     */
    public interface JsonObjectCallBack {
        public void jsonObjectCallBack(JSONObject jsonObject);
    }

    /**
     * 解析json数组回调接口
     */
    public interface JsonArrayCallBack {
        public void jsonArrayCallBack(JSONArray jsonArray);
    }

    /**
     * 请求超时的回调接口
     */
    public interface RequestTimeOutCallBack {
        public void timeOutCallBack(String timeOutString);
    }

    public static void parseJsonString(String jsonPath,
                                final JsonStringCallBack callBack,
                                final RequestTimeOutCallBack timeOutCallBack,
                                RequestQueue queue) {
        StringRequest request = new StringRequest(jsonPath,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        callBack.jsonStringCallBack(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        timeOutCallBack.timeOutCallBack(volleyError.getMessage());
                    }
                }) {



            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // 添加到队列中
        queue.add(request);
    }

    public static void parseJsonObject(String jsonPath,
                                final JsonObjectCallBack callBack,
                                final Map<String, String> map,
                                RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, jsonPath, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        callBack.jsonObjectCallBack(jsonObject);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        queue.add(request);
    }

    public static void parseJsonArray(String jsonPath,
                               final JsonArrayCallBack callBack,
                               RequestQueue queue) {
        JsonArrayRequest request = new JsonArrayRequest(jsonPath,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        callBack.jsonArrayCallBack(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage());
                    }
                });
        // 添加到队列中
        queue.add(request);
    }
}
