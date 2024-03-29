package com.example.colearn.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.colearn.R;
import com.example.colearn.custom.MyMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class FragBarChart extends SimpleFragment {

    private BarChart chart;

    @NonNull
    public static Fragment newInstance() {
        return new FragBarChart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_bar, container, false);

        // create a new chart object
//        chart.fitScreen();
        chart = new BarChart(getActivity());
        chart.getDescription().setEnabled(false);
//        chart.setOnChartGestureListener(this);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

        chart.setData(generateBarData(1, 20000, 7));

        Legend l = chart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

//        // programmatically add the chart
//        FrameLayout parent = v.findViewById(R.id.parentLayout);
//        parent.addView(chart);

        return v;
    }

}
