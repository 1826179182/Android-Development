package com.example.colearn.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colearn.R;
import com.example.colearn.databinding.ActivityLoginOrRegisterBinding;
import com.gyf.immersionbar.ImmersionBar;

public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    private static ActivityLoginOrRegisterBinding binding;
    public static LoginOrRegister loginOrRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginOrRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        loginOrRegister = this;

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        binding.loginBtn.setOnClickListener(this::onClick);
        binding.registerBtn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.login_btn:
                intent.setClass(LoginOrRegister.this, Login.class);
                break;
            case R.id.register_btn:
                intent.setClass(LoginOrRegister.this, Register.class);
                break;
        }
        startActivity(intent);
    }
}