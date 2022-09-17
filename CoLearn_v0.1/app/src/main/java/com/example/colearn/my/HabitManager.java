package com.example.colearn.my;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.d.lib.slidelayout.SlideLayout;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.pojo.Habit;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.ButtonClickUtils;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xuexiang.xui.widget.imageview.IconImageView;
import com.xuexiang.xui.widget.layout.XUIButton;
import com.xuexiang.xui.widget.popupwindow.ViewTooltip;

import java.util.List;

public class HabitManager extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "Home";
    private static List<Habit> habitManageList;
    private static HabitManagerAdapter habitManagerAdapter;
    private static Habit habit;
    private RecyclerView mRecyclerView;
    private ImageView question;
    private TitleBar titleBar;

    public static List<Habit> getHabitManageList() {
        return habitManageList;
    }

    public static HabitManagerAdapter getHabitManagerAdapter() {
        return habitManagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_manager);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        init();
    }

    private void init() {
        habitManageList = Home.getAllTodoList();
        question = findViewById(R.id.question);

        titleBar = findViewById(R.id.title_bar);
        mRecyclerView = findViewById(R.id.habit_manager_listview);

        habitManagerAdapter = new HabitManagerAdapter();
        mRecyclerView.setAdapter(habitManagerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        question.setOnTouchListener(this::onTouch);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewTooltip
                .on(v)
                .color(Color.rgb(193, 239, 255))
                .position(ViewTooltip.Position.BOTTOM)
                .text("右滑目标项，即可进行管理哦！")
                .textColor(R.color.black)
                .clickToHide(true)
                .autoHide(true, 1500)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .show();
        return false;
    }

    class HabitManagerAdapter extends RecyclerView.Adapter {
        private SlideLayout slideLayout;
        private CardView icon;
        private IconImageView res;
        private TextView habitName;
        private XUIButton edit;
        private XUIButton delete;


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_manage_listview, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            habit = (Habit) habitManageList.get(position);
            System.out.println(habit);
            icon.setCardBackgroundColor(habit.getHabitIcon());
            res.setImageResource(habit.getHabitIconRes());
            habitName.setText(habit.getHabitName());

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonClickUtils.isFastClick()) { return; }

                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), EditHabit.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    MyViewHolder myViewHolder = (MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
                    slideLayout = myViewHolder.itemView.findViewById(R.id.sl_slide);
                    slideLayout.close();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonClickUtils.isFastClick()) { return; }

                    MyViewHolder myViewHolder = (MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
                    slideLayout = myViewHolder.itemView.findViewById(R.id.sl_slide);
                    slideLayout.close();
                    habitManageList.remove(position);
                    SPUtils.putString( "todoList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                            , JSON.toJSONString(habitManageList), HabitManager.this);
                    habitManagerAdapter.notifyDataSetChanged();
                    Home.getTodoAdapter().notifyDataSetChanged();
                    Home.updateAllTodoList(Home.selectDate.getMonthOfYear(),Home.selectDate);

                }
            });
            slideLayout.close();
        }

        @Override
        public int getItemCount() {
            return habitManageList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                slideLayout = itemView.findViewById(R.id.sl_slide);
                icon = itemView.findViewById(R.id.habit_icon);
                res = itemView.findViewById(R.id.habit_icon_res);
                habitName = itemView.findViewById(R.id.habit);
                edit = itemView.findViewById(R.id.edit_habit);
                delete = itemView.findViewById(R.id.delete_habit);
            }
        }
    }
}