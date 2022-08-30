package com.example.colearn.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.colearn.R;
import com.example.colearn.components.Habit;
import com.example.colearn.databinding.ActivityAddEventBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class AddEvent extends AppCompatActivity implements View.OnClickListener {
    public static Activity activity;
    private static Habit newHabit;
    private ActivityAddEventBinding binding;

    public static Habit getNewHabit() {
        return newHabit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();

        setContentView(binding.getRoot());

        newHabit = new Habit();
        activity = this;


        binding.reading.setOnClickListener(this::onClick);
        binding.doHomework.setOnClickListener(this::onClick);
        binding.onlineClass.setOnClickListener(this::onClick);
        binding.singing.setOnClickListener(this::onClick);
        binding.painting.setOnClickListener(this::onClick);
        binding.dancing.setOnClickListener(this::onClick);
        binding.playing.setOnClickListener(this::onClick);
        binding.writing.setOnClickListener(this::onClick);
        binding.swimming.setOnClickListener(this::onClick);
        binding.riding.setOnClickListener(this::onClick);
        binding.basketball.setOnClickListener(this::onClick);
        binding.badminton.setOnClickListener(this::onClick);
        binding.tennis.setOnClickListener(this::onClick);
        binding.football.setOnClickListener(this::onClick);
        binding.jumping.setOnClickListener(this::onClick);
        binding.running.setOnClickListener(this::onClick);
        binding.addNew.setOnClickListener(this::onClick);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String habitType = "";
        switch (v.getId()) {
            case R.id.do_homework:
                habitType = "写作业";
                break;
            case R.id.reading:
                habitType = "读书";
                break;
            case R.id.online_class:
                habitType = "上网课";
                break;
            case R.id.singing:
                habitType = "歌唱";
                break;
            case R.id.painting:
                habitType = "画画";
                break;
            case R.id.dancing:
                habitType = "跳舞";
                break;
            case R.id.playing:
                habitType = "练琴";
                break;
            case R.id.writing:
                habitType = "写字";
                break;
            case R.id.swimming:
                habitType = "游泳";
                break;
            case R.id.riding:
                habitType = "骑车";
                break;
            case R.id.basketball:
                habitType = "篮球";
                break;
            case R.id.badminton:
                habitType = "羽毛球";
                break;
            case R.id.tennis:
                habitType = "乒乓球";
                break;
            case R.id.football:
                habitType = "足球";
                break;
            case R.id.jumping:
                habitType = "跳绳";
                break;
            case R.id.running:
                habitType = "跑步";
                break;
            case R.id.add_new:
                break;
        }
        Intent intent = new Intent();
        intent.setClass(this, AddNewEvent.class);
        newHabit.setHabitType(habitType);
        newHabit.setHabitIcon(((CardView) v).getCardBackgroundColor().getDefaultColor());
        startActivity(intent);
    }
}