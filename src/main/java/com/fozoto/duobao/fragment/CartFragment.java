package com.fozoto.duobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fozoto.duobao.R;
import com.fozoto.duobao.activity.MainActivity;
import com.fozoto.duobao.utils.LogUtil;

/**
 * Created by qingyan on 16-8-8.
 */

public class CartFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "CartFragment";

    private Button goDuobao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG, "进入了onCreateView");
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        goDuobao = (Button) view.findViewById(R.id.goDuobao);
        goDuobao.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "触发了onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG, "触发了onHiddenChanged");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goDuobao:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment2Fragment(new MainActivity.Fragment2Fragment() {
                    @Override
                    public void gotoFragment(ViewPager viewPager) {
                        //fragment传递数据
                        // mainActivity.setFragmentArgu(string);
                        viewPager.setCurrentItem(0);
                    }
                });
                mainActivity.forSkip();
                break;
            default:
                break;
        }
    }
}
