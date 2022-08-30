package com.example.colearn.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colearn.R;
import com.example.colearn.data.ChartData;

import java.util.ArrayList;

public class BaseFragment extends Fragment {

    //    private ListView list_2;
    //    private List<Habit> hasDoneList = new ArrayList<>();

    //    private static boolean hasChanged = false;
    private static int layoutId;
    public final int STYLE_HALF_PIE = 0;
    public final int STYLE_FULL_PIE = 1;
    public final int STYLE_BAR = 2;
    public final int STYLE_RADER = 3;
    public int pieType;
    public int thisLayout;
    public int thisRecyclerViewLayout;
    public int[] ChartFragType;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<ChartData> chartDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initChartDataList();
    }

    public void initChartDataList() {
        this.chartDataList = new ArrayList<>();
        for (int i = 0; i < ChartFragType.length; i++) {
            ChartData chartData = new ChartData();
            chartData.setTxtData("this is txtView + " + i);
            this.chartDataList.add(chartData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(thisLayout, null);
        mRecyclerView = view.findViewById(thisRecyclerViewLayout);
//        mRecyclerView.setHasFixedSize(true);
        Activity thisActivity = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(thisActivity);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(thisActivity, chartDataList);
        mRecyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        //        private Context context;
        private LayoutInflater mLayoutInflater;
        private ArrayList<ChartData> chartDataList;

        public MyAdapter(Context context, ArrayList<ChartData> chartData) {
            mLayoutInflater = LayoutInflater.from(context);
            chartDataList = chartData;
        }

        @Override
        public int getItemCount() {
            return ChartFragType.length;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("onCreateViewHolder", "this is Creating View Holder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
//            ChartTransaction(myViewHolder);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Log.d("Daily.java", "time:" + position);
            ChartData chartData = chartDataList.get(position);
//            holder.textView.setText(chartData.getTxtData());
//            ChartTransaction(holder);
            ChartTransaction(holder, ChartFragType[position]);
        }

        public void ChartTransaction(MyViewHolder holder) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (pieType) {
                case STYLE_HALF_PIE:
                    transaction.replace(holder.frameLayout.getId(), FragHalfPieChart.newInstance());
                    break;
                case STYLE_FULL_PIE:
                    transaction.replace(holder.frameLayout.getId(), FragPieChart.newInstance());
                    break;
                case STYLE_BAR:
                    transaction.replace(holder.frameLayout.getId(), FragBarChart.newInstance());
                    break;
                default:
                    break;
            }
            transaction.commit();
        }

        public void ChartTransaction(MyViewHolder holder, int thisType) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (thisType) {
                case STYLE_HALF_PIE:
                    transaction.replace(holder.frameLayout.getId(), FragHalfPieChart.newInstance());
                    break;
                case STYLE_FULL_PIE:
                    transaction.replace(holder.frameLayout.getId(), FragPieChart.newInstance());
                    break;
                case STYLE_BAR:
                    transaction.replace(holder.frameLayout.getId(), FragBarChart.newInstance());
                    break;
                case STYLE_RADER:
                    transaction.replace(holder.frameLayout.getId(), FragRadarChart.newInstance());
                    break;
                default:
                    break;
            }
            transaction.commit();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;
            private FrameLayout frameLayout;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.cardTxt);
                frameLayout = itemView.findViewById(R.id.daily_pie_chart);
                frameLayout.setId(View.generateViewId());
            }

            public int getFrameLayoutId() {
                return frameLayout.getId();
            }
        }
    }


}
