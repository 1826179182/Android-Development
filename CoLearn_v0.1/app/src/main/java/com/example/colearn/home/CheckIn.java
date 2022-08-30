package com.example.colearn.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.adapter.HasDoneListAdapter;
import com.example.colearn.components.CheckInRecord;
import com.example.colearn.components.Habit;
import com.example.colearn.databinding.ActivityCheckInBinding;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.xuexiang.xui.widget.popupwindow.ViewTooltip;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckIn extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "CheckIn";
    private static HasDoneListAdapter doneAdapter;
    private ListView list_2;
    private List<Habit> doneList = new ArrayList<>();

    private String[] res = {"book.json", "books.json", "planting_tree.json", "walking.json", "water_flowers.json"};
    private List<String> lottieRes = new ArrayList<>(Arrays.asList(res));
    private boolean expressionsSelected = false;

    private List<Habit> todayTodo = new ArrayList<>();

    private ActivityCheckInBinding binding;

    private int expression;

    private LocalDate localDate = LocalDate.now();

    public static HasDoneListAdapter getDoneAdapter() {
        return doneAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckInBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        init();
    }

    protected void init() {
        String hasDoneListStr = SPUtils.getString(LocalDate.now() + "hasDoneList", null, getApplicationContext());
        Log.d(TAG, "hasDoneListStr: " + hasDoneListStr);

        list_2 = findViewById(R.id.done_list);
        doneAdapter = new HasDoneListAdapter(this, R.layout.hasdone_listview, doneList);
        list_2.setAdapter(doneAdapter);
        if (hasDoneListStr != null) {
            List<Habit> temp = JSONObject.parseArray(hasDoneListStr, Habit.class);
            for (Habit doneItem : temp) {
                doneAdapter.add(doneItem);
            }
            Log.d(TAG, "doneListItems: " + HasDoneListAdapter.getDoneListItems());
        }

        LocalDate localDate = LocalDate.now();


        binding.happyExpressions.setOnClickListener(this::onClick);
        binding.unhappyExpressions.setOnClickListener(this::onClick);
        binding.smileExpressions.setOnClickListener(this::onClick);
        binding.confirmBtn.setOnClickListener(this::onClick);

        binding.happyExpressions.setOnTouchListener(this::onTouch);
        binding.unhappyExpressions.setOnTouchListener(this::onTouch);
        binding.smileExpressions.setOnTouchListener(this::onTouch);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                Intent intent = new Intent();
                intent.setClass(CheckIn.this, HistoryCheckIn.class);
                startActivity(intent);
            }

            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });

        if (SPUtils.getBoolean("Check in" + localDate.toString(), false, this) == true) {
            Log.d(TAG, "init: 已打卡");
            binding.confirmBtn.setText("已  打  卡");
            binding.confirmBtn.setBackgroundColor(getResources().getColor(R.color.btn_bg));
            binding.confirmBtn.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.happy_expressions:
                expressionsSelected = true;
                expression = 0;
                binding.happyExpressions.setImageResource(R.mipmap.happy_light);
                binding.unhappyExpressions.setImageResource(R.mipmap.unhappy_dark);
                binding.smileExpressions.setImageResource(R.mipmap.smile_dark);
                break;
            case R.id.unhappy_expressions:
                expressionsSelected = true;
                expression = 1;
                binding.happyExpressions.setImageResource(R.mipmap.happy_dark);
                binding.unhappyExpressions.setImageResource(R.mipmap.unhappy_light);
                binding.smileExpressions.setImageResource(R.mipmap.smile_dark);
                break;
            case R.id.smile_expressions:
                expressionsSelected = true;
                expression = 2;
                binding.happyExpressions.setImageResource(R.mipmap.happy_dark);
                binding.unhappyExpressions.setImageResource(R.mipmap.unhappy_dark);
                binding.smileExpressions.setImageResource(R.mipmap.smile_light);
                break;
            case R.id.confirm_btn:
                String todoStr = SPUtils.getString("todoList", null, this);
                if (todoStr != null) {
                    Log.d(TAG, "init: " + JSONArray.parseArray(todoStr));
                    List<Habit> AllTodoList;
                    AllTodoList = JSONObject.parseArray(todoStr, Habit.class);
                    for (Habit habit : AllTodoList) {
                        Log.d(TAG, "updateAllTodoList: " + habit.getTodoDate());
                        if (habit.getTodoDate().equals("无")) {
                        } else {
                            if (Integer.parseInt(habit.getTodoDate().split(" ")[0].split("-")[0]) == localDate.getMonthValue()
                                    && Integer.parseInt(habit.getTodoDate().split(" ")[0].split("-")[1]) == localDate.getDayOfMonth()) {
                                todayTodo.add(habit);
                            }
                        }
                    }
                    if (todayTodo.size() == 0) {
                        if (expressionsSelected) {
                            CustomDialog.show(new OnBindView<CustomDialog>(R.layout.check_in_result) {
                                @Override
                                public void onBind(final CustomDialog dialog, View v) {
                                    LottieAnimationView bg = v.findViewById(R.id.check_in_result_lottie);
                                    bg.setAnimation(lottieRes.get((int) (Math.random() * lottieRes.size())));
                                    ImageView close = v.findViewById(R.id.close);
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

                            Home.getCheckInRecords().add(new CheckInRecord(
                                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    , LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                                    , HasDoneListAdapter.getDoneListItems().size() + Home.getAllTodoList().size(), HasDoneListAdapter.getDoneListItems().size(), expression));

                            SPUtils.putString("HistoryCheckIn", JSON.toJSONString(Home.getCheckInRecords()), this);

                            binding.confirmBtn.setText("已  打  卡");
                            binding.confirmBtn.setBackgroundColor(getResources().getColor(R.color.btn_bg));
                            binding.confirmBtn.setClickable(false);
                            SPUtils.putBoolean("Check in" + localDate.toString(), true, this);
                        } else {
                            CookieBar.builder(this)
                                    .setTitle("打卡失败")
                                    .setMessage("请先记录一下今日心情哦！")
                                    .setBackgroundColor(com.xuexiang.xui.R.color.xui_default_round_btn_blue_bg)
                                    .show();
                        }
                    } else {
                        CookieBar.builder(this)
                                .setTitle("打卡失败")
                                .setMessage("今天任务还未全部完成，不能打卡哦！")
                                .setBackgroundColor(com.xuexiang.xui.R.color.xui_default_round_btn_blue_bg)
                                .show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.happy_expressions:
                ViewTooltip
                        .on(v)
                        .color(Color.WHITE)
                        .position(ViewTooltip.Position.TOP)
                        .text("今天有点开心呢！")
                        .textColor(R.color.teal_700)
                        .clickToHide(true)
                        .autoHide(true, 1500)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
            case R.id.unhappy_expressions:
                ViewTooltip
                        .on(v)
                        .color(Color.WHITE)
                        .position(ViewTooltip.Position.TOP)
                        .text("今天有点不开心。")
                        .textColor(R.color.teal_700)
                        .clickToHide(true)
                        .autoHide(true, 1500)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
            case R.id.smile_expressions:
                ViewTooltip
                        .on(v)
                        .color(Color.WHITE)
                        .position(ViewTooltip.Position.TOP)
                        .text("没什么特别的感觉，平常的一天。")
                        .textColor(R.color.teal_700)
                        .clickToHide(true)
                        .autoHide(true, 1500)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
        }
        return false;
    }

}