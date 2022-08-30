package com.example.colearn.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colearn.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class FragHalfPieChart extends SimpleFragment {

    protected final String[] parties = new String[]{
            "阅读", "看手机", "练习书法", "练琴", "打瞌睡"
    };
    private Typeface mTfLight;
    private Typeface mTfRegular;
    private PieChart chart;

    public static Fragment newInstance() {
        return new FragHalfPieChart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragHalfPieChart", "is Creating");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_piechart, container, false);

        chart = v.findViewById(R.id.MpPieChart);
        chart.setBackgroundColor(Color.WHITE);

        moveOffScreen();

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setCenterTextTypeface(mTfLight);//设置字体
        chart.setCenterText(generateCenterSpannableText());//中间显示文字

        chart.setDrawHoleEnabled(true);//true设置绘图孔启用
        chart.setHoleColor(Color.WHITE);//设置绘图孔颜色

        chart.setTransparentCircleColor(Color.WHITE);//
        chart.setTransparentCircleAlpha(110);//透明度

        chart.setHoleRadius(58f);//中间圆的半径
        chart.setTransparentCircleRadius(61f);//

        chart.setDrawCenterText(true);//中心是否允许画文字

        chart.setRotationEnabled(false);//整个视图是否旋转
        chart.setHighlightPerTapEnabled(true);//true点击各个板块会向上突起一点

        chart.setMaxAngle(180f); // 显示一半
        chart.setRotationAngle(180f);
        chart.setCenterTextOffset(0, -20);//中间文字向上偏移20
        setData(5, 100);//数据

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(2f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTypeface(mTfRegular);
        chart.setEntryLabelTextSize(12f);

        return v;
    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            values.add(new PieEntry((float) ((Math.random() * range) + range / 5), parties[i % parties.length]));
        }

        PieDataSet dataSet = new PieDataSet(values, "今日状况");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        chart.setData(data);

        chart.invalidate();
    }


    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("CoLearn\nPowered by Group4");
        s.setSpan(new RelativeSizeSpan(1.8f), 0, 7, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 7, s.length() - 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 7, s.length() - 8, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 7, s.length() - 8, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 7, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), s.length() - 7, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 7, s.length(), 0);
        return s;
    }

    private void moveOffScreen() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int offset = (int) (height * 0.15); /* percent to move */

        FrameLayout.LayoutParams rlParams = (FrameLayout.LayoutParams) chart.getLayoutParams();
        rlParams.setMargins(0, 0, 0, -offset);
        chart.setLayoutParams(rlParams);
    }
}