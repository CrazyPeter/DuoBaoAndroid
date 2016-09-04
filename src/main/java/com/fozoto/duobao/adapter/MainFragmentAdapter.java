package com.fozoto.duobao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.fozoto.duobao.activity.MainActivity;
import com.fozoto.duobao.fragment.CartFragment;
import com.fozoto.duobao.fragment.FindFragment;
import com.fozoto.duobao.fragment.HomeFragment;
import com.fozoto.duobao.fragment.LoginFragment;
import com.fozoto.duobao.fragment.UserFragment;
import com.fozoto.duobao.fragment.WinFragment;
import com.fozoto.duobao.utils.LogUtil;

/**
 * 底部导航的FragmentAdapter
 * Created by qingyan on 16-8-8.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 5;
    Fragment homeFragment = new HomeFragment();
    Fragment winFragment = new WinFragment();
    Fragment findFragment = new FindFragment();
    Fragment cartFragment = new CartFragment();
    Fragment userFragment = new UserFragment();
    Fragment loginFragment = new LoginFragment();

    private FragmentManager fm;

    Fragment[] fragments = {homeFragment, winFragment, findFragment, cartFragment, loginFragment};
    boolean[] fragmentsUpdateFlag = { false, false, false, false, false };

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MainActivity.TAB_HOME:
                return homeFragment;
            case MainActivity.TAB_WIN:
                return winFragment;
            case MainActivity.TAB_FIND:
                return findFragment;
            case MainActivity.TAB_CART:
                return cartFragment;
            case MainActivity.TAB_USER:
                return loginFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override

    public Object instantiateItem(ViewGroup container,int position) {

        //得到缓存的fragment
        Fragment fragment = (Fragment)super.instantiateItem(container,
                position);

//        //得到tag
//        String fragmentTag = fragment.getTag();
//
//        if (fragmentsUpdateFlag[position %fragmentsUpdateFlag.length]) {
//
//            //如果这个fragment需要更新
//            FragmentTransaction ft =fm.beginTransaction();
//
//            //移除旧的fragment
//            ft.remove(fragment);
//
//            //换成新的fragment
//
//            fragment =fragments[position %fragments.length];
//
//            //添加新fragment时必须用前面获得的tag
//            ft.add(container.getId(), fragment, fragmentTag);
//
//            ft.attach(fragment);
//
//            ft.commit();
//
//            //复位更新标志
//            fragmentsUpdateFlag[position %fragmentsUpdateFlag.length] =false;
//
//        }
        return fragment;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtil.d("MainFragmentAdapter", "destroyItem ::" + position);
        LogUtil.d("MainFragmentAdapter", object.getClass().getName());
        LogUtil.d("MainFragmentAdapter", container.getClass().getName());
        FragmentTransaction ft =fm.beginTransaction();
        ft.remove((Fragment)object);
        ft.commit();
    }
}
