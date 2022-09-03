package com.example.colearn.my;

import static com.example.colearn.MainActivity.baseUrl;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.R;
import com.example.colearn.components.Data;
import com.example.colearn.databinding.ActivityRegisterBinding;
import com.example.colearn.utils.AESUtil;
import com.example.colearn.utils.IEditTextChangeListener;
import com.example.colearn.utils.OkHttpUtil;
import com.example.colearn.utils.WorksSizeCheckUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.xuexiang.xui.widget.popupwindow.ViewTooltip;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    private final static String TAG = "Register";
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        buttonChangeColor();
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerRequest();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*监听登录按钮变色*/
    public void buttonChangeColor() {
        //创建工具类对象 把要改变颜色的Button先传过去
        WorksSizeCheckUtil.textChangeListener textChangeListener = new WorksSizeCheckUtil.textChangeListener(binding.registerBtn);
        textChangeListener.addAllEditText(binding.account, binding.password, binding.confirmPassword);//把所有要监听的EditText都添加进去
        //接口回调 在这里拿到boolean变量 根据isHasContent的值决定 Button应该设置什么颜色
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    binding.registerBtn.setBackgroundResource(com.xuexiang.xui.R.color.xui_config_color_light_blue);
                    binding.registerBtn.setClickable(true);
                } else {
                    binding.registerBtn.setBackgroundResource(android.R.color.darker_gray);
                }
            }
        });
    }

    private void registerRequest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())) {
            //构建Retrofit实例
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkHttpUtil.getOkHttpClient())
                    //设置网络请求BaseUrl地址
                    .baseUrl(baseUrl)
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //创建网络请求接口对象实例
            CoLearnRequestInterface request = retrofit.create(CoLearnRequestInterface.class);
            //对发送请求进行封装
            Call<Data<JSON>> call = request.register(binding.account.getText().toString()
                    , AESUtil.encryptECB(binding.password.getText().toString().getBytes(StandardCharsets.UTF_8)));
            //步骤7:发送网络请求(异步)
            call.enqueue(new Callback<Data<JSON>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<Data<JSON>> call, Response<Data<JSON>> response) {
                    //步骤8：请求处理,输出结果
                    Object body = response.body();
                    if (body == null) return;
                    Log.d(TAG, "返回的数据：" + response.body().toString());
                }

                //请求失败时回调
                @Override
                public void onFailure(Call<Data<JSON>> call, Throwable throwable) {
                    Log.d(TAG, "post回调失败：" + throwable.getMessage() + "," + throwable.toString());
                }
            });

        } else {
            ViewTooltip
                    .on(binding.password)
                    .color(Color.rgb(255, 100, 100))
                    .position(ViewTooltip.Position.BOTTOM)
                    .text("两次密码输入不一致")
                    .clickToHide(true)
                    .autoHide(true, 2000)
                    .animation(new ViewTooltip.FadeTooltipAnimation(500))
                    .show();
            ViewTooltip
                    .on(binding.confirmPassword)
                    .color(Color.rgb(255, 100, 100))
                    .position(ViewTooltip.Position.BOTTOM)
                    .text("两次密码输入不一致")
                    .clickToHide(true)
                    .autoHide(true, 2000)
                    .animation(new ViewTooltip.FadeTooltipAnimation(500))
                    .show();
        }
    }
}