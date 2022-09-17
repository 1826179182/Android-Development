package com.example.colearn;

import static com.luck.picture.lib.thread.PictureThreadUtils.runOnUiThread;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.colearn.pojo.User;
import com.example.colearn.databinding.MeBinding;
import com.example.colearn.my.DataSynchronize;
import com.example.colearn.my.HabitManager;
import com.example.colearn.my.LoginOrRegister;
import com.example.colearn.my.PersonalInformation;
import com.example.colearn.utils.ButtonClickUtils;
import com.example.colearn.utils.SPUtils;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;
import com.xuexiang.xui.widget.toast.XToast;

public class Me extends androidx.fragment.app.Fragment implements View.OnClickListener {

    private static MeBinding binding;
    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(User.getUser()!=null){
                binding.loginStatue.setVisibility(View.GONE);
                binding.dataSynchronize.setVisibility(View.VISIBLE);
                binding.exit.setVisibility(View.VISIBLE);
            } else {
                binding.loginStatue.setVisibility(View.VISIBLE);
                binding.dataSynchronize.setVisibility(View.GONE);
                binding.exit.setVisibility(View.GONE);
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        if(User.getUser()!=null){
            binding.loginStatue.setVisibility(View.GONE);
            binding.dataSynchronize.setVisibility(View.VISIBLE);
            binding.exit.setVisibility(View.VISIBLE);
        } else {
            binding.loginStatue.setVisibility(View.VISIBLE);
            binding.dataSynchronize.setVisibility(View.GONE);
            binding.exit.setVisibility(View.GONE);
        }


        binding.avatar.setOnClickListener(this::onClick);
        binding.settings.setOnClickListener(this::onClick);
        binding.shareApp.setOnClickListener(this::onClick);
        binding.relativeUs.setOnClickListener(this::onClick);
        binding.habitManager.setOnClickListener(this::onClick);
        binding.privacy.setOnClickListener(this::onClick);
        binding.exit.setOnClickListener(this::onClick);
        binding.dataSynchronize.setOnClickListener(this::onClick);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (ButtonClickUtils.isFastClick()) { return; }

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.avatar:
                if (User.getUser()!=null) {
                    intent.setClass(getContext(), PersonalInformation.class);
                } else {
                    intent.setClass(getContext(), LoginOrRegister.class);
                }
                startActivity(intent);
                break;
            case R.id.share_app:
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "CoLearn，助力儿童的健康成长！");
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.relative_us:
                try {
                    XToast.normal(getContext(), "跳转添加qq").show();
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1826179182";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    XToast.normal(getContext(), "请检查是否安装QQ").show();
                }
                break;
            case R.id.habit_manager:
                intent.setClass(getContext(), HabitManager.class);
                startActivity(intent);
                break;
            case R.id.exit:
                User.setUser(null);
                CookieBar.builder(getActivity())
                        .setMessage("退出登录成功！")
                        .setBackgroundColor(R.color.checked_bg)
                        .setLayoutGravity(Gravity.TOP)
                        .show();
                SPUtils.putString("user",null,getContext());
                Message msg =new Message();
                Me.mHandler.sendMessage(msg);
                break;
            case R.id.data_synchronize:
                intent.setClass(getContext(), DataSynchronize.class);
                startActivity(intent);
                break;

        }
    }
}
