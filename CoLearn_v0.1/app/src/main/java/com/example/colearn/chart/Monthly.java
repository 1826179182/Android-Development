package com.example.colearn.chart;

import android.os.Bundle;

import com.example.colearn.R;
import com.example.colearn.fragments.BaseFragment;


public class Monthly extends BaseFragment {

    public static Monthly getInstance(String title) {
        return new Monthly();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisLayout = R.layout.monthly;
        thisRecyclerViewLayout = R.id.recyclerView_daily_charts3;
        ChartFragType = new int[]{STYLE_HALF_PIE, STYLE_BAR, STYLE_FULL_PIE};

        initChartDataList();
    }
}