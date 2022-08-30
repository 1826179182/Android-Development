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

import com.alibaba.fastjson.JSONArray;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.components.WallPaper;
import com.example.colearn.databinding.ActivityChangeWallpaperBinding;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class ChangeWallpaper extends AppCompatActivity {
    private static ActivityChangeWallpaperBinding binding;
    private List<WallPaper> wallpapers = new ArrayList<>();
    private WallpaperAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeWallpaperBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        init();
    }

    private void init() {
        wallpapers.add(new WallPaper(R.mipmap.bg_cow, "奶牛", "sky_blue"));
        wallpapers.add(new WallPaper(R.mipmap.bg_girl, "田野上的女孩", "sky_blue"));
        wallpapers.add(new WallPaper(R.mipmap.bg_rain, "猫和窗外的雨", "rain_green"));
        wallpapers.add(new WallPaper(R.mipmap.bg_grassland, "山谷和牧羊", "rain_green"));
        wallpapers.add(new WallPaper(R.mipmap.bg_house, "平原和城堡", "sky_blue"));
        wallpapers.add(new WallPaper(R.mipmap.bg_lazy, "树上赏月", "deep_blue"));
        wallpapers.add(new WallPaper(R.mipmap.bg_sunflower, "向日葵", "deep_sky_blue"));
        wallpapers.add(new WallPaper(R.mipmap.bg_train, "水路火车", "water_blue"));

        mMyAdapter = new WallpaperAdapter();
        binding.wallpaperRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.wallpaperRecyclerView.setLayoutManager(layoutManager);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }

    private class WallpaperAdapter extends RecyclerView.Adapter {
        private ImageView wallpaperSrc;
        private TextView wallpaperName;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            WallPaper wallPaper = wallpapers.get(position);
            wallpaperSrc.setImageResource(wallPaper.getWallpaper());
            wallpaperName.setText(wallPaper.getWallpaperName());
            wallpaperSrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home.setWallPaper(wallPaper);
                    Home.changeWallpaper();
                    SPUtils.putString("wallpaper", JSONArray.toJSON(wallPaper).toString(), ChangeWallpaper.this);
                    finish();
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return wallpapers.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                wallpaperSrc = itemView.findViewById(R.id.wallpaper);
                wallpaperName = itemView.findViewById(R.id.wallpaper_name);
            }
        }
    }

}