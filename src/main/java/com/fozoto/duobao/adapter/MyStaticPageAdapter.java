package com.fozoto.duobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by qingyan on 16-8-26.
 */

public class MyStaticPageAdapter extends StaticPagerAdapter {

    private List<String> images;
    private Context mContext;

    public MyStaticPageAdapter(Context context) {
        super();
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
    public int getCount() {
        return images==null?0:images.size();
    }
}
