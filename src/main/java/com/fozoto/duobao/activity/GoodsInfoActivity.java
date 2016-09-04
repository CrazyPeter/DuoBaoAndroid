package com.fozoto.duobao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.fozoto.duobao.R;
import com.fozoto.duobao.adapter.MyAdapter;
import com.fozoto.duobao.adapter.MyLoopPageAdapter;
import com.fozoto.duobao.app.DuobaoApplication;
import com.fozoto.duobao.bean.GoodsInfoJson;
import com.fozoto.duobao.bean.Image;
import com.fozoto.duobao.bean.Info;
import com.fozoto.duobao.bean.Number;
import com.fozoto.duobao.control.UIController;
import com.fozoto.duobao.utils.JsonUtils;
import com.fozoto.duobao.utils.LogUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingyan on 16-8-23.
 */

public class GoodsInfoActivity extends FragmentActivity implements  PullToRefreshBase.OnRefreshListener2<ScrollView>, ListView.OnItemClickListener, View.OnClickListener{
    private final static String TAG = "GoodsInfoActivity";
    private int goodsId;
    private int issueId;
    private final String url="http://duobao.fozoto.com/android/show/info";

    // 导航
    private ImageView back, addToCart;
    private TextView buyNow, addCart;

    private TextView intro, remind, issueTV, total, per, last;
    private ProgressBar degree;
    private ImageView trait;
    private PullToRefreshScrollView pullToRefreshScrollView;
    // 滚动图片
    private RollPagerView shapeViewPager;
    private MyLoopPageAdapter shapePagerAdapter;
    private List<String> imgs=new ArrayList<>();

    // 登录用户对的夺宝号码区域
    private LinearLayout annalsZone;

    private ListView myChoiceListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsId = bundle.getInt("goodsId", 0);
        issueId = bundle.getInt("issueId", 0);

        initViews();

        shapeView();

        getGoodsInfo(url, goodsId, issueId);

    }

    private void initViews() {

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        addToCart = (ImageView) findViewById(R.id.addToCart);
        addToCart.setOnClickListener(this);

        buyNow = (TextView) findViewById(R.id.buyNow);
        buyNow.setOnClickListener(this);
        addCart = (TextView) findViewById(R.id.addCart);
        addCart.setOnClickListener(this);

        intro = (TextView) findViewById(R.id.intro);
        remind = (TextView) findViewById(R.id.remind);
        issueTV = (TextView) findViewById(R.id.issueId);
        total = (TextView) findViewById(R.id.total);
        per = (TextView) findViewById(R.id.per);
        last = (TextView) findViewById(R.id.last);
        degree = (ProgressBar) findViewById(R.id.degree);
        trait = (ImageView) findViewById(R.id.trait);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.content_scroll);
        pullToRefreshScrollView.setOnRefreshListener(this);

        // 选择列表
        myChoiceListView = (ListView) findViewById(R.id.myChoiceListView);
        List<MyChoice> myChoices = new ArrayList<>();
        myChoices.add(new MyChoice("图文详情", "建议在wifi下查看"));
        myChoices.add(new MyChoice("往期揭晓", null));
        myChoices.add(new MyChoice("晒单分享", null));
        MyAdapter<MyChoice> choiceAdapter = new MyAdapter<MyChoice>(myChoices, R.layout.item_list_choice) {
            @Override
            public void bindView(ViewHolder holder, MyChoice obj) {
                holder.setText(R.id.choice, obj.getChoice());
                if (obj.getTip()!=null) {
                    holder.setText(R.id.tip, obj.getTip());
                }
            }
        };
        myChoiceListView.setAdapter(choiceAdapter);
        myChoiceListView.setOnItemClickListener(this);

        annalsZone = (LinearLayout) findViewById(R.id.annalsZone);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                UIController.showGoodsDetail(this, goodsId);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                GoodsInfoActivity.this.finish();
                break;
            case R.id.addToCart:
                Toast.makeText(getApplicationContext(), "加入清单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buyNow:
                Toast.makeText(getApplicationContext(), "立即购买", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addCart:
                Toast.makeText(getApplicationContext(), "加入清单", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class MyChoice {
        String choice;
        String tip;

        public MyChoice (String choice, String tip) {
            this.choice = choice;
            this.tip = tip;
        }

        public String getChoice() {
            return choice;
        }

        public void setChoice(String choice) {
            this.choice = choice;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }

    private void getGoodsInfo(String url, final int goodsId, final int issueId) {
        url +="?goodsId="+goodsId+"&issueId="+issueId;
        imgs.clear();
        JsonUtils.parseJsonString(url, new JsonUtils.JsonStringCallBack() {
            @Override
            public void jsonStringCallBack(String jsonString) {
                LogUtil.e(TAG, jsonString);
                GoodsInfoJson goodsInfoJson;
                Gson gson = new Gson();
                goodsInfoJson = gson.fromJson(jsonString, GoodsInfoJson.class);

                if (goodsInfoJson.getStatus()) {
                    Info info = goodsInfoJson.getInfo();

                    // 商品滚动图片
                    List<Image> images = info.getImages();
                    if (images!=null && images.size()>0) {
                        for (Image image :images) {
                            LogUtil.d(TAG+"有图片：", image.getImage());
                            imgs.add(image.getImage());
                            LogUtil.d(TAG, image.getImage());
                        }
                        if (imgs.size()>0 && shapePagerAdapter!=null) {
                            shapePagerAdapter.setImgs(imgs);
                        }
                    }

                    intro.setText(info.getIntro());
                    remind.setText(info.getRemind());
                    issueTV.setText("期号："+issueId);
                    total.setText("总需"+info.getTotal()+"人次");
                    per.setText("(每份"+info.getPer()+"夺宝币)");
                    last.setText("剩余"+(info.getTotal()-info.getDone()));
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    if (!info.getTrait().equals("")) {
                        ImageRequest imageRequest = new ImageRequest(info.getTrait(), new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                trait.setImageBitmap(bitmap);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                trait.setImageResource(R.mipmap.ic_launcher_one);
                            }
                        });
                        DuobaoApplication.getHttpQueue().add(imageRequest);
                    }
                    // 设置精确到小数点后2位
                    numberFormat.setMaximumFractionDigits(0);
                    int progress = Integer.parseInt(numberFormat.format((float)info.getDone()/(float)info.getTotal()*100));
                    LogUtil.d(TAG, "progress="+progress);

                    // 夺宝号码
                    List<Number> numbers = info.getNumbers();
                    annalsZone.removeAllViews();
                    if (numbers!=null && numbers.size()>0) {
                        int num = numbers.size();
                        TextView joinNumText = new TextView(getApplicationContext());
                        joinNumText.setText("您参与了:  "+num+"人次");
                        joinNumText.setTextSize(14);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        annalsZone.addView(joinNumText, params);
                    } else {
                        TextView joinNumText = new TextView(getApplicationContext());
                        joinNumText.setText("您没有参与本期夺宝哦!");
                        joinNumText.setTextSize(12);
                        joinNumText.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 20, 0, 20);
                        annalsZone.addView(joinNumText, params);
                    }
                } else {
                    pullToRefreshScrollView.onRefreshComplete();
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

    /**
     * 滚动图片初始化
     */
    private void shapeView() {
        shapeViewPager = (RollPagerView) findViewById(R.id.shapeViewPager);
        shapeViewPager.setAdapter(shapePagerAdapter = new MyLoopPageAdapter(shapeViewPager, getApplicationContext()));
        shapeViewPager.setHintView(new IconHintView(this,R.mipmap.ic_dot_selected,R.mipmap.ic_dot_normal, 12));
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        getGoodsInfo(url, goodsId, issueId);
        pullToRefreshScrollView.onRefreshComplete();
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        UIController.showGoodsDetail(this, goodsId);
        pullToRefreshScrollView.onRefreshComplete();
    }
}
