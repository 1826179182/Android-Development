package com.example.colearn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colearn.R;
import com.example.colearn.data.ChartData;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartViewHolder> {

    public final int STYLE_HALF_PIE = 0;
    public final int STYLE_FULL_PIE = 1;
    public final int STYLE_BAR = 2;
    public int pieType;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ChartData> chartDataList;

    public ChartAdapter(Context context, ArrayList<ChartData> chartData) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        chartDataList = chartData;
    }

    @Override
    public int getItemCount() {
        return chartDataList.size();
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "this is Creating View Holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ChartViewHolder myViewHolder = new ChartViewHolder(view);
//            ChartTransaction(myViewHolder);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        Log.d("Daily.java", "time:" + position);
        ChartData chartData = chartDataList.get(position);
        holder.textView.setText(chartData.getCdCategory());
    }

    public class ChartViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private FrameLayout frameLayout;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardTxt);
            frameLayout = itemView.findViewById(R.id.daily_pie_chart);
            frameLayout.setId(View.generateViewId());
        }
    }
}
