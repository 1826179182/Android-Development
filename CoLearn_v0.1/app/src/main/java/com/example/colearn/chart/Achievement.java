package com.example.colearn.chart;

import android.os.Bundle;

import com.example.colearn.R;
import com.example.colearn.fragments.BaseFragment;


public class Achievement extends BaseFragment {
    public static Achievement getInstance(String title) {
        return new Achievement();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisLayout = R.layout.achievement;
        thisRecyclerViewLayout = R.id.recyclerView_daily_charts4;
        ChartFragType = new int[]{STYLE_HALF_PIE, STYLE_FULL_PIE};

        initChartDataList();
    }
}