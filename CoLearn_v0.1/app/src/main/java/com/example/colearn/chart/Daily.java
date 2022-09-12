package com.example.colearn.chart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colearn.R;
import com.example.colearn.adapter.ChartItemsAdapter;
import com.example.colearn.data.ChartData;
import com.example.colearn.data.mPieData;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class Daily extends Fragment {

    private Context mContext;
    public View mView;
    private PieChartBase pieChart;
    private RecyclerView recyclerView_pie;
    private CubicLineChartBase cubicLineChart;
    private ArrayList<ChartData> chartDataArrayList;

    public static Daily getInstance(String title) {
        return new Daily();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.daily, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load PieChart
        pieChart = new PieChartBase(mContext, view.findViewById(R.id.daily_chart_pie));

        // generate data
        mPieData mPieData = new mPieData();
//        mPieData.setPieChartDataLabel("今日活动项目");
        mPieData.addEntry(12.1f, "读书");
        mPieData.addEntry(24.1f, "玩手机");
        mPieData.addEntry(35.9f, "写字");
        mPieData.addEntry(27.9f, "练琴");

        // set data and show
        pieChart.setPieData(mPieData.generateData());
        pieChart.init();


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

        recyclerView_pie = view.findViewById(R.id.dialy_pie_recycleview);
        recyclerView_pie.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_pie.setAdapter(new ChartItemsAdapter(mContext, chartDataArrayList));


        //cubicchart
        cubicLineChart = new CubicLineChartBase(mContext, getActivity(), view.findViewById(R.id.daily_chart_cubicline));
        cubicLineChart.init();
        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout_d);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPieData mPieData = new mPieData();
                mPieData.setPieChartDataLabel("今日活动项目");
                mPieData.addEntry(52.1f, "读书");
                mPieData.addEntry(4.1f, "玩手机");
                mPieData.addEntry(15.9f, "写字");
                mPieData.addEntry(27.9f, "练琴");

                // set data and show
                pieChart.setPieData(mPieData.generateData());
                pieChart.init();
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