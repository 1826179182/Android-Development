package com.example.colearn.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.example.colearn.R;
import com.example.colearn.custom.RadarMarkerView;
import com.example.colearn.data.ChartData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

public class RadarChartBase {
    private Context context;
    private Activity activity;
    private RadarChart chart;
    private RadarData radarData;
    private ArrayList<String> mLabels;
    private Typeface tfLight;

    public RadarChartBase(Context context, Activity activity, RadarChart chart) {
        this.context = context;
        this.activity = activity;
        this.chart = chart;
        this.tfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
    }

    public void init() {
        chart.setBackgroundColor(Color.rgb(255, 255, 255));

        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        MarkerView mv = new RadarMarkerView(context, R.layout.radar_markerview);
        mv.setChartView(chart);
        chart.setMarker(mv);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);

        YAxis yAxis = chart.getYAxis();
        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);
    }

    public void updateData(ArrayList<ChartData> chartDataArrayList_lastW, ArrayList<ChartData> chartDataArrayList_thisW) {
        setData(chartDataArrayList_lastW, chartDataArrayList_thisW);
    }

    private void setData(ArrayList<ChartData> chartDataArrayList_lastW, ArrayList<ChartData> chartDataArrayList_thisW) {

        mLabels = new ArrayList<>();

        float mul = 80;
        float min = 20;
        int cnt = chartDataArrayList_lastW.size();

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            mLabels.add(chartDataArrayList_lastW.get(i).getCdCategory());

//            float val1 = (float) (Math.random() * mul) + min;
            float val1 = Float.parseFloat(chartDataArrayList_lastW.get(i).getCdRatio());
            entries1.add(new RadarEntry(val1));

//            float val2 = (float) (Math.random() * mul) + min;
            float val2 = Float.parseFloat(chartDataArrayList_thisW.get(i).getCdRatio());
            entries2.add(new RadarEntry(val2));
        }

        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return mLabels.get((int) value % mLabels.size());
            }
        });

        RadarDataSet set1 = new RadarDataSet(entries1, "上周");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "本周");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }
}
