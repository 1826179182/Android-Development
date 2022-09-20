package com.example.colearn.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BarChartData {

    private String cdCategory;
    private ArrayList<String> cdLength;
    private Date date;

    public BarChartData() {
    }

    public void setCategory(String category) {
        cdCategory = category;
    }

    public void setCdLength(ArrayList<String> cdLength) {
        this.cdLength = cdLength;
    }

    public String getCdCategory() {
        return cdCategory;
    }

    public ArrayList<String> getCdLength() {
        return cdLength;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getYear() {
        return new SimpleDateFormat("yyyy", Locale.getDefault()).format(date);
    }

    public String getMonth() {
        return new SimpleDateFormat("MM", Locale.getDefault()).format(date);
    }

    public String getDay() {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(date);
    }
}

