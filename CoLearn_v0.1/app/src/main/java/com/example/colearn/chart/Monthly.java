package com.example.colearn.chart;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colearn.Chart;
import com.example.colearn.R;
import com.example.colearn.data.BarChartData;
import com.example.colearn.databinding.ChartBinding;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Monthly extends Fragment {

    private Context mContext;
    public View mView;
    private BarChartBase barChart;
    private MaterialSpinner spinner;
    private int currentCategory = 0;
    private ArrayAdapter<String> adapterSpinner;
    private ArrayList<BarChartData> barChartDataArrayList;
    private ChartBinding chartBinding;

    public static Monthly getInstance(String title) {
        return new Monthly();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.monthly, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // barchart
        barChart = new BarChartBase(mContext, getActivity(), view.findViewById(R.id.monthly_chart_bar), new String[]{"读书", "练琴", "书法", "练字", "玩手机"});
        barChart.init();

        String[] labels = new String[]{"读书", "写字", "练琴", "玩手机", "打瞌睡", "啃手指"};


        spinner = view.findViewById(R.id.month_spinner);
        adapterSpinner = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, labels);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                currentCategory = position;
                Log.d("Monthly", String.valueOf(Chart.Year) + "年" + String.valueOf(Chart.Month) + "月");
                barChartDataArrayList = geneTestData();
                barChart.updateDate(barChartDataArrayList.get(position));
            }
        });


        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout_m);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                barChartDataArrayList = geneTestData();
                barChart.updateDate(barChartDataArrayList.get(currentCategory));
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                barChartDataArrayList = geneTestData();
                barChart.updateDate(barChartDataArrayList.get(currentCategory));
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
    }

    public ArrayList<BarChartData> geneTestData() {
        // test data
        String[] labels = new String[]{"读书", "写字", "练琴", "玩手机", "打瞌睡", "啃手指"};
        String date = new String(String.valueOf(Chart.Year) + "-" + String.valueOf(Chart.Month) + "-29 20:23:56");

        ArrayList<ArrayList<String>> thisLength = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int j = 0; j < 31; j++) {
                stringArrayList.add(String.valueOf(Math.random() * 2));
            }
            thisLength.add(stringArrayList);
        }

        barChartDataArrayList = new ArrayList<>();
        try {
            for (int i = 0; i < labels.length; i++) {
                BarChartData bardata = new BarChartData();
                bardata.setCategory(labels[i]);
                bardata.setCdLength(thisLength.get(i));
                bardata.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));
                barChartDataArrayList.add(bardata);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return barChartDataArrayList;
    }
}
