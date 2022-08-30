package com.example.colearn.my;

import static com.example.colearn.MainActivity.baseUrl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.components.CheckInRecord;
import com.example.colearn.components.Data;
import com.example.colearn.components.Habit;
import com.example.colearn.databinding.ActivityDataSynchronizeBinding;
import com.example.colearn.utils.OkHttpUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSynchronize extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "DataSynchronize";
    private static boolean autoSynchronize = false;
    private ActivityDataSynchronizeBinding binding;

    public static boolean isAutoSynchronize() {
        return autoSynchronize;
    }

    public static void dataRecovery() {
        getCheckInHistoryRequest();
        getTodoListRequest();
    }

    public static void dataSynchronize() {
        saveCheckInHistoryRequest();
        saveTodoListRequest();
    }

    private static void saveTodoListRequest() {
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

        List<Habit> allTodoList = Home.getAllTodoList();
        //对发送请求进行封装
        Call<Data<JSON>> call = request.saveTodoList(JSONArray.parseArray(JSON.toJSONString(allTodoList)));
        //步骤:发送网络请求(异步)
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
    }

    private static void saveCheckInHistoryRequest() {
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

        List<CheckInRecord> checkInRecords = Home.getCheckInRecords();
        //对发送请求进行封装
        Call<Data<JSON>> call = request.saveTodoList(JSONArray.parseArray(JSON.toJSONString(checkInRecords)));
        //步骤:发送网络请求(异步)
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
    }

    private static void getTodoListRequest() {
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
        Call<Data<JSON>> call = request.getTodoList();
        //步骤:发送网络请求(异步)
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
    }

    private static void getCheckInHistoryRequest() {
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
        Call<Data<JSON>> call = request.getCheckInList();
        //步骤:发送网络请求(异步)
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataSynchronizeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    autoSynchronize = true;
                } else {
                    autoSynchronize = false;
                }
            }
        });
        binding.synchronizeNow.setOnClickListener(this::onClick);
        binding.recoveryData.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.synchronize_now:
                dataSynchronize();
                break;
            case R.id.recovery_data:
                dataRecovery();
                break;
        }
    }
}