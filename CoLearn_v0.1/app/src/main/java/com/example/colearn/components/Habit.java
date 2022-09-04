package com.example.colearn.components;

public class Habit {
    public final static int TODO = 1;
    public final static int DONE = 0;
    public final static int UNALLOCATED = -1;

    private String habitName = "";
    private String habitType;
    private String todoDate = "无";

    ;
    private String finishTime;
    private int statue;
    private int habitIcon;
    private int habitIconRes;
    private String frequency = "只提醒一次";
    private String remindTime = "无";
    public Habit(String habitName, String habitType, String finishTime, int habitIcon, int habitIconRes, String todoDate, String frequency, String remindTime, int statue) {
        this.habitName = habitName;
        this.habitType = habitType;
        this.finishTime = finishTime;
        this.habitIcon = habitIcon;
        this.habitIconRes = habitIconRes;
        this.todoDate = todoDate;
        this.frequency = frequency;
        this.remindTime = remindTime;
        this.statue = statue;
    }
    public Habit() {
    }

    public static int getTODO() {
        return TODO;
    }

    public static int getDONE() {
        return DONE;
    }

    public static int getUNALLOCATED() {
        return UNALLOCATED;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "habitName='" + habitName + '\'' +
                ", habitType='" + habitType + '\'' +
                ", todoDate='" + todoDate + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", statue=" + statue +
                ", habitIcon=" + habitIcon +
                ", habitIconRes=" + habitIconRes +
                ", frequency='" + frequency + '\'' +
                ", remindTime='" + remindTime + '\'' +
                '}';
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitType() {
        return habitType;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public int getHabitIcon() {
        return habitIcon;
    }


    public void setHabitIcon(int habitIcon) {
        this.habitIcon = habitIcon;
    }

    public int getHabitIconRes() {
        return habitIconRes;
    }

    public void setHabitIconRes(int habitIconRes) {
        this.habitIconRes = habitIconRes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
