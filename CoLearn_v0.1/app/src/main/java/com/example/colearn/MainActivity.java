package com.example.colearn;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.components.User;
import com.example.colearn.entity.TabEntity;
import com.example.colearn.my.Login;
import com.example.colearn.utils.SPUtils;
import com.example.colearn.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.kongzue.dialogx.DialogX;
import com.luck.picture.lib.utils.SpUtils;

import java.util.ArrayList;
import java.util.logging.Logger;

import xyz.doikki.videoplayer.BuildConfig;
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
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
//播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(xyz.doikki.videoplayer.BuildConfig.DEBUG) //调试的时候请打开日志，方便排错
                /** 软解，支持格式较多，可通过自编译so扩展格式，结合 {@link xyz.doikki.dkplayer.widget.videoview.IjkVideoView} 使用更佳 */
//                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(AndroidMediaPlayerFactory.create()) //不推荐使用，兼容性较差
                /** 硬解，支持格式看手机，请使用CpuInfoActivity检查手机支持的格式，结合 {@link xyz.doikki.dkplayer.widget.videoview.ExoVideoView} 使用更佳 */
                .setPlayerFactory(ExoMediaPlayerFactory.create())
                // 设置自己的渲染view，内部默认TextureView实现
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
                // 根据手机重力感应自动切换横竖屏，默认false
//                .setEnableOrientation(true)
                // 监听系统中其他播放器是否获取音频焦点，实现不与其他播放器同时播放的效果，默认true
//                .setEnableAudioFocus(false)
                // 视频画面缩放模式，默认按视频宽高比居中显示在VideoView中
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                // 适配刘海屏，默认true
//                .setAdaptCutout(false)
                // 移动网络下提示用户会产生流量费用，默认不提示，
                // 如果要提示则设置成false并在控制器中监听STATE_START_ABORT状态，实现相关界面，具体可以参考PrepareView的实现
//                .setPlayOnMobileNetwork(false)
                // 进度管理器，继承ProgressManager，实现自己的管理逻辑
//                .setProgressManager(new ProgressManagerImpl())
                .build());

//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }

        String userStr = SPUtils.getString("user", null, this);
        User.setUser(JSONObject.parseObject(userStr, User.class));
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