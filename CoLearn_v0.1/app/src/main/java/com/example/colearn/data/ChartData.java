package com.example.colearn.data;

public class ChartData {

    private int cdImgResId;
    private String cdCategory;
    private String cdLength;
    private String cdRatio;


    public ChartData() {}

    public void setImgResId(int resId) {
        cdImgResId = resId;
    }


    public void setCategory(String category){
        cdCategory=category;
    }


    public void setCdLength(String cdLength) {
        this.cdLength = cdLength;
    }

    public void setCdRatio(String cdRatio) {
        this.cdRatio = cdRatio;
    }

    public int getImgResId() {
        return cdImgResId;
    }

    public String getCdCategory(){
        return cdCategory;
    }

    public String getCdLength() {
        return cdLength;
    }

    public String getCdRatio() {
        return cdRatio;
    }
}
