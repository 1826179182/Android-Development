package com.example.colearn.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.pojo.Plant;
import com.example.colearn.pojo.Task;
import com.example.colearn.databinding.ActivityPlantingBinding;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.xuexiang.xui.widget.layout.XUIButton;
import com.xuexiang.xui.widget.popupwindow.ViewTooltip;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Planting extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "Planting";

    private static List<Plant> plants;

    private static ActivityPlantingBinding binding;

    public static List<Plant> getPlants() {
        return plants;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantingBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(com.xuexiang.xui.R.color.xui_btn_green_select_color)
                .init();
        init();
    }

    private void init() {
        plants = Home.getPlants();

        binding.startPlant.setOnClickListener(this::onClick);
        binding.myGarden.setOnClickListener(this::onClick);
        binding.question.setOnTouchListener(this::onTouch);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                Intent intent = new Intent();
                intent.setClass(Planting.this, BotanicalIllustrations.class);
                startActivity(intent);
            }

            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
        DialogX.init(getApplicationContext());
        String plantsStr = SPUtils.getString("plants".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                , null, getApplicationContext());
        Log.d(TAG, "plantsStr: " + plantsStr);
        if (plantsStr != null && plants.size() == 0) {
            List<Plant> temp = JSONObject.parseArray(plantsStr, Plant.class);
            for (Plant plant : temp) {
                plants.add(plant);
            }
            Log.d(TAG, "plants: " + Planting.plants);
        }

        binding.plantTask.setText(Task.getTask());
        int result = SPUtils.getInt(LocalDate.now() + "_plant_task_finish?".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                , -1, getApplicationContext());
        if (result == 1) {
            binding.startPlant.setText("任务已完成");
            binding.startPlant.setClickable(false);
        } else if (result == 0) {
            if (Task.hasFinish()) {
                binding.startPlant.setText("任务已完成");
                binding.startPlant.setClickable(false);
                Log.d(TAG, "hasFinish ");
                CustomDialog.show(new OnBindView<CustomDialog>(R.layout.acquire_plant_layout) {
                    @Override
                    public void onBind(final CustomDialog dialog, View v) {
                        ImageView close = v.findViewById(R.id.close);
                        XUIButton gotoGarden = v.findViewById(R.id.goto_my_garden);
                        XUIButton ok = v.findViewById(R.id.btn_ok);

                        LottieAnimationView plant = v.findViewById(R.id.plant);
                        Plant newPlant = plants.get(plants.size() - 1);
                        newPlant.setState(Plant.PLANT);
                        newPlant.setFinishTime(new SimpleDateFormat( "yyyy-MM-dd HH:mm").format(new Date()));
                        plant.setAnimation("plant_" + newPlant.getId() + ".json");
                        SPUtils.putString("plants".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                , JSON.toJSONString(plants), getApplicationContext());
                        SPUtils.putInt(LocalDate.now() + "_plant_task_finish?".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                , 1, getApplicationContext());
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        gotoGarden.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(Planting.this, MyGarden.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setMaskColor(Color.parseColor("#4D000000"));
                        dialog.setCancelable(false);
                    }
                });
            } else {
                Log.d(TAG, "noFinish ");
                binding.startPlant.setText("正在进行中");
                binding.startPlant.setClickable(false);
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_plant:
                CustomDialog.show(new OnBindView<CustomDialog>(R.layout.start_plant_layout) {
                    @Override
                    public void onBind(final CustomDialog dialog, View v) {
                        ImageView close = v.findViewById(R.id.close);

                        int randomPlant = (int) (Math.random() * 9 + 1);
                        Plant newPlant = new Plant(randomPlant);
                        newPlant.setState(Plant.GROWING);
                        plants.add(newPlant);
                        SPUtils.putString("plants".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                , JSON.toJSONString(plants), getApplicationContext());
                        SPUtils.putInt(LocalDate.now() + "_plant_task_finish?".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                , 0, getApplicationContext());
                        binding.startPlant.setText("正在进行中");
                        binding.startPlant.setClickable(false);

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setMaskColor(Color.parseColor("#4D000000"));
                        dialog.setCancelable(false);
                    }
                });
                break;
            case R.id.my_garden:
                Intent intent = new Intent();
                intent.setClass(Planting.this, MyGarden.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewTooltip
                .on(v)
                .color(Color.WHITE)
                .position(ViewTooltip.Position.TOP)
                .text("完成每日任务，即可随机获得一颗种子哦。")
                .textColor(R.color.teal_700)
                .clickToHide(true)
                .autoHide(true, 1500)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .show();
        return false;
    }
}