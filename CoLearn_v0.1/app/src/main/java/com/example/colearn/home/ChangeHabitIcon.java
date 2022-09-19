package com.example.colearn.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.colearn.R;
import com.example.colearn.databinding.ActivityChangeHabitIconBinding;
import com.example.colearn.my.EditHabit;
import com.example.colearn.pojo.Habit;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class ChangeHabitIcon extends AppCompatActivity implements View.OnClickListener {

    private ActivityChangeHabitIconBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeHabitIconBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        init();
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

            case R.id.daily_ball:
                habitType = "daily_ball";
                break;
            case R.id.daily_bathing:
                habitType = "daily_bathing";
                break;
            case R.id.daily_book:
                habitType = "daily_book";
                break;
            case R.id.daily_brushing:
                habitType = "daily_brushing";
                break;
            case R.id.daily_clean:
                habitType = "daily_clean";
                break;
            case R.id.daily_cook:
                habitType = "daily_cook";
                break;
            case R.id.daily_exercise_one:
                habitType = "daily_exercise_one";
                break;
            case R.id.daily_exercise_two:
                habitType = "daily_exercise_two";
                break;
            case R.id.daily_reading:
                habitType = "daily_reading";
                break;
            case R.id.daily_shopping:
                habitType = "daily_shopping";
                break;
            case R.id.daily_xs:
                habitType = "daily_xs";
                break;

            case R.id.drink_c:
                habitType = "drink_c";
                break;
            case R.id.drink_cf:
                habitType = "drink_cf";
                break;
            case R.id.drink_hj:
                habitType = "drink_hj";
                break;
            case R.id.drink_kl:
                habitType = "drink_kl";
                break;
            case R.id.drink_kqs:
                habitType = "drink_kqs";
                break;

            case R.id.food_bbt:
                habitType = "food_bbt";
                break;
            case R.id.food_d:
                habitType = "food_d";
                break;
            case R.id.food_hb:
                habitType = "food_hb";
                break;
            case R.id.food_jt:
                habitType = "food_jt";
                break;
            case R.id.food_r:
                habitType = "food_r";
                break;
            case R.id.food_rice:
                habitType = "food_rice";
                break;
            case R.id.food_x:
                habitType = "food_x";
                break;
            case R.id.food_ym:
                habitType = "food_ym";
                break;
            case R.id.food_yu:
                habitType = "food_yu";
                break;
            case R.id.food_zznc:
                habitType = "food_zznc";
                break;

            case R.id.fruits_cm:
                habitType = "fruits_cm";
                break;
            case R.id.fruits_nm:
                habitType = "fruits_nm";
                break;
            case R.id.fruits_jz:
                habitType = "fruits_jz";
                break;
            case R.id.fruits_yt:
                habitType = "fruits_yt";
                break;
            case R.id.fruits_pg:
                habitType = "fruits_pg";
                break;
            case R.id.fruits_pt:
                habitType = "fruits_pt";
                break;

            case R.id.other_ballons:
                habitType = "other_ballons";
                break;
            case R.id.other_birthday:
                habitType = "other_birthday";
                break;
            case R.id.other_evening:
                habitType = "other_evening";
                break;
            case R.id.other_goal:
                habitType = "other_goal";
                break;
            case R.id.other_flag:
                habitType = "other_flag";
                break;

            case R.id.vegetables_bc:
                habitType = "vegetables_bc";
                break;
            case R.id.vegetables_d:
                habitType = "vegetables_d";
                break;
            case R.id.vegetables_hc:
                habitType = "vegetables_hc";
                break;
            case R.id.vegetables_lb:
                habitType = "vegetables_lb";
                break;
            case R.id.vegetables_xhs:
                habitType = "vegetables_xhs";
                break;

        }
        Intent intent = new Intent();
        if (getIntent().getStringExtra("activity").equals("edit")) {
            Habit newHabit = EditHabit.getNewHabit();
            newHabit.setHabitType(habitType);
            newHabit.setHabitIcon(((CardView) v).getCardBackgroundColor().getDefaultColor());
            EditHabit.update();
        } else {
            intent.setClass(ChangeHabitIcon.this, AddNewEvent.class);
            startActivity(intent);
            Habit newHabit = AddEvent.getNewHabit();
            newHabit.setHabitType(habitType);
            newHabit.setHabitIcon(((CardView) v).getCardBackgroundColor().getDefaultColor());
        }
        finish();

    }

    private void init() {
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

        binding.dailyBall.setOnClickListener(this::onClick);
        binding.dailyBathing.setOnClickListener(this::onClick);
        binding.dailyBook.setOnClickListener(this::onClick);
        binding.dailyBrushing.setOnClickListener(this::onClick);
        binding.dailyClean.setOnClickListener(this::onClick);
        binding.dailyCook.setOnClickListener(this::onClick);
        binding.dailyExerciseOne.setOnClickListener(this::onClick);
        binding.dailyExerciseTwo.setOnClickListener(this::onClick);
        binding.dailyReading.setOnClickListener(this::onClick);
        binding.dailyShopping.setOnClickListener(this::onClick);
        binding.dailyXs.setOnClickListener(this::onClick);

        binding.drinkC.setOnClickListener(this::onClick);
        binding.drinkCf.setOnClickListener(this::onClick);
        binding.drinkHj.setOnClickListener(this::onClick);
        binding.drinkKl.setOnClickListener(this::onClick);
        binding.drinkKqs.setOnClickListener(this::onClick);

        binding.foodBbt.setOnClickListener(this::onClick);
        binding.foodD.setOnClickListener(this::onClick);
        binding.foodHb.setOnClickListener(this::onClick);
        binding.foodJt.setOnClickListener(this::onClick);
        binding.foodR.setOnClickListener(this::onClick);
        binding.foodRice.setOnClickListener(this::onClick);
        binding.foodX.setOnClickListener(this::onClick);
        binding.foodYm.setOnClickListener(this::onClick);
        binding.foodYu.setOnClickListener(this::onClick);
        binding.foodZznc.setOnClickListener(this::onClick);

        binding.fruitsCm.setOnClickListener(this::onClick);
        binding.fruitsJz.setOnClickListener(this::onClick);
        binding.fruitsNm.setOnClickListener(this::onClick);
        binding.fruitsPg.setOnClickListener(this::onClick);
        binding.fruitsPt.setOnClickListener(this::onClick);
        binding.fruitsYt.setOnClickListener(this::onClick);

        binding.otherBallons.setOnClickListener(this::onClick);
        binding.otherBirthday.setOnClickListener(this::onClick);
        binding.otherEvening.setOnClickListener(this::onClick);
        binding.otherFlag.setOnClickListener(this::onClick);
        binding.otherGoal.setOnClickListener(this::onClick);

        binding.vegetablesBc.setOnClickListener(this::onClick);
        binding.vegetablesD.setOnClickListener(this::onClick);
        binding.vegetablesHc.setOnClickListener(this::onClick);
        binding.vegetablesLb.setOnClickListener(this::onClick);
        binding.vegetablesXhs.setOnClickListener(this::onClick);
        binding.basketball.setOnClickListener(this::onClick);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }

}