package com.fozoto.duobao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.fozoto.duobao.app.DuobaoApplication;
import com.fozoto.duobao.R;
import com.fozoto.duobao.adapter.MyAdapter;
import com.fozoto.duobao.entity.Goods;
import com.fozoto.duobao.entity.HomeGoods;
import com.fozoto.duobao.entity.PageBean;
import com.fozoto.duobao.utils.JsonUtils;
import com.fozoto.duobao.utils.LogUtil;
import com.fozoto.duobao.view.MyGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;

/**
 * Created by qingyan on 16-8-3.
 */

public class HomeFragment extends Fragment  implements
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    private int page=1;
    private int size=20;
    private String mUrl = "http://duobao.fozoto.com/android/goods/list?page=+"+page+"&size="+size;
    private final static String TAG = "HomeFragment";
    private MyGridView homeGrid;
//    private PullToRefreshGridView mPullRefreshGridView;
    private PullToRefreshScrollView mPullRefreshScrollView;
    private LinearLayout layout;
    //private PullToRefreshScrollView scrollView;

    private  MyAdapter<HomeGoods> goodsAdapter;

    private ArrayList<HomeGoods> homeGoodsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "进入onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_home, container, false);

        initView(view);

        goodsAdapter = new MyAdapter<HomeGoods>(homeGoodsList, R.layout.s_goods_info) {
            @Override
            public void bindView(ViewHolder holder, HomeGoods obj) {
                holder.setText(R.id.goods_intro, obj.getIntro());
                if (!TextUtils.isEmpty(obj.getTrait())) {
                    holder.setImageNet(R.id.goods_trait, obj.getTrait());
                } else {
                    LogUtil.e(TAG, "为空");
                }
                holder.setImageNet(R.id.goods_image, obj.getImage());
            }
        };

//        GetData getData = new GetData();
//        getData.execute(url);

        homeGrid.setAdapter(goodsAdapter);

//        mPullRefreshGridView.setAdapter(goodsAdapter);

        LogUtil.d(TAG, "进入onCreateView");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 加载数据
        loadData(getmUrl());
    }

    private void initView (View view) {
        layout = (LinearLayout) view.findViewById(R.id.layout);
        //设置当前焦点，防止打开Activity出现在GridView位置问题
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();
//        mPullRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.home_grid);

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.content_scroll);
        // 刷新label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间" );
        mPullRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel( "下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新" );
        // 上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode. BOTH);
        mPullRefreshScrollView.setOnRefreshListener( this);


        homeGrid = (MyGridView) view.findViewById(R.id.home_grid);

    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(int page, int size) {
        this.mUrl = "http://duobao.fozoto.com/android/goods/list?page=+"+page+"&size="+size;
    }

    //    // 这个方法用在ViewPager的Fragment中
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //相当于Fragment的onResume
//        } else {
//            //相当于Fragment的onPause
//        }
//    }

    private void loadData(String url) {
        JsonUtils.parseJsonString(url, new JsonUtils.JsonStringCallBack() {
            @Override
            public void jsonStringCallBack(String jsonString) {
                if (jsonString.equals("-1")) {
                    LogUtil.e(TAG, "该商品不存在");
                } else {
                    PageBean<Goods> goodsList = JSON.parseObject(jsonString, PageBean.class);

                    LogUtil.d(TAG, goodsList.getQueryResultList().toString());
                    for (Goods goods : JSON.parseArray(goodsList.getQueryResultList().toString(), Goods.class)) {
                        LogUtil.e(TAG, goods.toString());
                        HomeGoods homeGoods = new HomeGoods();
                        homeGoods.setIntro(goods.getIntro());
                        homeGoods.setImage(goods.getImage());
                        if (!TextUtils.isEmpty(goods.getTrait())) {
                            homeGoods.setTrait(goods.getTrait());
                        } else {
                            LogUtil.e(TAG, "为null");
                        }
                        homeGoodsList.add(homeGoods);
                    }
                    goodsAdapter.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }
        }, new JsonUtils.RequestTimeOutCallBack() {
            @Override
            public void timeOutCallBack(String timeOutString) {
                LogUtil.e(TAG, timeOutString);
            }
        }, DuobaoApplication.getHttpQueue());
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        // homeGoodsList.clear();
        LogUtil.d(TAG, "onPullDownToRefresh");
        homeGoodsList.clear();
        page = 1;
        setmUrl(page, size);
        loadData(getmUrl());
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page ++;
        setmUrl(page, size);
        String url = getmUrl();
        loadData(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "触发了onResume");
    }

    /**
     * 会触发这个是因为我们的fragment采用的是add,hide,show方式,而不是replace方式
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LogUtil.d(TAG, "被隐藏了");
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            LogUtil.d(TAG, "被选中了");
        }
    }

}
