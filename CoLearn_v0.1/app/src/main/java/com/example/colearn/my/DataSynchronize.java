package com.example.colearn.my;

import static com.example.colearn.MainActivity.baseUrl;
import static com.xuexiang.xui.XUI.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.chart.Daily;
import com.example.colearn.data.ChartData;
import com.example.colearn.databinding.ActivityDataSynchronizeBinding;
import com.example.colearn.pojo.CheckInRecord;
import com.example.colearn.pojo.Habit;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.OkHttpUtil;
import com.example.colearn.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

import java.io.IOException;

import okhttp3.ResponseBody;
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

    /**
     * 恢复同步数据
     */
    public void dataRecovery() {
        getList("getTodoList");
        getList("getCheckInHistory");
        getList("getPlantsList");
//        getList("getDailyActivityDetails");
//        getList("getDailyHotSeq");
    }


    /**
     * 同步数据
     */
    public void dataSynchronize() {
        saveList("saveTodoList");
        saveList("saveCheckInHistory");
        saveList("savePlantsList");
    }

    /**
     * 同步list数据
     *
     * @param path list类型
     */
    private void saveList(String path) {
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
        Call<ResponseBody> call = null;
        //对发送请求进行封装
        switch (path) {
            case "saveTodoList":
                call = request.saveList(JSONArray.parseArray(JSON.toJSONString(Home.getAllTodoList())), path);
                break;
            case "saveCheckInHistory":
                call = request.saveList(JSONArray.parseArray(JSON.toJSONString(Home.getCheckInRecords())), path);
                break;
            case "savePlantsList":
                call = request.saveList(JSONArray.parseArray(JSON.toJSONString(Home.getPlants())), path);
                break;
        }

        //步骤:发送网络请求(异步)
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //步骤8：请求处理,输出结果
                String result = null;
                ResponseBody body = response.body();
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (body == null) return;
                Log.d(TAG, "返回的数据：" + result);
                CookieBar.builder(DataSynchronize.this)
                        .setTitle(response.message())
                        .setMessage(response.message())
                        .setBackgroundColor(R.color.error)
                        .setLayoutGravity(Gravity.TOP)
                        .show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "post回调失败：" + throwable.getMessage() + "," + throwable.toString());
            }
        });
    }

    /**
     * 恢复list数据
     *
     * @param path list类型
     */
    private void getList(String path) {
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
        Call<ResponseBody> call = call = request.getList(path);
        //步骤:发送网络请求(异步)
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //步骤8：请求处理,输出结果
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
                    switch (path) {
                        case "getTodoList":
                            Home.setAllTodoList(JSONObject.parseArray(result, Habit.class));
                            SPUtils.putString("todoList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                    , JSON.toJSONString(Home.getAllTodoList()), getContext());

                            Home.getTodoAdapter().notifyDataSetChanged();
                            Home.updateAllTodoList(Home.selectDate.getMonthOfYear(), Home.selectDate);
                            break;
                        case "getCheckInHistory":
                            Home.setCheckInRecords(JSONObject.parseArray(result, CheckInRecord.class));
                            SPUtils.putString("HistoryCheckIn".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                    , JSON.toJSONString(Home.getCheckInRecords()), getContext());
                            break;
                        case "getPlantsList":
                            SPUtils.putString("plants".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                    , JSON.toJSONString(result), getContext());
                            break;
//                        case "getDailyActivityDetails":
//                            Daily.setChartDataList(JSONObject.parseArray(result, ChartData.class));
//                            SPUtils.putString("DailyActivities".concat(User.getUser() == null ? "" : User.getUser().getAccount())
//                                    , JSON.toJSONString(result), getContext());
//                            break;
//                        case "getDailyHotSeq":
//                            Daily.setHotSeq(JSONObject.parseArray(result, Float.class));
//                            SPUtils.putString("DailyActivities".concat(User.getUser() == null ? "" : User.getUser().getAccount())
//                                    , JSON.toJSONString(result), getContext());
//                            break;
                    }
                    CookieBar.builder(DataSynchronize.this)
                            .setTitle(response.message())
                            .setMessage(response.message())
                            .setBackgroundColor(R.color.error)
                            .setLayoutGravity(Gravity.TOP)
                            .show();
                } else {
                    CookieBar.builder(DataSynchronize.this)
                            .setTitle(response.message())
                            .setMessage("出了点小问题，请稍后再试。")
                            .setBackgroundColor(R.color.error)
                            .setLayoutGravity(Gravity.TOP)
                            .show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
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