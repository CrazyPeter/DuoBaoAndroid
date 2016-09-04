package com.fozoto.duobao.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fozoto.duobao.activity.GoodsDetailActivity;
import com.fozoto.duobao.activity.GoodsInfoActivity;

/**
 * Created by qingyan on 16-8-24.
 */

public class UIController {
    public static void showGoodsInfo(Activity context,int goodsId, int issueId) {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId", goodsId);
        bundle.putInt("issueId", issueId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showGoodsDetail(Activity context, int goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId", goodsId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
