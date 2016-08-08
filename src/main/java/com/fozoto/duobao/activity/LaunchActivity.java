package com.fozoto.duobao.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fozoto.duobao.R;
import com.fozoto.duobao.control.ActivityController;
import com.fozoto.duobao.fragment.CartFragment;
import com.fozoto.duobao.fragment.FindFragment;
import com.fozoto.duobao.fragment.HomeFragment;
import com.fozoto.duobao.fragment.UserFragment;
import com.fozoto.duobao.fragment.WinFragment;
import com.fozoto.duobao.utils.LogUtil;

public class LaunchActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private final static String TAG = "LaunchActivity";

    // 事务
    private FragmentManager fmManager;
    private FragmentTransaction fmTransaction;

    // 底部导航触发的fragment
    private HomeFragment homeFrag;
    private WinFragment winFrag;
    private FindFragment findFrag;
    private CartFragment cartFrag;
    private UserFragment userFrag;

    // 底部导航组
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fmManager = getFragmentManager();

        // 初始化视图
        initView();

        // 初始化fragment
        initFragment(savedInstanceState);
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.bottom_navigator);
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(this);
        }
    }

    // 解决重影问题 http://blog.csdn.net/fly7632785/article/details/48295741
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            LogUtil.d(TAG, "savedInstanceState != null");
            homeFrag = (HomeFragment) fmManager.findFragmentByTag(HomeFragment.class.getName());
            winFrag = (WinFragment) fmManager.findFragmentByTag(WinFragment.class.getName());
            findFrag = (FindFragment) fmManager.findFragmentByTag(FindFragment.class.getName());
            cartFrag = (CartFragment) fmManager.findFragmentByTag(CartFragment.class.getName());
            userFrag = (UserFragment) fmManager.findFragmentByTag(UserFragment.class.getName());
        } else {
            // 开始事务
            fmTransaction = fmManager.beginTransaction();
            homeFrag = new HomeFragment();
            // 将homeFrag添加到容器中
            fmTransaction.add(R.id.content_container, homeFrag, HomeFragment.class.getName());
            winFrag = new WinFragment();
            // 将winFrag添加到容器中
            fmTransaction.add(R.id.content_container, winFrag, WinFragment.class.getName());
            findFrag = new FindFragment();
            // 将findFrag添加到容器中
            fmTransaction.add(R.id.content_container, findFrag, FindFragment.class.getName());
            cartFrag = new CartFragment();
            // 将cartFrag添加到容器中
            fmTransaction.add(R.id.content_container, cartFrag, CartFragment.class.getName());
            userFrag = new UserFragment();
            // 将findFrag添加到容器中
            fmTransaction.add(R.id.content_container, userFrag, UserFragment.class.getName());
            // 提交事务
            fmTransaction.commit();
        }
    }

    /**
     * 隐藏所有fragment
     * 在调用方提交事务
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFrag!=null) fragmentTransaction.hide(homeFrag);
        if (winFrag!=null) fragmentTransaction.hide(winFrag);
        if (findFrag!=null) fragmentTransaction.hide(findFrag);
        if (cartFrag!=null) fragmentTransaction.hide(cartFrag);
        if (userFrag!=null) fragmentTransaction.hide(userFrag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 底部导航触发
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fmTransaction = fmManager.beginTransaction();
        hideAllFragment(fmTransaction);
        switch (checkedId) {
            case R.id.tab_home:
                LogUtil.d(TAG, "点击了homeBtn");
                if (homeFrag == null) {
                    LogUtil.d(TAG, "进入onCheckedChanged, homeFrag为空, homeFrag初始化");
                    homeFrag= new HomeFragment();
                    // 将homeFrag添加到容器中
                    fmTransaction.add(R.id.content_container, homeFrag, HomeFragment.class.getName());
                } else {
                    LogUtil.d(TAG, "homeFrag不为空");
                    fmTransaction.show(homeFrag);
                }
//                fmTransaction.addToBackStack("homeFrag");
                break;
            case R.id.tab_win:
                LogUtil.d(TAG, "点击了winBtn");
                if (winFrag == null) {
                    LogUtil.d(TAG, "进入onCheckedChanged, winFrag为空, winFrag初始化");
                    winFrag = new WinFragment();
                    // 将winFrag添加到容器中
                    fmTransaction.add(R.id.content_container, winFrag, WinFragment.class.getName());
                } else {
                    fmTransaction.show(winFrag);
                }
//                fmTransaction.addToBackStack("winFrag");
                break;
            case R.id.tab_find:
                LogUtil.d(TAG, "点击了findBtn");
                if (findFrag == null) {
                    LogUtil.d(TAG, "进入onCheckedChanged, findFrag为空, findFrag初始化");
                    findFrag = new FindFragment();
                    // 将findFrag添加到容器中
                    fmTransaction.add(R.id.content_container, findFrag, FindFragment.class.getName());
                } else {
                    fmTransaction.show(findFrag);
                }
//                fmTransaction.addToBackStack("findFrag");
                break;
            case R.id.tab_cart:
                LogUtil.d(TAG, "点击了cartBtn");
                if (cartFrag == null) {
                    LogUtil.d(TAG, "进入onCheckedChanged, cartFrag为空, cartFrag初始化");
                    cartFrag = new CartFragment();
                    // 将cartFrag添加到容器中
                    fmTransaction.add(R.id.content_container, cartFrag, CartFragment.class.getName());
                } else {
                    fmTransaction.show(cartFrag);
                }
//                fmTransaction.addToBackStack("cartFrag");
                break;
            case R.id.tab_user:
                LogUtil.d(TAG, "点击了userBtn");
                if (userFrag == null) {
                    LogUtil.d(TAG, "进入onCheckedChanged, userFrag为空, userFrag初始化");
                    userFrag = new UserFragment();
                    // 将findFrag添加到容器中
                    fmTransaction.add(R.id.content_container, userFrag, UserFragment.class.getName());
                } else {
                    fmTransaction.show(userFrag);
                }
//                fmTransaction.addToBackStack("userFrag");
                break;
            default:
                break;
        }
        fmTransaction.commit();
        LogUtil.d(TAG, "执行了 fmTransaction.commit()");
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis()-exitTime)>2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出1元夺宝", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityController.AppExit(getApplicationContext());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    private static void popAllFragments() {
//        fmManager.popBackStack();
//    }

}
