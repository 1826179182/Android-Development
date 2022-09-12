package com.example.colearn.utils;

import android.content.Context;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;

public class ChartDownload {
    private Context context;

    public ChartDownload(Context context){}

    public void saveToGallery(Chart chart, String name) {
        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
            Toast.makeText(this.context.getApplicationContext(), "Saving SUCCESSFUL!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.context.getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                    .show();
    }
}
