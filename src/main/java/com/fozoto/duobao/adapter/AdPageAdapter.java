package com.fozoto.duobao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fozoto.duobao.utils.LogUtil;

import java.util.List;

/**
 * Created by qingyan on 16-8-23.
 */

public class AdPageAdapter extends PagerAdapter {
    private final static String TAG = "AdPageAdapter";
    private List<ImageView> mImagesData;

    public AdPageAdapter(List<ImageView> imagesData) {
        this.mImagesData = imagesData;
    }

    /**
     * 获得页面的总数
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE; // 使得图片可以循环
    }

    /**
     * 获得相应位置上的view
     * container view的容器，其实就是viewpager自身
     * position 相应的位置
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // System.out.println("instantiateItem ::" + position);

        // 给 container 添加一个view
        container.addView(mImagesData.get(position % mImagesData.size()));
        // 返回一个和该view相对的object
        return mImagesData.get(position % mImagesData.size());
    }

    /**
     * 判断 view和object的对应关系
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view == object) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 销毁对应位置上的object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtil.d(TAG, "destroyItem ::" + position);
        container.removeView((View) object);
        object = null;
    }
}
