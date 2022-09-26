package com.example.colearn.my;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colearn.R;
import com.example.colearn.databinding.ActivityChangeNicknameBinding;
import com.example.colearn.pojo.User;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.Objects;

public class ChangeNickname extends AppCompatActivity {

    private ActivityChangeNicknameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeNicknameBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
        binding.confirmBtn.setOnClickListener(v -> {
            Log.d("ChangeNickName", "enter clicked");
            PersonalInformation.changeIngo(Objects.requireNonNull(binding.newNickname.getText()).toString(), "nickname");
            this.finish();
        });
        binding.newNickname.setText(PersonalInformation.getNickname());
    }

}