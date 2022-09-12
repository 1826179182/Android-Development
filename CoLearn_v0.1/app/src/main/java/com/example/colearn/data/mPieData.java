package com.example.colearn.data;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class mPieData {

    private String pieChartDataLabel;
    private ArrayList<PieEntry> pieEntries;

    public mPieData() {
        pieEntries = new ArrayList<>();
    }

    public void addEntry(float data, String label) {
        pieEntries.add(new PieEntry((data), label));
    }

    public PieData generateData() {
        PieDataSet pieDataSet = new PieDataSet(pieEntries, pieChartDataLabel);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);

        // 描述线
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.3f);

        // 数据线
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLinePart2Length(0.6f);

//        pieDataSet.setValueLineColor(Color.RED);
        pieDataSet.setUsingSliceColorAsValueLineColor(true);

        PieData d = new PieData(pieDataSet);
//            d.setValueTypeface(tfLight);
        return d;
    }

    public void setPieChartDataLabel(String label) {
        pieChartDataLabel = label;

    }
}
