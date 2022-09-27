package com.example.colearn.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colearn.MainActivity;
import com.example.colearn.R;
import com.example.colearn.databinding.ActivityDevModeBinding;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;

public class DevMode extends AppCompatActivity implements View.OnClickListener {

    private ActivityDevModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevModeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor("#eef3f7")
                .init();

        binding.editTxtVideoURL.setOnClickListener(this::onClick);
        binding.btnDevApply.setOnClickListener(this::onClick);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dev_apply:
                MainActivity.updateUrl(binding.editTxtVideoURL.getText().toString());
                SPUtils.putString("rtmpURL", binding.editTxtVideoURL.getText().toString(), this);
                Toast.makeText(this, "已应用", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        binding.editTxtVideoURL.setText(SPUtils.getString("rtmpURL", null, this));
        binding.devModeToken.setText(SPUtils.getString("token", null, this));
    }
}