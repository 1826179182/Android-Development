package com.example.colearn.chart;

import android.os.Bundle;

import com.example.colearn.R;
import com.example.colearn.fragments.BaseFragment;


public class Weekly extends BaseFragment {

    public static Weekly getInstance(String title) {
        return new Weekly();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisLayout = R.layout.weekly;
        thisRecyclerViewLayout = R.id.recyclerView_daily_charts2;
        ChartFragType = new int[]{STYLE_RADER, STYLE_FULL_PIE};

        initChartDataList();
    }
}