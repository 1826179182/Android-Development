package com.example.colearn.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colearn.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.ColorTemplate;

public class FragPieChart extends SimpleFragment {

    private PieChart chart;

    public static Fragment newInstance() {
        return new FragPieChart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_piechart, container, false);

        chart = v.findViewById(R.id.MpPieChart);
        chart.getDescription().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

        chart.setCenterTextTypeface(tf);
        chart.setCenterText(generateCenterText());
        chart.setCenterTextSize(10f);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        chart.setCenterTextTypeface(tf);

        // set Lable
        chart.setEntryLabelColor(Color.rgb(255, 255, 255));
        chart.setDrawEntryLabels(true);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(50f);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        chart.setData(generatePieData());

        return v;
    }

    private SpannableString generateCenterText() {
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
