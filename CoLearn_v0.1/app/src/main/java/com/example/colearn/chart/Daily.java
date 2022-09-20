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

        // test data
        String[] labels = new String[]{"读书", "写字", "练琴", "玩手机", "打瞌睡", "啃手指"};
        String[] ratio = new String[]{"0.1", "0.2", "0.1", "0.1", "0.1", "0.4"};
        String[] length = new String[]{"1.4", "0.9", "0.6", "2.2", "0.5", "0.1"};
        int[] imgIds = new int[]{R.mipmap.reading, R.mipmap.swim, R.mipmap.do_homework,
                R.mipmap.guitar, R.mipmap.badminton, R.mipmap.food_jt};


        chartDataArrayList = new ArrayList<>();

        for(int i=0;i<6;i++){
            ChartData chartData = new ChartData();
            chartData.setCategory(labels[i]);
            chartData.setImgResId(imgIds[i]);
            chartData.setCdRatio(ratio[i]);
            chartData.setCdLength(length[i]);
            chartDataArrayList.add(chartData);
        }

        recyclerView_pie = view.findViewById(R.id.dialy_pie_recycleview);
        recyclerView_pie.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_pie.setAdapter(new ChartItemsAdapter(mContext, chartDataArrayList));

        //cubicChart
        cubicLineChart = new CubicLineChartBase(mContext, getActivity(), view.findViewById(R.id.daily_chart_cubicline));
        cubicLineChart.init();
        cubicLineChart.updateData(new float[]{1,2,3,4,5,6,7,8,9,9,9,9,9,9,9,9,9,9,9,9,1,2,3,4});
        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout_d);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pieChart.updateData(chartDataArrayList);
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