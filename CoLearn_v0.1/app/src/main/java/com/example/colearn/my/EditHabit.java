package com.example.colearn.my;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.kongzue.dialogx.interfaces.BaseDialog.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.components.Habit;
import com.example.colearn.databinding.ActivityEditHabitBinding;
import com.example.colearn.home.ChangeHabitIcon;
import com.example.colearn.home.ChangeHabitName;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditHabit extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "EditHabit";


    private static Habit newHabit;
    private static Habit oldHabit;


    private static ActivityEditHabitBinding binding;
    private static int imageRes;
    private static String habitType;
    private int position;

    private String[] freList = {"只提醒一次", "每天", "每周", "每月"};
    private int constellationSelectOption = 0;

    public static Habit getNewHabit() {
        return newHabit;
    }

    public static void update() {
        habitType = newHabit.getHabitType();
        switch (newHabit.getHabitType()) {
            case "读书":
                binding.habitIconRes.setImageResource(R.mipmap.reading);
                imageRes = R.mipmap.reading;
                break;
            case "写作业":
                binding.habitIconRes.setImageResource(R.mipmap.do_homework);
                imageRes = R.mipmap.do_homework;
                break;
            case "上网课":
                binding.habitIconRes.setImageResource(R.mipmap.online_class);
                imageRes = R.mipmap.online_class;
                break;
            case "歌唱":
                binding.habitIconRes.setImageResource(R.mipmap.sing);
                imageRes = R.mipmap.sing;
                break;
            case "画画":
                binding.habitIconRes.setImageResource(R.mipmap.paint);
                imageRes = R.mipmap.paint;
                break;
            case "跳舞":
                binding.habitIconRes.setImageResource(R.mipmap.dancer);
                imageRes = R.mipmap.dancer;
                break;
            case "练琴":
                binding.habitIconRes.setImageResource(R.mipmap.guitar);
                imageRes = R.mipmap.guitar;
                break;
            case "写字":
                binding.habitIconRes.setImageResource(R.mipmap.pencil);
                imageRes = R.mipmap.pencil;
                break;
            case "游泳":
                binding.habitIconRes.setImageResource(R.mipmap.swim);
                imageRes = R.mipmap.swim;
                break;
            case "骑车":
                binding.habitIconRes.setImageResource(R.mipmap.bike);
                imageRes = R.mipmap.bike;
                break;
            case "篮球":
                binding.habitIconRes.setImageResource(R.mipmap.basketball);
                imageRes = R.mipmap.basketball;
                break;
            case "羽毛球":
                binding.habitIconRes.setImageResource(R.mipmap.badminton);
                imageRes = R.mipmap.badminton;
                break;
            case "乒乓球":
                binding.habitIconRes.setImageResource(R.mipmap.tennis);
                imageRes = R.mipmap.tennis;
                break;
            case "足球":
                binding.habitIconRes.setImageResource(R.mipmap.football);
                imageRes = R.mipmap.football;
                break;
            case "跳绳":
                binding.habitIconRes.setImageResource(R.mipmap.skip);
                imageRes = R.mipmap.skip;
                break;
            case "跑步":
                binding.habitIconRes.setImageResource(R.mipmap.running);
                imageRes = R.mipmap.running;
                break;
            case "daily_ball":
                binding.habitIconRes.setImageResource(R.mipmap.daily_ball);
                imageRes = R.mipmap.daily_ball;
                break;
            case "daily_bathing":
                binding.habitIconRes.setImageResource(R.mipmap.daily_bathing);
                imageRes = R.mipmap.daily_bathing;
                break;
            case "daily_book":
                binding.habitIconRes.setImageResource(R.mipmap.daily_book);
                imageRes = R.mipmap.daily_book;
                break;
            case "daily_brushing":
                binding.habitIconRes.setImageResource(R.mipmap.daily_brushing);
                imageRes = R.mipmap.daily_brushing;
                break;
            case "daily_clean":
                binding.habitIconRes.setImageResource(R.mipmap.daily_clean);
                imageRes = R.mipmap.daily_clean;
                break;
            case "daily_cook":
                binding.habitIconRes.setImageResource(R.mipmap.daily_cook);
                imageRes = R.mipmap.daily_cook;
                break;
            case "daily_exercise_one":
                binding.habitIconRes.setImageResource(R.mipmap.daily_exercise_one);
                imageRes = R.mipmap.daily_exercise_one;
                break;
            case "daily_exercise_two":
                binding.habitIconRes.setImageResource(R.mipmap.daily_exercise_two);
                imageRes = R.mipmap.daily_exercise_two;
                break;
            case "daily_reading":
                binding.habitIconRes.setImageResource(R.mipmap.daily_reading);
                imageRes = R.mipmap.daily_reading;
                break;
            case "daily_shopping":
                binding.habitIconRes.setImageResource(R.mipmap.daily_shopping);
                imageRes = R.mipmap.daily_shopping;
                break;
            case "daily_xs":
                binding.habitIconRes.setImageResource(R.mipmap.daily_xs);
                imageRes = R.mipmap.daily_xs;
                break;
            case "drink_c":
                binding.habitIconRes.setImageResource(R.mipmap.drink_c);
                imageRes = R.mipmap.drink_c;
                break;
            case "drink_cf":
                binding.habitIconRes.setImageResource(R.mipmap.drink_cf);
                imageRes = R.mipmap.drink_cf;
                break;
            case "drink_hj":
                binding.habitIconRes.setImageResource(R.mipmap.drink_hj);
                imageRes = R.mipmap.drink_hj;
                break;
            case "drink_kl":
                binding.habitIconRes.setImageResource(R.mipmap.drink_kl);
                imageRes = R.mipmap.drink_kl;
                break;
            case "drink_kqs":
                binding.habitIconRes.setImageResource(R.mipmap.drink_kqs);
                imageRes = R.mipmap.drink_kqs;
                break;
            case "food_bbt":
                binding.habitIconRes.setImageResource(R.mipmap.food_bbt);
                imageRes = R.mipmap.food_bbt;
                break;
            case "food_d":
                binding.habitIconRes.setImageResource(R.mipmap.food_d);
                imageRes = R.mipmap.food_d;
                break;
            case "food_hb":
                binding.habitIconRes.setImageResource(R.mipmap.food_hb);
                imageRes = R.mipmap.food_hb;
                break;
            case "food_jt":
                binding.habitIconRes.setImageResource(R.mipmap.food_jt);
                imageRes = R.mipmap.food_jt;
                break;
            case "food_r":
                binding.habitIconRes.setImageResource(R.mipmap.food_r);
                imageRes = R.mipmap.food_r;
                break;
            case "food_rice":
                binding.habitIconRes.setImageResource(R.mipmap.food_rice);
                imageRes = R.mipmap.food_rice;
                break;
            case "food_x":
                binding.habitIconRes.setImageResource(R.mipmap.food_x);
                imageRes = R.mipmap.food_x;
                break;
            case "food_ym":
                binding.habitIconRes.setImageResource(R.mipmap.food_ym);
                imageRes = R.mipmap.food_ym;
                break;
            case "food_yu":
                binding.habitIconRes.setImageResource(R.mipmap.food_yu);
                imageRes = R.mipmap.food_yu;
                break;
            case "food_zznc":
                binding.habitIconRes.setImageResource(R.mipmap.food_zznc);
                imageRes = R.mipmap.food_zznc;
                break;
            case "fruits_cm":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_cm);
                imageRes = R.mipmap.fruits_cm;
                break;
            case "fruits_nm":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_nm);
                imageRes = R.mipmap.fruits_nm;
                break;
            case "fruits_jz":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_jz);
                imageRes = R.mipmap.fruits_jz;
                break;
            case "fruits_yt":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_yt);
                imageRes = R.mipmap.fruits_yt;
                break;
            case "fruits_pg":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_pg);
                imageRes = R.mipmap.fruits_pg;
                break;
            case "fruits_pt":
                binding.habitIconRes.setImageResource(R.mipmap.fruits_pt);
                imageRes = R.mipmap.fruits_pt;
                break;
            case "other_ballons":
                binding.habitIconRes.setImageResource(R.mipmap.other_ballons);
                imageRes = R.mipmap.other_ballons;
                break;
            case "other_birthday":
                binding.habitIconRes.setImageResource(R.mipmap.other_birthday);
                imageRes = R.mipmap.other_birthday;
                break;
            case "other_evening":
                binding.habitIconRes.setImageResource(R.mipmap.other_evening);
                imageRes = R.mipmap.other_evening;
                break;
            case "other_goal":
                binding.habitIconRes.setImageResource(R.mipmap.other_goal);
                imageRes = R.mipmap.other_goal;
                break;
            case "other_flag":
                binding.habitIconRes.setImageResource(R.mipmap.other_flag);
                imageRes = R.mipmap.other_flag;
                break;
            case "vegetables_bc":
                binding.habitIconRes.setImageResource(R.mipmap.vegetables_bc);
                imageRes = R.mipmap.vegetables_bc;
                break;
            case "vegetables_d":
                binding.habitIconRes.setImageResource(R.mipmap.vegetables_d);
                imageRes = R.mipmap.vegetables_d;
                break;
            case "vegetables_hc":
                binding.habitIconRes.setImageResource(R.mipmap.vegetables_hc);
                imageRes = R.mipmap.vegetables_hc;
                break;
            case "vegetables_lb":
                binding.habitIconRes.setImageResource(R.mipmap.vegetables_lb);
                imageRes = R.mipmap.vegetables_lb;
                break;
            case "vegetables_xhs":
                binding.habitIconRes.setImageResource(R.mipmap.vegetables_xhs);
                imageRes = R.mipmap.vegetables_xhs;
                break;
        }
        newHabit.setHabitIconRes(imageRes);
        binding.habitName.setText(newHabit.getHabitName());
        binding.habitIcon.setCardBackgroundColor(newHabit.getHabitIcon());
        binding.habitFrequency.setText(newHabit.getFrequency());
        binding.habitRemind.setText(newHabit.getRemindTime());
        binding.habitTodoDate.setText(newHabit.getTodoDate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditHabitBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();

        position = getIntent().getIntExtra("position", -1);
        oldHabit = HabitManager.getHabitManageList().get(position);
        newHabit = new Habit(oldHabit.getHabitName()
                , oldHabit.getHabitType()
                , oldHabit.getFinishTime()
                , oldHabit.getHabitIcon()
                , oldHabit.getHabitIconRes()
                , oldHabit.getTodoDate()
                , oldHabit.getFrequency()
                , oldHabit.getRemindTime()
                , oldHabit.TODO);

        if (!newHabit.getHabitType().equals("") && newHabit.getHabitType() != null) {
            habitType = newHabit.getHabitType();
            switch (newHabit.getHabitType()) {
                case "读书":
                    binding.habitIconRes.setImageResource(R.mipmap.reading);
                    imageRes = R.mipmap.reading;
                    break;
                case "写作业":
                    binding.habitIconRes.setImageResource(R.mipmap.do_homework);
                    imageRes = R.mipmap.do_homework;
                    break;
                case "上网课":
                    binding.habitIconRes.setImageResource(R.mipmap.online_class);
                    imageRes = R.mipmap.online_class;
                    break;
                case "歌唱":
                    binding.habitIconRes.setImageResource(R.mipmap.sing);
                    imageRes = R.mipmap.sing;
                    break;
                case "画画":
                    binding.habitIconRes.setImageResource(R.mipmap.paint);
                    imageRes = R.mipmap.paint;
                    break;
                case "跳舞":
                    binding.habitIconRes.setImageResource(R.mipmap.dancer);
                    imageRes = R.mipmap.dancer;
                    break;
                case "练琴":
                    binding.habitIconRes.setImageResource(R.mipmap.guitar);
                    imageRes = R.mipmap.guitar;
                    break;
                case "写字":
                    binding.habitIconRes.setImageResource(R.mipmap.pencil);
                    imageRes = R.mipmap.pencil;
                    break;
                case "游泳":
                    binding.habitIconRes.setImageResource(R.mipmap.swim);
                    imageRes = R.mipmap.swim;
                    break;
                case "骑车":
                    binding.habitIconRes.setImageResource(R.mipmap.bike);
                    imageRes = R.mipmap.bike;
                    break;
                case "篮球":
                    binding.habitIconRes.setImageResource(R.mipmap.basketball);
                    imageRes = R.mipmap.basketball;
                    break;
                case "羽毛球":
                    binding.habitIconRes.setImageResource(R.mipmap.badminton);
                    imageRes = R.mipmap.badminton;
                    break;
                case "乒乓球":
                    binding.habitIconRes.setImageResource(R.mipmap.tennis);
                    imageRes = R.mipmap.tennis;
                    break;
                case "足球":
                    binding.habitIconRes.setImageResource(R.mipmap.football);
                    imageRes = R.mipmap.football;
                    break;
                case "跳绳":
                    binding.habitIconRes.setImageResource(R.mipmap.skip);
                    imageRes = R.mipmap.skip;
                    break;
                case "跑步":
                    binding.habitIconRes.setImageResource(R.mipmap.running);
                    imageRes = R.mipmap.running;
                    break;
                case "daily_ball":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_ball);
                    imageRes = R.mipmap.daily_ball;
                    break;
                case "daily_bathing":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_bathing);
                    imageRes = R.mipmap.daily_bathing;
                    break;
                case "daily_book":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_book);
                    imageRes = R.mipmap.daily_book;
                    break;
                case "daily_brushing":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_brushing);
                    imageRes = R.mipmap.daily_brushing;
                    break;
                case "daily_clean":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_clean);
                    imageRes = R.mipmap.daily_clean;
                    break;
                case "daily_cook":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_cook);
                    imageRes = R.mipmap.daily_cook;
                    break;
                case "daily_exercise_one":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_exercise_one);
                    imageRes = R.mipmap.daily_exercise_one;
                    break;
                case "daily_exercise_two":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_exercise_two);
                    imageRes = R.mipmap.daily_exercise_two;
                    break;
                case "daily_reading":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_reading);
                    imageRes = R.mipmap.daily_reading;
                    break;
                case "daily_shopping":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_shopping);
                    imageRes = R.mipmap.daily_shopping;
                    break;
                case "daily_xs":
                    binding.habitIconRes.setImageResource(R.mipmap.daily_xs);
                    imageRes = R.mipmap.daily_xs;
                    break;
                case "drink_c":
                    binding.habitIconRes.setImageResource(R.mipmap.drink_c);
                    imageRes = R.mipmap.drink_c;
                    break;
                case "drink_cf":
                    binding.habitIconRes.setImageResource(R.mipmap.drink_cf);
                    imageRes = R.mipmap.drink_cf;
                    break;
                case "drink_hj":
                    binding.habitIconRes.setImageResource(R.mipmap.drink_hj);
                    imageRes = R.mipmap.drink_hj;
                    break;
                case "drink_kl":
                    binding.habitIconRes.setImageResource(R.mipmap.drink_kl);
                    imageRes = R.mipmap.drink_kl;
                    break;
                case "drink_kqs":
                    binding.habitIconRes.setImageResource(R.mipmap.drink_kqs);
                    imageRes = R.mipmap.drink_kqs;
                    break;
                case "food_bbt":
                    binding.habitIconRes.setImageResource(R.mipmap.food_bbt);
                    imageRes = R.mipmap.food_bbt;
                    break;
                case "food_d":
                    binding.habitIconRes.setImageResource(R.mipmap.food_d);
                    imageRes = R.mipmap.food_d;
                    break;
                case "food_hb":
                    binding.habitIconRes.setImageResource(R.mipmap.food_hb);
                    imageRes = R.mipmap.food_hb;
                    break;
                case "food_jt":
                    binding.habitIconRes.setImageResource(R.mipmap.food_jt);
                    imageRes = R.mipmap.food_jt;
                    break;
                case "food_r":
                    binding.habitIconRes.setImageResource(R.mipmap.food_r);
                    imageRes = R.mipmap.food_r;
                    break;
                case "food_rice":
                    binding.habitIconRes.setImageResource(R.mipmap.food_rice);
                    imageRes = R.mipmap.food_rice;
                    break;
                case "food_x":
                    binding.habitIconRes.setImageResource(R.mipmap.food_x);
                    imageRes = R.mipmap.food_x;
                    break;
                case "food_ym":
                    binding.habitIconRes.setImageResource(R.mipmap.food_ym);
                    imageRes = R.mipmap.food_ym;
                    break;
                case "food_yu":
                    binding.habitIconRes.setImageResource(R.mipmap.food_yu);
                    imageRes = R.mipmap.food_yu;
                    break;
                case "food_zznc":
                    binding.habitIconRes.setImageResource(R.mipmap.food_zznc);
                    imageRes = R.mipmap.food_zznc;
                    break;
                case "fruits_cm":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_cm);
                    imageRes = R.mipmap.fruits_cm;
                    break;
                case "fruits_nm":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_nm);
                    imageRes = R.mipmap.fruits_nm;
                    break;
                case "fruits_jz":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_jz);
                    imageRes = R.mipmap.fruits_jz;
                    break;
                case "fruits_yt":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_yt);
                    imageRes = R.mipmap.fruits_yt;
                    break;
                case "fruits_pg":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_pg);
                    imageRes = R.mipmap.fruits_pg;
                    break;
                case "fruits_pt":
                    binding.habitIconRes.setImageResource(R.mipmap.fruits_pt);
                    imageRes = R.mipmap.fruits_pt;
                    break;
                case "other_ballons":
                    binding.habitIconRes.setImageResource(R.mipmap.other_ballons);
                    imageRes = R.mipmap.other_ballons;
                    break;
                case "other_birthday":
                    binding.habitIconRes.setImageResource(R.mipmap.other_birthday);
                    imageRes = R.mipmap.other_birthday;
                    break;
                case "other_evening":
                    binding.habitIconRes.setImageResource(R.mipmap.other_evening);
                    imageRes = R.mipmap.other_evening;
                    break;
                case "other_goal":
                    binding.habitIconRes.setImageResource(R.mipmap.other_goal);
                    imageRes = R.mipmap.other_goal;
                    break;
                case "other_flag":
                    binding.habitIconRes.setImageResource(R.mipmap.other_flag);
                    imageRes = R.mipmap.other_flag;
                    break;
                case "vegetables_bc":
                    binding.habitIconRes.setImageResource(R.mipmap.vegetables_bc);
                    imageRes = R.mipmap.vegetables_bc;
                    break;
                case "vegetables_d":
                    binding.habitIconRes.setImageResource(R.mipmap.vegetables_d);
                    imageRes = R.mipmap.vegetables_d;
                    break;
                case "vegetables_hc":
                    binding.habitIconRes.setImageResource(R.mipmap.vegetables_hc);
                    imageRes = R.mipmap.vegetables_hc;
                    break;
                case "vegetables_lb":
                    binding.habitIconRes.setImageResource(R.mipmap.vegetables_lb);
                    imageRes = R.mipmap.vegetables_lb;
                    break;
                case "vegetables_xhs":
                    binding.habitIconRes.setImageResource(R.mipmap.vegetables_xhs);
                    imageRes = R.mipmap.vegetables_xhs;
                    break;
            }
            newHabit.setHabitIconRes(imageRes);
            binding.habitName.setText(newHabit.getHabitName());
            binding.habitIcon.setCardBackgroundColor(newHabit.getHabitIcon());
            binding.habitFrequency.setText(newHabit.getFrequency());
            binding.habitRemind.setText(newHabit.getRemindTime());
            binding.habitTodoDate.setText(newHabit.getTodoDate());
        } else {
            newHabit.setHabitType("other");
        }
        binding.question.setOnTouchListener(this::onTouch);
        binding.name.setOnClickListener(this::onClick);
        binding.icon.setOnClickListener(this::onClick);
        binding.todoDate.setOnClickListener(this::onClick);
        binding.frequency.setOnClickListener(this::onClick);
        binding.remind.setOnClickListener(this::onClick);
        binding.confirmBtn.setOnClickListener(this::onClick);

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
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Intent intent;
        switch (v.getId()) {
            case R.id.confirm_btn:
                Home.getTodoAdapter().notifyDataSetChanged();
                HabitManager.getHabitManagerAdapter().notifyDataSetChanged();
                System.out.println(oldHabit);
                System.out.println(newHabit);
                Log.d(TAG, "1: " + JSON.toJSONString(Home.getAllTodoList()));
                oldHabit.setHabitType(newHabit.getHabitType());
                oldHabit.setHabitName(newHabit.getHabitName());
                oldHabit.setHabitIconRes(newHabit.getHabitIconRes());
                oldHabit.setHabitIcon(newHabit.getHabitIcon());
                oldHabit.setFrequency(newHabit.getFrequency());
                oldHabit.setTodoDate(newHabit.getTodoDate());
                oldHabit.setRemindTime(newHabit.getRemindTime());
                oldHabit.setStatue(newHabit.getStatue());
                oldHabit.setFinishTime(newHabit.getFinishTime());
                Log.d(TAG, "2: " + JSON.toJSONString(Home.getAllTodoList()));

                SPUtils.putString("todoList", JSON.toJSONString(Home.getAllTodoList()), getContext());

                Log.d(TAG, "onClick: SPU:" + SPUtils.getString("todoList", JSON.toJSONString(Home.getAllTodoList()), getContext()));

                Home.updateAllTodoList(LocalDate.now().getMonthOfYear(), LocalDate.now());

                finish();
                break;
            case R.id.name:
                intent = new Intent();
                intent.setClass(this, ChangeHabitName.class);
                intent.putExtra("activity", "edit");
                startActivity(intent);
                break;
            case R.id.icon:
                intent = new Intent();
                intent.setClass(this, ChangeHabitIcon.class);
                intent.putExtra("activity", "edit");
                startActivity(intent);
                break;
            case R.id.frequency:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                        binding.habitFrequency.setText(freList[options1]);
                        newHabit.setFrequency(freList[options1]);
                        constellationSelectOption = options1;
                        return false;
                    }
                })
                        .setTitleText("")
                        .setSelectOptions(constellationSelectOption)
                        .build();
                pvOptions.setPicker(freList);
                pvOptions.show();
                break;
            case R.id.todo_date:
                //正确设置方式 原因：注意事项有说明
                startDate.set(selectedDate.get(Calendar.YEAR), (selectedDate.get(Calendar.MONTH)), selectedDate.get(Calendar.DAY_OF_MONTH));
                endDate.set(2099, 12, 30);

                TimePickerView pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        newHabit.setTodoDate(new SimpleDateFormat("MM-dd E").format(date));
                        binding.habitTodoDate.setText(new SimpleDateFormat("MM-dd E").format(date));
                    }
                })
                        .setType(new boolean[]{false, true, true, false, false, false})// 默认全部显示
                        .setCancelText("Cancel")//取消按钮文字
                        .setSubmitText("Sure")//确认按钮文字
                        .setContentTextSize(17)
                        .setItemVisibleCount(5)
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(startDate, endDate)//起始终止年月日设定
                        .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCancelText("取消")
                        .setSubmitText("确认")
                        .isDialog(false)//是否显示为对话框样式
                        .build();
                pvDate.show();
                break;
            case R.id.remind:

                //正确设置方式 原因：注意事项有说明
                startDate.set(selectedDate.get(Calendar.YEAR), (selectedDate.get(Calendar.MONTH)), selectedDate.get(Calendar.DAY_OF_MONTH));
                endDate.set(2099, 12, 30);
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        newHabit.setRemindTime(new SimpleDateFormat("MM-dd HH:mm E").format(date));
                        binding.habitRemind.setText(new SimpleDateFormat("MM-dd HH:mm E").format(date));
                    }
                })
                        .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
                        .setCancelText("Cancel")//取消按钮文字
                        .setSubmitText("Sure")//确认按钮文字
                        .setContentTextSize(17)
                        .setItemVisibleCount(5)
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(startDate, endDate)//起始终止年月日设定
                        .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCancelText("取消")
                        .setSubmitText("确认")
                        .isDialog(false)//是否显示为对话框样式
                        .build();
                pvTime.show();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        XUIPopup mNormalPopup = new XUIPopup(getContext());
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                DensityUtils.dp2px(getContext(), 250),
                WRAP_CONTENT
        ));
        ;
        textView.setPadding(40, 20, 20, 40);
        textView.setText("名称：为目标起个名字吧!\n" + "图标：更换喜欢的目标图标吧\n" + "时间：计划完成目标的日期\n" + "频率：目标完成的频率。\n" + "提醒：为目标设置一个提醒时间吧！");
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        mNormalPopup.setContentView(textView);
        mNormalPopup.setAnimStyle(XUIPopup.ANIM_AUTO);
        mNormalPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
        mNormalPopup.show(v);
        return false;
    }
}