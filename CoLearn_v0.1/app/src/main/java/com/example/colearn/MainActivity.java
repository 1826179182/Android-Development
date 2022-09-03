package com.example.colearn;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.colearn.entity.TabEntity;
import com.example.colearn.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.kongzue.dialogx.DialogX;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public final static String baseUrl = "http://47.104.134.68:8989/";


    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private CommonTabLayout mTabLayout;
    private String[] mTitles = {"主页", "图表", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DialogX.init(this);

        for (String title : mTitles) {
            if ("主页".equals(title)) {
                mTabEntities.add(new TabEntity(title, R.mipmap.home_selected, R.mipmap.home_unselected));
                mFragments.add(new Home());
            } else if ("图表".equals(title)) {
                mTabEntities.add(new TabEntity(title, R.mipmap.chart_selected, R.mipmap.chart_unselected));
                mFragments.add(new Chart());
            } else if ("我的".equals(title)) {
                mTabEntities.add(new TabEntity(title, R.mipmap.boy_selected, R.mipmap.boy_unselected));
                mFragments.add(new Me());
            }
        }

        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl);
        mTabLayout.setTabData(mTabEntities, this, R.id.flContent, mFragments);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    ImmersionBar.with(MainActivity.this)
                            .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                            .statusBarDarkFont(true, 0f)
                            .statusBarColor(R.color.title_bg_sky_blue)
                            .init();
                    Home.changeWallpaper();
                } else {
                    ImmersionBar.with(MainActivity.this)
                            .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                            .statusBarDarkFont(true, 0f)
                            .statusBarColor(R.color.white)
                            .init();
                }
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    ImmersionBar.with(MainActivity.this)
                            .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                            .statusBarDarkFont(true, 0f)
                            .statusBarColor(R.color.home_title)
                            .init();
                    Home.changeWallpaper();
                } else {
                    ImmersionBar.with(MainActivity.this)
                            .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                            .statusBarDarkFont(true, 0f)
                            .statusBarColor(R.color.white)
                            .init();
                }
            }
        });

    }


}