package com.fozoto.duobao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fozoto.duobao.control.ActivityController;

/**
 * Created by qingyan on 16-7-4.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    /**
     * 获取当前Activity的类名
     * @return
     */
    public String getActivityName() {
        return getClass().getSimpleName();
    }
}
