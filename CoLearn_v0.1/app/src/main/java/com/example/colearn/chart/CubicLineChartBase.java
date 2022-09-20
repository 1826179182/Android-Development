package com.example.colearn.chart;

import static android.graphics.Color.BLACK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.colearn.custom.HourXAxisFormatter;
import com.example.colearn.utils.ChartDownload;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CubicLineChartBase {

    private static final int PERMISSION_STORAGE = 0;
    private Context context;
    private Activity activity;
    private LineChart chart;
    private BarData barData;
    private Typeface tfLight;

    public CubicLineChartBase(Context context, Activity activity, LineChart chart) {
        this.context = context;
        this.activity = activity;
        this.chart = chart;
        this.tfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

    }

    public void init() {

        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.rgb(250, 250, 250));

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setDrawBorders(true);
        chart.setDrawGridBackground(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setMaxHighlightDistance(300);

        ValueFormatter xAxisFormatter = new HourXAxisFormatter();
        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        x.setAxisMinimum(0f);
        x.setLabelCount(24, false);
        x.setTextColor(BLACK);
        x.setTypeface(tfLight);
        x.setGranularity(2f);
        x.setDrawLabels(true);
        x.setDrawGridLines(true);
        x.setValueFormatter(xAxisFormatter);
        x.enableAxisLineDashedLine(10F, 10F, 0F);


        YAxis y = chart.getAxisLeft();
        y.setTypeface(tfLight);
        y.setLabelCount(24, false);
        y.setTextColor(BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setGranularity(3f);
        y.setAxisLineColor(Color.WHITE);
        y.setDrawLabels(true);
        y.setDrawGridLines(true);
        y.enableAxisLineDashedLine(10F, 10F, 0F);


        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        // don't forget to refresh the drawing

        chart.invalidate();
    }

    public void updateData(float[] hotSeq){
        if(hotSeq.length!=24){
            Log.d("CubicLineChartBase","Data not valid!");
        }
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < hotSeq.length; i++) {
            values.add(new Entry(i, hotSeq[i]));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "dataset_1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.rgb(223, 82, 38));
            set1.setFillColor(Color.rgb(239, 191, 171));
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
        chart.invalidate();
    }

    public void setPinched() {
        if (chart.isPinchZoomEnabled())
            chart.setPinchZoom(false);
        else
            chart.setPinchZoom(true);
        chart.invalidate();
    }


    public void saveChart() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            saveToGallery();
        } else {
            requestStoragePermission(chart);
        }
    }

    protected void saveToGallery() {
        ChartDownload chartDownload = new ChartDownload(context);
        chartDownload.saveToGallery(chart, "CubicLineChartActivity");
    }

    protected void requestStoragePermission(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(view, "Write permission is required to save image to gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                        }
                    }).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Permission Required!", Toast.LENGTH_SHORT)
                    .show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
        }
    }

}

