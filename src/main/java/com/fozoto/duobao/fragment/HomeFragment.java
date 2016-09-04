package com.fozoto.duobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fozoto.duobao.R;
import com.fozoto.duobao.adapter.MyAdapter;
import com.fozoto.duobao.adapter.MyStaticPageAdapter;
import com.fozoto.duobao.app.DuobaoApplication;
import com.fozoto.duobao.bean.Ad;
import com.fozoto.duobao.bean.AdJson;
import com.fozoto.duobao.bean.Home;
import com.fozoto.duobao.bean.HomeJson;
import com.fozoto.duobao.control.UIController;
import com.fozoto.duobao.utils.JsonUtils;
import com.fozoto.duobao.utils.LogUtil;
import com.fozoto.duobao.view.MyGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingyan on 16-8-3.
 */

public class HomeFragment extends Fragment implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener, TextView.OnClickListener {

    // 广告
    private RollPagerView adViewPager;
    private MyStaticPageAdapter adPagerAdapter;
    private List<String> adImgs=new ArrayList<>();
    private String adUrl = "http://duobao.fozoto.com/android/show/ad";

    // 中间导航
    private TextView indexKind, indexTen, indexShare, indexQa;

    // 商品
    private int page = 1;
    private int size = 20;
    private String mUrl = "http://duobao.fozoto.com/android/show/home?page=+" + page + "&size=" + size;
    private final static String TAG = "HomeFragment";
    private MyGridView homeGrid;
    private PullToRefreshScrollView mPullRefreshScrollView;
    private LinearLayout layout;
    private MyAdapter<Home> homeAdapter;
    private List<Home> homes = new ArrayList<>();   // 商品数据

    // 网络不好,再次刷新时,广告也刷新,平常不刷新广告
    private boolean netWeak = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "进入onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG, "进入onCreateView");
        final View view = inflater.inflate(R.layout.frag_home, container, false);

        initView(view);

        homeAdapter = new MyAdapter<Home>(homes, R.layout.home_goods_info) {
            @Override
            public void bindView(ViewHolder holder, Home obj) {
                holder.setText(R.id.goods_intro, obj.getIntro());
                if (!TextUtils.isEmpty(obj.getTrait())) {
                    holder.setImageNet(R.id.goods_trait, obj.getTrait());
                }
                holder.setImageNet(R.id.goods_image, obj.getImage());
                holder.setText(R.id.goods_progress, (int)obj.getDegree() + "%");
                holder.setOnClickListener(R.id.add_cart, HomeFragment.this);
            }
        };

        homeGrid.setAdapter(homeAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        // 加载数据
//        loadData(getmUrl());
//        loadAdData(adUrl);
    }

    private void initView(View view) {
        layout = (LinearLayout) view.findViewById(R.id.layout);
        //设置当前焦点，防止打开Activity出现在GridView位置问题
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.content_scroll);
        // 刷新label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        // 上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshScrollView.setOnRefreshListener(this);

        homeGrid = (MyGridView) view.findViewById(R.id.home_grid);
        homeGrid.setOnItemClickListener(this);

        // 广告
        adViewPager = (RollPagerView) view.findViewById(R.id.adViewPager);
        adViewPager.setAdapter(adPagerAdapter = new MyStaticPageAdapter(getActivity().getApplicationContext()));
        adViewPager.setPlayDelay(3000);
        adViewPager.setHintPadding(2,0,2,10);
        adViewPager.setHintView(new IconHintView(getActivity().getApplicationContext(),R.mipmap.ic_dot_selected,R.mipmap.ic_dot_normal, 12));

        // 中间导航
        indexKind = (TextView) view.findViewById(R.id.index_kind);
        indexKind.setOnClickListener(this);
        indexTen = (TextView) view.findViewById(R.id.index_ten);
        indexTen.setOnClickListener(this);
        indexShare = (TextView) view.findViewById(R.id.index_share);
        indexShare.setOnClickListener(this);
        indexQa = (TextView) view.findViewById(R.id.index_qa);
        indexQa.setOnClickListener(this);
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(int page, int size) {
        this.mUrl = "http://duobao.fozoto.com/android/show/home?page=+" + page + "&size=" + size;
    }

    private void loadData(String url) {
        JsonUtils.parseJsonString(url, new JsonUtils.JsonStringCallBack() {
            @Override
            public void jsonStringCallBack(String jsonString) {
                HomeJson homeJson = JSON.parseObject(jsonString, HomeJson.class);
                LogUtil.d(TAG, "有几个数据:"+homes.size());
                if (homeJson.getStatus()) {
                    List<Home> homeList = homeJson.getHomes();
                    for (Home home:homeList) {
                        homes.add(home);
                    }
                    LogUtil.d(TAG, "有几个数据:"+homes.size());
                    homeAdapter.notifyDataSetChanged();
                    // Call onRefreshComplete when the list has been refreshed.
                    mPullRefreshScrollView.onRefreshComplete();
                } else {
                    mPullRefreshScrollView.onRefreshComplete();
                    Toast.makeText(getActivity(), "没有更多商品了", Toast.LENGTH_SHORT).show();
                }
            }
        }, new JsonUtils.RequestTimeOutCallBack() {
            @Override
            public void timeOutCallBack(String timeOutString) {
                mPullRefreshScrollView.onRefreshComplete();
                Toast.makeText(getActivity(), "网络错误,请稍后重试", Toast.LENGTH_SHORT).show();
                netWeak = true;
                // 没有网络,提示商品数据有变化
                // homeAdapter.notifyDataSetChanged();
            }
        }, DuobaoApplication.getHttpQueue());
    }

    private void loadAdData(String adUrl) {
        adImgs.clear();
        adPagerAdapter.notifyDataSetChanged();
        JsonUtils.parseJsonString(adUrl, new JsonUtils.JsonStringCallBack() {
            @Override
            public void jsonStringCallBack(String jsonString) {
                AdJson adJson = JSON.parseObject(jsonString, AdJson.class);
                if (adJson.getStatus()) {
                    List<Ad> ads = adJson.getAds();
                    if (ads !=null && ads.size()>0) {
                        for (int i = 0; i < ads.size(); i++) {
                            adImgs.add(ads.get(i).getImage());
                        }
                        if (adImgs!=null && adImgs.size()>0 && adPagerAdapter!=null) {
                            adPagerAdapter.setImgs(adImgs);
                            LogUtil.d(TAG, adImgs.toString());
                        }
                    }
                } else {
                    mPullRefreshScrollView.onRefreshComplete();
                    LogUtil.e(TAG, "广告不存在");
                    Toast.makeText(getActivity(), "很抱歉，服务器内部出现错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new JsonUtils.RequestTimeOutCallBack() {
            @Override
            public void timeOutCallBack(String timeOutString) {
                mPullRefreshScrollView.onRefreshComplete();
                // Toast.makeText(getActivity(), "网络错误,请稍后重试", Toast.LENGTH_SHORT).show();
                netWeak = true;
                // 没有网络,广告数量为0,提示数据有变化
                adPagerAdapter.notifyDataSetChanged();
            }
        }, DuobaoApplication.getHttpQueue());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        LogUtil.d(TAG, "onPullDownToRefresh");
        homes.clear();
        page = 1;
        setmUrl(page, size);

        loadData(getmUrl());
        // 网络不好时需要刷新广告
        if (netWeak) {
            loadAdData(adUrl);
            // 网络好时,不刷新广告
            netWeak = false;
        }
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        setmUrl(page, size);
        String url = getmUrl();
        loadData(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        setmUrl(page, size);
        loadData(getmUrl());
        loadAdData(adUrl);
        LogUtil.d(TAG, "触发了onResume");
    }

    /**
     * 会触发这个是因为我们的fragment采用的是add,hide,show方式,而不是replace方式
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LogUtil.d(TAG, "onHiddenChanged --> 被隐藏了");
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            LogUtil.d(TAG, "onHiddenChanged --> 被选中了");
        }
    }

    // 这个方法用在ViewPager的Fragment中
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            page = 1;
            LogUtil.d(TAG, "setUserVisibleHint --> 被隐藏了");
        } else {
            //相当于Fragment的onPause
            LogUtil.d(TAG, "setUserVisibleHint --> 被选中了");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(), "position="+position, Toast.LENGTH_SHORT).show();
        Home home = homes.get(position);
        LogUtil.d(TAG, home.toString());
        UIController.showGoodsInfo(getActivity(), home.getGoodsId(), home.getIssueId());
        LogUtil.d(TAG, "商品为"+home.getGoodsId()+"期为:"+home.getIssueId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_cart:
                Toast.makeText(getActivity(), "加入清单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index_kind:
                Toast.makeText(getActivity(), "分类", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index_ten:
                Toast.makeText(getActivity(), "优选商品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index_share:
                Toast.makeText(getActivity(), "晒单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index_qa:
                Toast.makeText(getActivity(), "常见问题", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
