package com.example.colearn.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.Oscillator;

import com.example.colearn.R;
import com.example.colearn.fragments.BaseFragment;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;

import org.joda.time.LocalDate;

import java.util.List;


public class Daily extends BaseFragment {

    private WeekCalendar mWeekCalendar;

    public static Daily getInstance(String title) {
        return new Daily();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisLayout = R.layout.daily;
        thisRecyclerViewLayout = R.id.recyclerView_daily_charts1;
        pieType = STYLE_FULL_PIE;
        ChartFragType = new int[]{STYLE_HALF_PIE, STYLE_FULL_PIE};

        initChartDataList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeekCalendar = view.findViewById(R.id.weekCalendar_daily);

        mWeekCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                Log.d(Oscillator.TAG, "   当前页面选中 " + localDate);
                Log.d(Oscillator.TAG, "   dateChangeBehavior " + dateChangeBehavior);

                Log.e(Oscillator.TAG, "baseCalendar::" + baseCalendar);
            }
        });
        mWeekCalendar.setOnCalendarMultipleChangedListener(new OnCalendarMultipleChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currPagerCheckedList, List<LocalDate> totalCheckedList, DateChangeBehavior dateChangeBehavior) {
                Log.d(Oscillator.TAG, year + "年" + month + "月");
                Log.d(Oscillator.TAG, "当前页面选中：：" + currPagerCheckedList);
                Log.d(Oscillator.TAG, "全部选中：：" + totalCheckedList);
            }
        });
    }
}