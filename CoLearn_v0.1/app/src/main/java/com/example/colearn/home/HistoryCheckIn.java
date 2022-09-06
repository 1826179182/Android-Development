package com.example.colearn.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.components.CheckInRecord;
import com.example.colearn.utils.ButtonClickUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class HistoryCheckIn extends AppCompatActivity {
    private static List<CheckInRecord> checkInRecords = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CheckInHistoryAdapter mMyAdapter;

    private TitleBar titleBar;

    public static List<CheckInRecord> getCheckInRecords() {
        return checkInRecords;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_check_in);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        init();
    }

    private void init() {

        checkInRecords = Home.getCheckInRecords();

        titleBar = findViewById(R.id.title_bar);
        mRecyclerView = findViewById(R.id.check_in_history_listview);
        mMyAdapter = new CheckInHistoryAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }

    private class CheckInHistoryAdapter extends RecyclerView.Adapter {

        private TextView date;
        private TextView time;
        private TextView todo;
        private TextView hasDone;
        private ImageView expression;
        private ImageView upAndDown;
        private ExpandableLayout expandableLayout;


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_in_history_listview, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            CheckInRecord checkInRecord = HistoryCheckIn.getCheckInRecords().get(position);
            date.setText(checkInRecord.getDate());
            time.setText(checkInRecord.getTime());
            todo.setText(String.valueOf(checkInRecord.getTodo()));
            hasDone.setText(String.valueOf(checkInRecord.getHasDone()));
            switch (checkInRecord.getExpression()) {
                case 0:
                    expression.setImageResource(R.mipmap.happy_light);
                    break;
                case 1:
                    expression.setImageResource(R.mipmap.smile_light);
                    break;
                case 2:
                    expression.setImageResource(R.mipmap.unhappy_light);
                    break;
            }

            upAndDown = holder.itemView.findViewById(R.id.up_and_down);
            upAndDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonClickUtils.isFastClick()) { return; }

                    MyViewHolder myViewHolder = (MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
                    if (myViewHolder != null) {
                        expandableLayout = ((ExpandableLayout) myViewHolder.itemView.findViewById(R.id.check_in_detail));
                        if (expandableLayout.isExpanded()) {
                            expandableLayout.collapse();
                            ((ImageView) myViewHolder.itemView.findViewById(R.id.up_and_down)).setImageResource(R.mipmap.down);
                        } else {
                            expandableLayout.expand();
                            ((ImageView) myViewHolder.itemView.findViewById(R.id.up_and_down)).setImageResource(R.mipmap.up);
                        }
                    }


                }
            });
        }

        @Override
        public int getItemCount() {
            return HistoryCheckIn.getCheckInRecords().size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.check_in_date);
                time = itemView.findViewById(R.id.check_in_time);
                todo = itemView.findViewById(R.id.todo_number);
                hasDone = itemView.findViewById(R.id.has_done_number);
                expression = itemView.findViewById(R.id.expressions);
            }
        }


    }
}