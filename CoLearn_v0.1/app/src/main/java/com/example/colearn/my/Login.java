package com.example.colearn.my;

import static com.example.colearn.MainActivity.baseUrl;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.Me;
import com.example.colearn.R;
import com.example.colearn.databinding.ActivityLoginBinding;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.ButtonClickUtils;
import com.example.colearn.utils.IEditTextChangeListener;
import com.example.colearn.utils.OkHttpUtil;
import com.example.colearn.utils.RSAUtils;
import com.example.colearn.utils.SPUtils;
import com.example.colearn.utils.WorksSizeCheckUtil;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private final static String TAG = "Login";
    private static ActivityLoginBinding binding;
    private boolean pwdVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
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
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ButtonClickUtils.isFastClick()) {
                    return;
                }
                try {
                    loginRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.visibilityTouch.setOnClickListener(v -> {
            Log.d(TAG, "visibility img clicked");
            if (pwdVisibility) {
                Log.d(TAG, "visible->invisible");
                binding.visibilityImg.setImageResource(R.mipmap.invisible);
                binding.password.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                pwdVisibility = false;
            } else {
                Log.d(TAG, "invisible->visible");
                binding.visibilityImg.setImageResource(R.mipmap.visible);
                binding.password.setInputType(EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
                pwdVisibility = true;
            }
        });
    }

    /*监听登录按钮变色*/
    public void buttonChangeColor() {
        //创建工具类对象 把要改变颜色的Button先传过去
        WorksSizeCheckUtil.textChangeListener textChangeListener = new WorksSizeCheckUtil.textChangeListener(binding.loginBtn);
        textChangeListener.addAllEditText(binding.account, binding.password);//把所有要监听的EditText都添加进去
        //接口回调 在这里拿到boolean变量 根据isHasContent的值决定 Button应该设置什么颜色
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    binding.loginBtn.setBackgroundResource(com.xuexiang.xui.R.color.xui_config_color_light_blue);
                    binding.loginBtn.setClickable(true);
                } else {
                    binding.loginBtn.setBackgroundResource(android.R.color.darker_gray);
                }
            }
        });
    }

    private void loginRequest() throws Exception {
        Log.d(TAG, "loginRequest: start login");
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
        Call<ResponseBody> call = request.login(binding.account.getText().toString()
                , RSAUtils.encrypt(binding.password.getText().toString()));
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                ResponseBody body = response.body();
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (body == null) return;
                Log.d(TAG, "返回的数据：" + result);
                if (response.code() == 200) {
                    JSONObject data = JSONObject.parseObject(result);
                    Log.d(TAG, "onResponse: login success! token:" + data.get("token"));
                    User.setUser(new User());
                    User.getUser().setAccount(binding.account.getText().toString());
                    User.getUser().setId((String) data.get("id"));
                    User.getUser().setNickname((String) data.get("username"));
                    User.getUser().setGender((String) data.get("gender"));
                    SPUtils.putString("user", new Gson().toJson(User.getUser()), Login.this);
                    SPUtils.putString("token", (String) data.get("token"), Login.this);
                    SPUtils.putString("gender", (String) data.get("gender"), Login.this);
                    SPUtils.putString("id", (String) data.get("id"), Login.this);
                    SPUtils.putString("nickname", (String) data.get("username"), Login.this);
                    Log.d(TAG, "token received:" + (String) data.get("token"));
                    Log.d(TAG, "token loaded:" + SPUtils.getString("token", null, Login.this));
                    Log.d(TAG, "current user=>" + SPUtils.getString("user", null, Login.this));
                    Message msg = new Message();
                    Me.mHandler.sendMessage(msg);
                    finish();
                    LoginOrRegister.loginOrRegister.finish();
                } else {
                    CookieBar.builder(Login.this)
                            .setTitle("登录失败")
                            .setMessage(response.message())
                            .setBackgroundColor(R.color.error)
                            .setLayoutGravity(Gravity.TOP)
                            .show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "post回调失败：" + throwable.getMessage() + "," + throwable.toString());
                CookieBar.builder(Login.this)
                        .setTitle("登录失败")
                        .setMessage("网络错误！请稍后再试。")
                        .setBackgroundColor(R.color.error)
                        .setLayoutGravity(Gravity.TOP)
                        .show();
            }
        });
    }
}