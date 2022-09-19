package com.example.colearn;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.adapter.TodoListAdapter;
import com.example.colearn.databinding.HomeBinding;
import com.example.colearn.home.AddEvent;
import com.example.colearn.home.ChangeWallpaper;
import com.example.colearn.home.CheckIn;
import com.example.colearn.home.Planting;
import com.example.colearn.my.Monitoring;
import com.example.colearn.pojo.CheckInRecord;
import com.example.colearn.pojo.Habit;
import com.example.colearn.pojo.Plant;
import com.example.colearn.pojo.User;
import com.example.colearn.pojo.WallPaper;
import com.example.colearn.utils.ButtonClickUtils;
import com.example.colearn.utils.MenuHelper;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.necer.calendar.BaseCalendar;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import me.samlss.timomenu.TimoMenu;
import me.samlss.timomenu.animation.FlipItemAnimation;
import me.samlss.timomenu.interfaces.OnTimoItemClickListener;
import me.samlss.timomenu.interfaces.TimoMenuListener;
import me.samlss.timomenu.view.TimoItemView;


public class Home extends androidx.fragment.app.Fragment implements View.OnClickListener {
    private static final String TAG = "Home";

    public static LocalDate selectDate;

    private static WallPaper wallPaper;
    private static List<CheckInRecord> checkInRecords = new ArrayList<>();
    private static TodoListAdapter todoAdapter;
    private static List<Habit> AllTodoList = new ArrayList<>();
    private static List<Plant> plants = new ArrayList<>();

    public static List<Plant> getPlants() {
        return plants;
    }

    public static void setPlants(List<Plant> plants) {
        Home.plants = plants;
    }

    private static HomeBinding binding;
    private static FragmentActivity activity;
    private ListView list_1;
    private List<Habit> todoList = new ArrayList<>();
    private TimoMenu mTimoMenu;

    public static WallPaper getWallPaper() {
        return wallPaper;
    }

    public static void setWallPaper(WallPaper wallPaper) {
        Home.wallPaper = wallPaper;
    }

    public static List<CheckInRecord> getCheckInRecords() {
        return checkInRecords;
    }

    public static List<Habit> getAllTodoList() {
        return AllTodoList;
    }

    public static TodoListAdapter getTodoAdapter() {
        return todoAdapter;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onClick(View v) {

        if (ButtonClickUtils.isFastClick()) { return; }

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.up_and_down:
                if (binding.expandableLayout.isExpanded()) {
                    binding.expandableLayout.collapse();
                    binding.upAndDown.setImageResource(R.mipmap.down);
                } else {
                    binding.expandableLayout.expand();
                    binding.upAndDown.setImageResource(R.mipmap.up);
                }
                break;
            case R.id.back_today:
                binding.weekCalendar.jumpDate(LocalDate.now().toString());
                todoAdapter.notifyDataSetChanged();
                break;
            case R.id.add_event:
                intent.setClass(getContext(), AddEvent.class);
                startActivity(intent);
                break;
            case R.id.clock_in:
                intent.setClass(getContext(), CheckIn.class);
                startActivity(intent);
                break;
            case R.id.planting:
                intent.setClass(getContext(), Planting.class);
                startActivity(intent);
                break;
            case R.id.wallpaper:
                intent.setClass(getContext(), ChangeWallpaper.class);
                startActivity(intent);
                break;
            case R.id.more_func:
                mTimoMenu.show();
                break;
            case R.id.camera:
                intent.setClass(getContext(), Monitoring.class);
                startActivity(intent);
                break;
        }
    }

    private void init(View view) {
        activity = getActivity();
        String wallpaperStr = SPUtils.getString("wallpaper", null, getContext());
        if (wallpaperStr != null) {
            wallPaper = JSONArray.parseObject(wallpaperStr, WallPaper.class);
        } else {
            wallPaper = new WallPaper(R.mipmap.bg_sunflower, "向日葵", "deep_sky_blue");
        }
        changeWallpaper();

        System.out.println(User.getUser() == null ? "" : User.getUser().getAccount());
        String historyCheckInStr = SPUtils.getString("HistoryCheckIn".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                , null, getContext());
        Log.d(TAG, "init: " + historyCheckInStr);
        if (historyCheckInStr != null) {
            List<CheckInRecord> temp = JSONObject.parseArray(historyCheckInStr, CheckInRecord.class);
            for (CheckInRecord checkInRecord : temp) {
                checkInRecords.add(checkInRecord);
            }
        }

        list_1 = view.findViewById(R.id.todolist);
        todoAdapter = new TodoListAdapter(getContext(), R.layout.todo_listview, todoList);
        list_1.setAdapter(todoAdapter);

        binding.addEvent.setOnClickListener(this::onClick);
        binding.clockIn.setOnClickListener(this::onClick);
        binding.planting.setOnClickListener(this::onClick);

        binding.wallpaper.setOnClickListener(this::onClick);
        binding.backToday.setOnClickListener(this::onClick);
        binding.upAndDown.setOnClickListener(this::onClick);
        binding.moreFunc.setOnClickListener(this::onClick);
        binding.camera.setOnClickListener(this::onClick);
        binding.weekCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                selectDate = localDate;
                switch (month) {
                    case 1:
                        binding.month.setText("一月");
                        break;
                    case 2:
                        binding.month.setText("二月");
                        break;
                    case 3:
                        binding.month.setText("三月");
                        break;
                    case 4:
                        binding.month.setText("四月");
                        break;
                    case 5:
                        binding.month.setText("五月");
                        break;
                    case 6:
                        binding.month.setText("六月");
                        break;
                    case 7:
                        binding.month.setText("七月");
                        break;
                    case 8:
                        binding.month.setText("八月");
                        break;
                    case 9:
                        binding.month.setText("九月");
                        break;
                    case 10:
                        binding.month.setText("十月");
                        break;
                    case 11:
                        binding.month.setText("十一月");
                        binding.month.setTextSize(15);
                        break;
                    case 12:
                        binding.month.setText("十二月");
                        binding.month.setTextSize(15);
                        break;
                }
                if (!LocalDate.now().equals(localDate)) {
                    binding.backToday.setExpanded(true);
                } else {
                    binding.backToday.setExpanded(false);
                }
                updateAllTodoList(month, localDate);
            }

        });

        int itemViewWidth = (getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth() - 40) / 5;
        mTimoMenu = new TimoMenu.Builder(getActivity())
                .setGravity(Gravity.CENTER)
                .setTimoMenuListener(new TimoMenuListener() {
                    @Override
                    public void onShow() {
                    }

                    @Override
                    public void onDismiss() {

                    }
                })
                .setTimoItemClickListener(new OnTimoItemClickListener() {
                    @Override
                    public void onItemClick(int row, int index, TimoItemView view) {
                        Intent intent = new Intent();
                        switch (index) {
                            case 0:
                                intent.setClass(getContext(), AddEvent.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent.setClass(getContext(), CheckIn.class);
                                startActivity(intent);
                                break;
                            case 2:
                                intent.setClass(getContext(), Planting.class);
                                startActivity(intent);
                                break;
                            case 3:
                                intent.setClass(getContext(), Monitoring.class);
                                startActivity(intent);
                                break;
                        }
                        mTimoMenu.dismiss();
                    }
                })
                .setMenuMargin(new Rect(100, 20, 100, 20))
                .setMenuPadding(new Rect(25, 10, 25, 10))
                .addRow(FlipItemAnimation.create(), MenuHelper.getTopList(itemViewWidth))
                .build();
    }

    public static void setCheckInRecords(List<CheckInRecord> checkInRecords) {
        Home.checkInRecords = checkInRecords;
    }

    public static void setAllTodoList(List<Habit> allTodoList) {
        AllTodoList = allTodoList;
    }

    public static void updateAllTodoList(int month, LocalDate localDate) {
        String todoStr = SPUtils.getString("todoList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                , null, todoAdapter.getContext());
        todoAdapter.getTodoList().removeAll(todoAdapter.getTodoList());
        if (todoStr != null) {
            Log.d(TAG, "init: " + JSONArray.parseArray(todoStr));
            AllTodoList = JSONObject.parseArray(todoStr, Habit.class);
            Log.d(TAG, "init: allList" + AllTodoList);
            for (Habit habit : AllTodoList) {
                Log.d(TAG, "updateAllTodoList: " + habit.getTodoDate());
                addHabit(habit,month,localDate);
                todoAdapter.notifyDataSetChanged();
            }
            Log.d(TAG, "init: " + todoAdapter.getTodoList());

        }
    }

    public static void changeWallpaper() {
        binding.wallpaper.setBackgroundResource(wallPaper.getWallpaper());
        switch (wallPaper.getTitleBarColor()) {
            case "sky_blue":
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                        .statusBarDarkFont(true, 0f)
                        .statusBarColor(R.color.title_bg_sky_blue)
                        .init();
                break;
            case "rain_green":
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                        .statusBarDarkFont(true, 0f)
                        .statusBarColor(R.color.title_bg_rain_green)
                        .init();
                break;
            case "deep_blue":
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                        .statusBarDarkFont(true, 0f)
                        .statusBarColor(R.color.title_bg_deep_blue)
                        .init();
                break;
            case "deep_sky_blue":
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                        .statusBarDarkFont(true, 0f)
                        .statusBarColor(R.color.title_bg_deep_sky_blue)
                        .init();
                break;
            case "water_blue":
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                        .statusBarDarkFont(true, 0f)
                        .statusBarColor(R.color.title_bg_water_blue)
                        .init();
                break;
        }
        Message msg = new Message();
    }

    public static void addHabit(Habit habit, int month, LocalDate selectDate){
        if(!selectDate.isBefore(LocalDate.now())){
            if (habit.getTodoDate().equals("无") || habit.getFrequency().equals("每天")) {
                if (!habit.getFinishTime().split(" ")[0].equals(selectDate.toString())){
                    todoAdapter.add(habit);
                }
            } else if(habit.getFrequency().equals("每周") && habit.getTodoDate().split(" ")[1].equals(changeDayOfWeek(selectDate.getDayOfWeek()))){
                if (!habit.getFinishTime().split(" ")[0].equals(selectDate.toString())){
                    todoAdapter.add(habit);
                }
            } else if(habit.getFrequency().equals("每月") && Integer.parseInt(habit.getTodoDate().split(" ")[0].split("-")[1])==(selectDate.getDayOfMonth())){
                if (!habit.getFinishTime().split(" ")[0].equals(selectDate.toString())){
                    todoAdapter.add(habit);
                }
            } else {
                if (Integer.parseInt(habit.getTodoDate().split(" ")[0].split("-")[0]) == month
                        && Integer.parseInt(habit.getTodoDate().split(" ")[0].split("-")[1]) == selectDate.getDayOfMonth()) {
                    todoAdapter.add(habit);
                }
            }
        }
    }

    private static String changeDayOfWeek(int day){
        switch (day){
            case 1: return "周一";
            case 2: return "周二";
            case 3: return "周三";
            case 4: return "周四";
            case 5: return "周五";
            case 6: return "周六";
            case 7: return "周日";
        }
        return "";
    }
}
