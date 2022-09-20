package com.example.colearn.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.colearn.data.ChartData;
import com.example.colearn.data.mPieData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartBase {
    private Context context;
    private PieChart chart;
    private PieData pieData;
    private Typeface mTfLight;

    public PieChartBase(Context context, PieChart chart) {
        this.context = context;
        this.chart = chart;
    }

    public void init() {
        chart.getDescription().setEnabled(false);

        mTfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

        chart.setCenterTextTypeface(mTfLight);
        chart.setCenterText(generateCenterSpannableText());
        chart.setCenterTextSize(10f);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        chart.setCenterTextTypeface(mTfLight);

        // set Lable
        chart.setEntryLabelColor(Color.rgb(0, 0, 0));
        chart.setDrawEntryLabels(true);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(50f);
        chart.setTransparentCircleAlpha(87);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);

        chart.setData(pieData);
    }

    public void updateData(List<ChartData> chartDataArrayList){
        mPieData mPieData = new mPieData();
        for (ChartData chardata: chartDataArrayList) {
            mPieData.addEntry(Float.parseFloat(chardata.getCdRatio()),chardata.getCdCategory());
        }
        setPieData(mPieData.generateData());
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private void setPieData(PieData pieData) {
        this.pieData = pieData;
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
}
