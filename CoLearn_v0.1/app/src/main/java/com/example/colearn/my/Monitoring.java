package com.example.colearn.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.colearn.R;
import com.gyf.immersionbar.ImmersionBar;

public class Monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
    }
}