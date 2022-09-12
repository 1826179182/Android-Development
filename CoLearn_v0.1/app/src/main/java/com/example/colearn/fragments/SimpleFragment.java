package com.example.colearn.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public abstract class SimpleFragment extends Fragment {

    private final String[] mLabels = new String[]{"Company A", "Company B", "Company C", "Company D", "Company E", "Company F"};
    public Typeface tfLight;
    protected Context context;

    public SimpleFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarData generateBarData(int dataSets, float range, int count) {

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

        BarData d = new BarData(sets);
        d.setValueTypeface(tfLight);
        return d;
    }

    protected ScatterData generateScatterData(int dataSets, float range, int count) {

        ArrayList<IScatterDataSet> sets = new ArrayList<>();

        ScatterChart.ScatterShape[] shapes = ScatterChart.ScatterShape.getAllDefaultShapes();

        for (int i = 0; i < dataSets; i++) {

            ArrayList<Entry> entries = new ArrayList<>();

            for (int j = 0; j < count; j++) {
                entries.add(new Entry(j, (float) (Math.random() * range) + range / 4));
            }

            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }

        ScatterData d = new ScatterData(sets);
        d.setValueTypeface(tfLight);
        return d;
    }

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "学习"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "运动"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "低效"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "书法"));


//        for(int i = 0; i < count; i++) {
//            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
//        }

        PieDataSet ds1 = new PieDataSet(entries1, "活动占比");
        ds1.setColors(ColorTemplate.JOYFUL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);
        // 描述线
        ds1.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        ds1.setValueLinePart1Length(0.3f);
        // 数据线
        ds1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        ds1.setValueLinePart2Length(0.6f);

        ds1.setValueLineColor(Color.RED);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tfLight);

        return d;
    }

    protected LineData generateLineData() {

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "sine.txt"), "Sine function");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "cosine.txt"), "Cosine function");

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        // load DataSets from files in assets folder
        sets.add(ds1);
        sets.add(ds2);

        LineData d = new LineData(sets);
        d.setValueTypeface(tfLight);
        return d;
    }

    protected LineData getComplexity() {

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "n.txt"), "O(n)");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "nlogn.txt"), "O(nlogn)");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "square.txt"), "O(n\u00B2)");
        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "three.txt"), "O(n\u00B3)");

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setLineWidth(2.5f);
        ds1.setCircleRadius(3f);
        ds2.setLineWidth(2.5f);
        ds2.setCircleRadius(3f);
        ds3.setLineWidth(2.5f);
        ds3.setCircleRadius(3f);
        ds4.setLineWidth(2.5f);
        ds4.setCircleRadius(3f);


        // load DataSets from files in assets folder
        sets.add(ds1);
        sets.add(ds2);
        sets.add(ds3);
        sets.add(ds4);

        LineData d = new LineData(sets);
        d.setValueTypeface(tfLight);
        return d;
    }

    private String getLabel(int i) {
        return mLabels[i];
    }
}
