package com.example.colearn.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class HourXAxisFormatter extends ValueFormatter {

    private final String[] mMonths = new String[]{
            "1时", "2时", "3时", "4时", "5时", "6时", "7时", "8时", "9时", "10时", "11时", "12时", "13时", "14时", "15时", "16时", "17时", "18时", "19时", "20时", "21时", "22时", "23时", "24时"
    };

    public HourXAxisFormatter() {
        // take parameters to change behavior of formatter
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

//        float percent = value / axis.mAxisRange;
        return mMonths[(int) (value)];
    }
}
