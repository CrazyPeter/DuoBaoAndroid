package com.fozoto.duobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by qingyan on 16-8-25.
 */

public class MyLoopPageAdapter extends LoopPagerAdapter {
    private List<String> images;
    private Context mContext;

    public MyLoopPageAdapter(RollPagerView viewPager, Context context) {
        super(viewPager);
        this.mContext = context;
    }

    public void setImgs(List<String> images){
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(mContext).load(images.get(position)).into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return images==null?0:images.size();
    }
}
