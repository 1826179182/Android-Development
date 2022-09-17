package com.example.colearn.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.colearn.R;
import com.example.colearn.pojo.Plant;
import com.example.colearn.databinding.ActivityMyGargenBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class MyGarden extends AppCompatActivity {
    private static ActivityMyGargenBinding binding;
    private List<Plant> plants = new ArrayList<>();
    private MyGardenAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyGargenBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(com.xuexiang.xui.R.color.xui_btn_green_select_color)
                .init();
        init();
    }

    private void init() {
        plants = Planting.getPlants();

        mMyAdapter = new MyGardenAdapter();
        binding.myGardenRecyclerView.setAdapter(mMyAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.myGardenRecyclerView.setLayoutManager(layoutManager);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });


    }

    private class MyGardenAdapter extends RecyclerView.Adapter {
        private LottieAnimationView plant;
        private TextView acquireTime;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.garden_plant_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            plant.setAnimation("plant_" + plants.get(position).getId() + ".json");
            acquireTime.setText(plants.get(position).getFinishTime());
            plant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LottieAnimationView lottie = binding.myGardenRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.plant);
                    lottie.playAnimation();
                }
            });
        }

        @Override
        public int getItemCount() {
            return plants.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                plant = itemView.findViewById(R.id.plant);
                acquireTime = itemView.findViewById(R.id.acquire_time);
            }
        }


    }
}