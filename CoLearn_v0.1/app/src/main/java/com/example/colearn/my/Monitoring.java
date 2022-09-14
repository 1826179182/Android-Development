package com.example.colearn.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.colearn.R;
import com.example.colearn.chart.Achievement;
import com.example.colearn.chart.Daily;
import com.example.colearn.chart.Monthly;
import com.example.colearn.chart.Weekly;
import com.example.colearn.databinding.ActivityMonitoringBinding;
import com.example.colearn.utils.MyPrepareView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.PrepareView;


public class Monitoring extends AppCompatActivity implements OnTabSelectListener , View.OnClickListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private static ActivityMonitoringBinding binding;
    private String[] mTitles = {"行为统计", "图表分析"};
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonitoringBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.black)
                .init();


        binding.player.setUrl("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4"); //设置视频地址

        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        controller.setEnableOrientation(true);
        MyPrepareView prepareView  = new MyPrepareView(this);
        prepareView.setClickStart();
        controller.addControlComponent(prepareView);
        controller.addControlComponent(new CompleteView(this)); //自动完成播放界面
        controller.addControlComponent(new ErrorView(this)); //错误界面
        binding.player.setVideoController(controller); //设置控制器
        binding.player.start(); //开始播放，不调用则不自动播放

        mFragments.add(Daily.getInstance("行为统计"));
        mFragments.add(Weekly.getInstance("图表分析"));
        ViewPager vp = findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        SlidingTabLayout tabLayout = findViewById(R.id.tl);
        tabLayout.setViewPager(vp);
        vp.setCurrentItem(0);


    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.player.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.player.release();
    }


    @Override
    public void onBackPressed() {
        if (!binding.player.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}