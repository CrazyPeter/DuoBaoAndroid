package com.fozoto.duobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fozoto.duobao.R;
import com.fozoto.duobao.utils.LogUtil;

/**
 * Created by qingyan on 16-8-26.
 */

public class LoginFragment extends Fragment {
    private final static String TAG = "LoginFragment";
    private EditText user, pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG, "进入了onCreateView");
        View view = inflater.inflate(R.layout.frag_login, container, false);
        user = (EditText) view.findViewById(R.id.user);
        pwd = (EditText) view.findViewById(R.id.pwd);
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
}
