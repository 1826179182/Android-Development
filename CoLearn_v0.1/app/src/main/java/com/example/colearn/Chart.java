package com.example.colearn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.Oscillator;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.colearn.chart.Achievement;
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

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Chart extends Fragment implements OnTabSelectListener {
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private MyViewPager2Adapter mAdapter;
    private TabLayout mTabLayout;

    private WeekCalendar mWeekCalendar;
    public static LocalDate selectDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
