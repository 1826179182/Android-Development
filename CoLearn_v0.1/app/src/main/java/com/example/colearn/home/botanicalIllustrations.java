package com.example.colearn.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.colearn.R;
import com.example.colearn.components.Plant;
import com.example.colearn.databinding.ActivityBotanicalIllustrationsBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class botanicalIllustrations extends AppCompatActivity {
    private static ActivityBotanicalIllustrationsBinding binding;
    private List<Plant> plants = new ArrayList<>();

    private BannerViewPager<Plant> mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBotanicalIllustrationsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(com.xuexiang.xui.R.color.xui_btn_green_select_color)
                .init();
        init();
    }

    private void init() {
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });


        plants.add(new Plant(1));
        plants.add(new Plant(2));
        plants.add(new Plant(3));
        plants.add(new Plant(4));
        plants.add(new Plant(5));
        plants.add(new Plant(6));
        plants.add(new Plant(7));
        plants.add(new Plant(8));
        plants.add(new Plant(9));

        mViewPager = findViewById(R.id.banner_view);
        mViewPager.setLifecycleRegistry(getLifecycle())
                .setAdapter(new MultiViewTypesAdapter())
                .create();
        mViewPager.refreshData(plants);
    }

    private class MultiViewTypesAdapter extends BaseBannerAdapter<Plant> {

        @Override
        protected void bindData(BaseViewHolder<Plant> holder, Plant plant, int position, int pageSize) {
            plant = plants.get(position);
            LottieAnimationView lottie = holder.itemView.findViewById(R.id.plant);
            TextView statue = holder.itemView.findViewById(R.id.acquire_statue);
            for (Plant e : Planting.getPlants()) {
                if (e.getId() == plant.getId())
                    statue.setText("已获得");
            }
            lottie.setAnimation("plant_" + plant.getId() + ".json");
            lottie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lottie.playAnimation();
                }
            });
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.banner_item_view;
        }
    }
}