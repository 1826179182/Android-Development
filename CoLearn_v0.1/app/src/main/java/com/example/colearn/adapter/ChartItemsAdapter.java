package com.example.colearn.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colearn.R;
import com.example.colearn.data.ChartData;
import com.xuexiang.xui.widget.imageview.IconImageView;

import java.util.ArrayList;

public class ChartItemsAdapter extends RecyclerView.Adapter<ChartItemsAdapter.ChartItemsViewHolder> {

    private ArrayList<ChartData> chartDataArrayList;
    private Context context;

    public ChartItemsAdapter(Context context, ArrayList<ChartData> chartDataArrayList) {
        this.context = context;
        this.chartDataArrayList = chartDataArrayList;
    }

    @NonNull
    @Override
    public ChartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item, parent, false);
        ChartItemsViewHolder chartItemsViewHolder = new ChartItemsViewHolder(v);
        return chartItemsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChartItemsViewHolder holder, int position) {
        holder.item_img.setIconBitmap(BitmapFactory.decodeResource(context.getResources(), chartDataArrayList.get(position).getImgResId()));
        holder.item_img.setIconScale(0.8f);
        holder.item_name.setText(chartDataArrayList.get(position).getCdCategory());
        holder.item_ratio.setText(chartDataArrayList.get(position).getCdRatio() + "%");
        holder.item_time.setText(chartDataArrayList.get(position).getCdLength()+"小时");
        holder.progressBar.setProgress((int) (Float.parseFloat(chartDataArrayList.get(position).getCdRatio()) * 100));
    }

    @Override
    public int getItemCount() {
        return chartDataArrayList.size();
    }

    public class ChartItemsViewHolder extends RecyclerView.ViewHolder {

        private IconImageView item_img;
        private TextView item_name;
        private TextView item_ratio;
        private TextView item_time;
        private ProgressBar progressBar;

        public ChartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_name = itemView.findViewById(R.id.item_name);
            item_ratio = itemView.findViewById(R.id.item_ratio);
            item_time = itemView.findViewById(R.id.item_time);
            progressBar = itemView.findViewById(R.id.item_prog);
        }
    }
}
