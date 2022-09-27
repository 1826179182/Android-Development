package com.example.colearn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.Oscillator;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.colearn.chart.Daily;
import com.example.colearn.chart.Monthly;
import com.example.colearn.chart.Weekly;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.xuexiang.xui.widget.layout.XUILinearLayout;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Chart extends Fragment implements OnTabSelectListener {
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private MyViewPager2Adapter mAdapter;
    private TabLayout mTabLayout;
    private XUILinearLayout monthLayout;
    private TextView chartMonth;
    private Calendar startMonth;
    private Calendar endMonth;
    private Calendar selectedDate;

    private WeekCalendar mWeekCalendar;
    public static LocalDate selectDate;
    private static int Year = 2022;
    private static int Month = 8;
    private static int Day = 1;

    public static void setYear(int year) {
        Year = year;
    }

    public static void setMonth(int month) {
        Month = month;
    }

    public static int getYear() {
        return Year;
    }

    public static int getMonth() {
        return Month;
    }

    public static int getDay(){
        return Day;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding.inf
        return inflater.inflate(R.layout.chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitles = new ArrayList<>();
        mTitles.add("日统计");
        mTitles.add("周统计");
        mTitles.add("月统计");
//        mTitles.add("小成就");

        mFragments = new ArrayList<>();
        mFragments.add(Daily.getInstance("日统计"));
        mFragments.add(Weekly.getInstance("周统计"));
        mFragments.add(Monthly.getInstance("月统计"));
//        mFragments.add(Achievement.getInstance("小成就"));


        ViewPager2 viewPager = view.findViewById(R.id.vp);
        mAdapter = new MyViewPager2Adapter(getActivity().getSupportFragmentManager(), getLifecycle(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);

        mTabLayout = view.findViewById(R.id.ChartTabLayout);
        new TabLayoutMediator(mTabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTitles.get(position));
            }
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("Page Selected", "" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        mWeekCalendar = view.findViewById(R.id.weekCalendar_chart);
        chartMonth = view.findViewById(R.id.chartMonth);
        mWeekCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                Log.d(Oscillator.TAG, "   当前页面选中 " + localDate);
                Log.d(Oscillator.TAG, "   dateChangeBehavior " + dateChangeBehavior);
                Log.e(Oscillator.TAG, "baseCalendar::" + baseCalendar);
                Year = year;
                Month = month;
                Day = localDate.getDayOfMonth();
                switch (month) {
                    case 1:
                        chartMonth.setText("一月");
                        break;
                    case 2:
                        chartMonth.setText("二月");
                        break;
                    case 3:
                        chartMonth.setText("三月");
                        break;
                    case 4:
                        chartMonth.setText("四月");
                        break;
                    case 5:
                        chartMonth.setText("五月");
                        break;
                    case 6:
                        chartMonth.setText("六月");
                        break;
                    case 7:
                        chartMonth.setText("七月");
                        break;
                    case 8:
                        chartMonth.setText("八月");
                        break;
                    case 9:
                        chartMonth.setText("九月");
                        break;
                    case 10:
                        chartMonth.setText("十月");
                        break;
                    case 11:
                        chartMonth.setText("十一月");
                        chartMonth.setTextSize(15);
                        break;
                    case 12:
                        chartMonth.setText("十二月");
                        chartMonth.setTextSize(15);
                        break;
                }
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


        monthLayout = view.findViewById(R.id.daily_month_layout);
        monthLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Chart", "month clicked");
                        startMonth = Calendar.getInstance();
                        endMonth = Calendar.getInstance();
                        selectedDate = Calendar.getInstance();

                        startMonth.set(2010, 0, 1);
                        endMonth.set(2099, 12, 31);

                        TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {//选中事件回调
                                mWeekCalendar.jumpDate(new SimpleDateFormat("yyyy-MM").format(date) + "-01");
                            }
                        })
                                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                                .setCancelText("取消")
                                .setSubmitText("确认")

                                .setContentTextSize(17)
                                .setItemVisibleCount(5)
                                .setTitleSize(20)//标题文字大小
                                .setTitleText("选择月份")//标题文字
                                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                                .isCyclic(false)//是否循环滚动
                                .setDate(Calendar.getInstance())// 如果不设置的话，默认是系统时间*/
                                .setRangDate(startMonth, endMonth)//起始终止年月日设定
                                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                                .isDialog(true)//是否显示为对话框样式
                                .build();
                        pvTime.findViewById(com.bigkoo.pickerview.R.id.rv_topbar).setBackgroundResource(R.drawable.round_white_top);
                        pvTime.findViewById(com.bigkoo.pickerview.R.id.timepicker).setBackgroundResource(R.drawable.round_white_bottom);
                        pvTime.show();
                    }
                });
    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {
    }

    private class MyViewPager2Adapter extends FragmentStateAdapter {
        ArrayList<Fragment> fragments;

        public MyViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment> myFragment) {
            super(fragmentManager, lifecycle);
            fragments = myFragment;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }

}
