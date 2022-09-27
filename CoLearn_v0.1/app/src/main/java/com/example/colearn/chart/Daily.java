package com.example.colearn.chart;

import static com.example.colearn.MainActivity.baseUrl;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.colearn.Chart;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.Me;
import com.example.colearn.R;
import com.example.colearn.adapter.ChartItemsAdapter;
import com.example.colearn.data.ChartData;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.OkHttpUtil;
import com.example.colearn.utils.SPUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Daily extends Fragment {

    public View mView;
    private Context mContext;

    private PieChartBase pieChart;
    private RecyclerView recyclerView_pie;
    private CubicLineChartBase cubicLineChart;
    private static List<ChartData> chartDataArrayList = new ArrayList<>();
    private static List<Float> HotSeq = new ArrayList<>();
    private final static String TAG = "Daily";

    public static Daily getInstance(String title) {
        return new Daily();
    }

    public static void setChartDataList(List<ChartData> chartDataArrayList) {
        Daily.chartDataArrayList = chartDataArrayList;
    }

    public static void setHotSeq(List<Float> hotSeq) {
        Daily.HotSeq = hotSeq;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.daily, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load PieChart
        pieChart = new PieChartBase(mContext, view.findViewById(R.id.daily_chart_pie));

        // test data
        String[] labels = new String[]{"读书", "写字", "练琴", "玩手机", "打瞌睡", "啃手指"};
        String[] ratio = new String[]{"10", "20", "10", "10", "10", "40"};
        String[] length = new String[]{"1.4", "0.9", "0.6", "2.2", "0.5", "0.1"};
        int[] imgIds = new int[]{R.mipmap.reading, R.mipmap.swim, R.mipmap.do_homework,
                R.mipmap.guitar, R.mipmap.badminton, R.mipmap.food_jt};


        for (int i = 0; i < 6; i++) {
            ChartData chartData = new ChartData();
            chartData.setCategory(labels[i]);
            chartData.setImgResId(imgIds[i]);
            chartData.setCdRatio(ratio[i]);
            chartData.setCdLength(length[i]);
            chartDataArrayList.add(chartData);
        }

        pieChart.updateData(chartDataArrayList);
        pieChart.init();

        recyclerView_pie = view.findViewById(R.id.dialy_pie_recycleview);
        recyclerView_pie.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_pie.setAdapter(new ChartItemsAdapter(mContext, chartDataArrayList));

        //cubicChart
        HotSeq = new ArrayList<>();
        for (int j = 0; j < 24; j++) {
            HotSeq.add((float) Math.random() * 2);
        }

        cubicLineChart = new CubicLineChartBase(mContext, getActivity(), view.findViewById(R.id.daily_chart_cubicline));
        cubicLineChart.init();
        cubicLineChart.updateData(Daily.HotSeq);
        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout_d);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    updateData(refreshlayout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void updateData(RefreshLayout refreshlayout) throws Exception {
        Log.d(TAG, "Daily: start updating data");
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
        Call<ResponseBody> call = request.getDailyAcitvities("getDaily",User.getUser().getAccount()
                , String.valueOf(Chart.getYear()), String.valueOf(Chart.getMonth()),String.valueOf(Chart.getDay()));
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
                    Daily.setChartDataList(JSONObject.parseArray(result, ChartData.class));
                    pieChart.updateData(Daily.chartDataArrayList);
                    SPUtils.putString("DailyActivities".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                            , JSON.toJSONString(result), getContext());
                    Message msg = new Message();
                    Me.mHandler.sendMessage(msg);
                    refreshlayout.finishRefresh(1000);
                } else {
                    refreshlayout.finishRefresh(false);
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "post回调失败：" + throwable.getMessage() + "," + throwable.toString());
                refreshlayout.finishRefresh(false);
            }
        });
    }
}