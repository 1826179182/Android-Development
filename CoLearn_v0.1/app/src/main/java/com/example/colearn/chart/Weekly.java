package com.example.colearn.chart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colearn.R;
import com.example.colearn.data.ChartData;
import com.example.colearn.data.mPieData;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


public class Weekly extends Fragment {

    private Context mContext;
    public View mView;
    private HalfPieChartBase halfPieChart;
    private RadarChartBase radarChart;
    private ArrayList<ChartData> chartDataArrayList;

    public static Weekly getInstance(String title) {
        return new Weekly();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.weekly, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load HalfPieChart
        halfPieChart = new HalfPieChartBase(mContext, getActivity(), view.findViewById(R.id.weekly_chart_halfpie));

        // generate data
        mPieData mHalfPieData = new mPieData();
        mHalfPieData.setPieChartDataLabel("今日活动项目");
        mHalfPieData.addEntry(12.1f, "读书");
        mHalfPieData.addEntry(24.1f, "玩手机");
        mHalfPieData.addEntry(35.9f, "写字");
        mHalfPieData.addEntry(27.9f, "练琴");

        // set data and show
        halfPieChart.setHalfPieData(mHalfPieData.generateData());
        halfPieChart.init();


        chartDataArrayList = new ArrayList<>();
        ChartData chartData = new ChartData();
        chartData.setCategory("读书");
        chartData.setImgResId(R.mipmap.reading);
        chartData.setCdRatio("0.21");
        chartData.setCdLength("1.4");
        chartDataArrayList.add(chartData);

        ChartData chartData2 = new ChartData();
        chartData2.setCategory("写字");
        chartData2.setImgResId(R.mipmap.basketball);
        chartData2.setCdRatio("0.41");
        chartData2.setCdLength("2.5");
        chartDataArrayList.add(chartData2);


        //radarchart
        radarChart = new RadarChartBase(mContext, getActivity(), view.findViewById(R.id.weekly_chart_radar), new String[]{"读书", "玩手机", "写字", "练琴", "睡觉"});
        radarChart.init();

        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout_w);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

}