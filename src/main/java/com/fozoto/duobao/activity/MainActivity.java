package com.fozoto.duobao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fozoto.duobao.R;
import com.fozoto.duobao.adapter.MainFragmentAdapter;
import com.fozoto.duobao.control.ActivityController;

/**
 * 底部导航的activity
 */
public class MainActivity extends FragmentActivity implements RadioButton.OnClickListener{
    private final static String TAG = "MainActivity";

    private RadioButton homeBtn, winBtn, findBtn, cartBtn, userBtn;

    private ViewPager mainViewPager;

    private  Fragment2Fragment fragment2Fragment;

    //
    public final static int TAB_HOME = 0;
    public final static int TAB_WIN = 1;
    public final static int TAB_FIND = 2;
    public final static int TAB_CART = 3;
    public final static int TAB_USER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityController.addActivity(this);

        // 初始化视图
        initView();

        homeBtn.setChecked(true);

    }

    private void initView() {
        homeBtn = (RadioButton) findViewById(R.id.tab_home);
        homeBtn.setOnClickListener(this);
        winBtn = (RadioButton) findViewById(R.id.tab_win);
        winBtn.setOnClickListener(this);
        findBtn = (RadioButton) findViewById(R.id.tab_find);
        findBtn.setOnClickListener(this);
        cartBtn = (RadioButton) findViewById(R.id.tab_cart);
        cartBtn.setOnClickListener(this);
        userBtn = (RadioButton) findViewById(R.id.tab_user);
        userBtn.setOnClickListener(this);

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mainFragmentAdapter);

        addViewPagerListener();
    }

    private void addViewPagerListener() {
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_HOME:
                        homeBtn.setChecked(true);
                        break;
                    case TAB_WIN:
                        winBtn.setChecked(true);
                        break;
                    case TAB_FIND:
                        findBtn.setChecked(true);
                        break;
                    case TAB_CART:
                        cartBtn.setChecked(true);
                        break;
                    case TAB_USER:
                        userBtn.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                mainViewPager.setCurrentItem(TAB_HOME);
                break;
            case R.id.tab_win:
                mainViewPager.setCurrentItem(TAB_WIN);
                break;
            case R.id.tab_find:
                mainViewPager.setCurrentItem(TAB_FIND);
                break;
            case R.id.tab_cart:
                mainViewPager.setCurrentItem(TAB_CART);
                break;
            case R.id.tab_user:
                mainViewPager.setCurrentItem(TAB_USER);
                break;
            default:
                break;
        }
    }

    public interface Fragment2Fragment{
        public void gotoFragment(ViewPager viewPager);
    }

    public void setFragment2Fragment(Fragment2Fragment fragment2Fragment){
        this.fragment2Fragment = fragment2Fragment;
    }

    public void forSkip(){
        if(fragment2Fragment!=null){
            fragment2Fragment.gotoFragment(mainViewPager);
        }
    }

}
