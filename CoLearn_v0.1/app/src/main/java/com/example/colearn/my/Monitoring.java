package com.example.colearn.my;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.example.colearn.R;
import com.example.colearn.databinding.ActivityMonitoringBinding;
import com.example.colearn.utils.MyPrepareView;
import com.gyf.immersionbar.ImmersionBar;

import retrofit2.http.Url;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

public class Monitoring extends AppCompatActivity {

    private static ActivityMonitoringBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonitoringBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
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
}