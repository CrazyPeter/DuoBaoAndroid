package com.fozoto.duobao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.fozoto.duobao.R;
import com.fozoto.duobao.app.DuobaoApplication;
import com.fozoto.duobao.bean.Detail;
import com.fozoto.duobao.bean.DetailJson;
import com.fozoto.duobao.bean.Image;
import com.fozoto.duobao.utils.JsonUtils;
import com.fozoto.duobao.utils.LogUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingyan on 16-8-25.
 */

public class GoodsDetailActivity extends AppCompatActivity implements ImageView.OnClickListener{

    private final static String TAG = "GoodsDetailActivity";

    private LinearLayout detailLayout;
    private int goodsId;
    private String url = "http://duobao.fozoto.com/android/show/detail?goodsId=";
    private List<String> imageAddr = new ArrayList<>();
    private ImageView back, refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

        initViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsId = bundle.getInt("goodsId", 0);

        loadData(url, goodsId);
    }

    private void loadData(String url, int goodsId) {
        String mUrl = url+goodsId;
        imageAddr.clear();
        JsonUtils.parseJsonString(mUrl, new JsonUtils.JsonStringCallBack() {
            @Override
            public void jsonStringCallBack(String jsonString) {
                LogUtil.e(TAG, jsonString);
                DetailJson detailJson;
                Gson gson = new Gson();
                detailJson = gson.fromJson(jsonString, DetailJson.class);

                if (detailJson.getStatus()) {

                    Detail detail = detailJson.getDetail();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    // 商品详情图片
                    List<Image> images = detail.getImages();
                    if (images!=null && images.size()>0) {
                        for (Image image :images) {
                            LogUtil.d(TAG+"有图片：", image.getImage());
                            imageAddr.add(image.getImage());
                            LogUtil.d(TAG, image.getImage());
                        }
                        if (imageAddr.size()>0) {
                            for (String imageUrl : imageAddr) {
                                final ImageView imageView = new ImageView(getApplicationContext());
                                ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        imageView.setImageBitmap(bitmap);
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    }
                                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        imageView.setImageResource(R.mipmap.ic_launcher_one);
                                    }
                                });
                                DuobaoApplication.getHttpQueue().add(imageRequest);
                                detailLayout.addView(imageView, params);
                            }

                            // 图片加载完，再加载重要提醒
                            if (detail.getExplains()!=null) {
                                TextView textView = new TextView(getApplicationContext());
                                textView.setText(detail.getExplains());
                                detailLayout.addView(textView, params);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "很抱歉，服务器内部出现错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new JsonUtils.RequestTimeOutCallBack() {
            @Override
            public void timeOutCallBack(String timeOutString) {
                Toast.makeText(getApplicationContext(), "网络错误,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        }, DuobaoApplication.getHttpQueue());
    }

    private void initViews() {
        detailLayout = (LinearLayout) findViewById(R.id.container_detail);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                GoodsDetailActivity.this.finish();
                break;
            case R.id.refresh:
                detailLayout.removeAllViews();
                loadData(url, goodsId);
                break;
            default:
                break;
        }
    }
}
