package com.example.colearn.pojo;

import org.joda.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Plant {
    public final static int SEED = -1;
    public final static int GROWING = 0;
    public final static int PLANT = 1;

    private int id;
    private int state = -1;
    private String beginTime;
    private String finishTime;

    public Plant() {
    }

    public Plant(int id) {
        this.id = id;
        beginTime = LocalDate.now().toString() + ' ' + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", state=" + state +
                ", beginTime='" + beginTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                '}';
    }

}
