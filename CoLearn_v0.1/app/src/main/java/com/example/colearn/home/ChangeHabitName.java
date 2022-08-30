package com.example.colearn.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colearn.R;
import com.example.colearn.components.Habit;
import com.example.colearn.databinding.ActivityChangeHabitNameBinding;
import com.example.colearn.my.EditHabit;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

public class ChangeHabitName extends AppCompatActivity {
    private static final String TAG = "ChangeHabitName";

    private Habit newHabit;

    private String habitName;
    private String msg;

    private ActivityChangeHabitNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeHabitNameBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        msg = (String) getIntent().getExtras().get("activity");

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitName = binding.newHabitName.getEditValue();
                if (!habitName.equals("") && habitName != null) {
                    Intent intent = new Intent();
                    if (msg.equals("edit")) {
                        newHabit = EditHabit.getNewHabit();
                        Log.d(TAG, "edit: " + newHabit);
                        newHabit.setHabitName(habitName);
                        Log.d(TAG, "edit: after" + newHabit);
                        Log.d(TAG, "edit: " + EditHabit.getNewHabit());
                        EditHabit.update();
                    } else {
                        newHabit = AddEvent.getNewHabit();
                        Log.d(TAG, "add: " + newHabit);
                        newHabit.setHabitName(habitName);
                        intent.setClass(ChangeHabitName.this, AddNewEvent.class);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    CookieBar.builder(ChangeHabitName.this)
                            .setTitle("创建失败")
                            .setMessage("请一定要填写目标名字，明确目标哦！")
                            .setBackgroundColor(com.xuexiang.xui.R.color.xui_default_round_btn_blue_bg)
                            .show();
                }

            }
        });
    }
}