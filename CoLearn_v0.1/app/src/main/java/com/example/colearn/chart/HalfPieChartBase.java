package com.example.colearn.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.ColorTemplate;

public class HalfPieChartBase {
    private Context context;
    private Activity activity;
    private PieChart chart;
    private PieData halfPieData;
    private Typeface mTfLight;

    public HalfPieChartBase(Context context, Activity activity, PieChart chart) {
        this.context = context;
        this.activity = activity;
        this.chart = chart;
    }

    public void init() {

        mTfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

//        moveOffScreen();

        chart.getDescription().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setCenterTextTypeface(mTfLight);//设置字体
        chart.setCenterText(generateCenterSpannableText());//中间显示文字

        chart.setDrawHoleEnabled(true);//true设置绘图孔启用
        chart.setHoleColor(Color.WHITE);//设置绘图孔颜色

        chart.setTransparentCircleColor(Color.WHITE);//
        chart.setTransparentCircleAlpha(110);//透明度

        chart.setHoleRadius(58f);//中间圆的半径
        chart.setTransparentCircleRadius(61f);//

        chart.setDrawCenterText(true);//中心是否允许画文字

        chart.setRotationEnabled(false);//整个视图是否旋转
        chart.setHighlightPerTapEnabled(true);//true点击各个板块会向上突起一点

        chart.setMaxAngle(180f); // 显示一半
        chart.setRotationAngle(180f);
        chart.setCenterTextOffset(0, -20);//中间文字向上偏移20

        // set Lable
        chart.setEntryLabelColor(Color.rgb(0, 0, 0));
        chart.setDrawEntryLabels(true);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        chart.setData(halfPieData);
    }

    public void setHalfPieData(PieData halfPieData) {
        this.halfPieData = halfPieData;
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("CoLearn\nPowered by Group4");
        s.setSpan(new RelativeSizeSpan(1.8f), 0, 7, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 7, s.length() - 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 7, s.length() - 8, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 7, s.length() - 8, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 7, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), s.length() - 8, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 7, s.length(), 0);
        return s;
    }

    private void moveOffScreen() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int offsetV = (int) (height * 0.15);
        int offsetH = (int) (width * 0.08);

        LinearLayout.LayoutParams rlParams = (LinearLayout.LayoutParams) chart.getLayoutParams();
        rlParams.setMargins(0, 0, 0, -offsetV);
        chart.setLayoutParams(rlParams);
    }
}
