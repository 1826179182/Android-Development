package com.example.colearn.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.SeekBar;

import androidx.core.content.ContextCompat;

import com.example.colearn.custom.DayAxisValueFormatter;
import com.example.colearn.custom.MyValueFormatter;
import com.example.colearn.data.BarChartData;
import com.example.colearn.entity.MyBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartBase implements SeekBar.OnSeekBarChangeListener {

    private Context context;
    private Activity activity;
    private MyBarChart chart;
    private BarData barData;
    private String[] mLabels;
    private Typeface tfLight;
    private Typeface tfRegular;

    public BarChartBase(Context context, Activity activity, MyBarChart chart, String[] labels) {
        this.context = context;
        this.activity = activity;
        this.chart = chart;
        this.mLabels = labels;
        this.tfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
        this.tfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    public void init() {

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(3f); // only intervals of 1 day
        xAxis.setLabelCount(24);
        xAxis.setValueFormatter(xAxisFormatter);


        ValueFormatter custom = new MyValueFormatter("h");

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(12f);
        l.setYEntrySpace(10f);

//        generateBarData(1, 2000, 5);
    }

    public void generateBarData(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();

        for (int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<>();

            for (int j = 0; j < count; j++) {
                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        barData = new BarData(sets);
    }

    public void updateDate(BarChartData barChartData) {

        String year = barChartData.getYear();
        String month = barChartData.getMonth();

        ArrayList<BarEntry> values = new ArrayList<>();

        int i = 0;
        for (String str : barChartData.getCdLength()) {
            float val = Float.parseFloat(str);
//            values.add(new BarEntry(i, val, context.getResources().getDrawable(R.drawable.star)));
            values.add(new BarEntry(i++, val));
        }

        BarDataSet set1;

        set1 = new BarDataSet(values, year + "年" + month + "月");

        set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

        int startColor1 = ContextCompat.getColor(context, android.R.color.holo_orange_light);
        int startColor2 = ContextCompat.getColor(context, android.R.color.holo_blue_light);
        int startColor3 = ContextCompat.getColor(context, android.R.color.holo_orange_light);
        int startColor4 = ContextCompat.getColor(context, android.R.color.holo_green_light);
        int startColor5 = ContextCompat.getColor(context, android.R.color.holo_red_light);
        int endColor1 = ContextCompat.getColor(context, android.R.color.holo_blue_dark);
        int endColor2 = ContextCompat.getColor(context, android.R.color.holo_purple);
        int endColor3 = ContextCompat.getColor(context, android.R.color.holo_green_dark);
        int endColor4 = ContextCompat.getColor(context, android.R.color.holo_red_dark);
        int endColor5 = ContextCompat.getColor(context, android.R.color.holo_orange_dark);

        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(startColor1, endColor1));
        gradientColors.add(new GradientColor(startColor2, endColor2));
        gradientColors.add(new GradientColor(startColor3, endColor3));
        gradientColors.add(new GradientColor(startColor4, endColor4));
        gradientColors.add(new GradientColor(startColor5, endColor5));

        set1.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(5f);
        data.setValueTypeface(tfRegular);
        data.setBarWidth(0.9f);

        chart.setData(data);
//        }
        chart.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        setData(7, 1);
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private String getLabel(int i) {
        return mLabels[i];
    }
}
