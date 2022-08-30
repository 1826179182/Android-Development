package com.example.colearn.components;

public class CheckInRecord {
    private String date;
    private String time;
    private int todo;
    private int hasDone;
    private int expression;

    public CheckInRecord(String date, String time, int todo, int hasDone, int expression) {
        this.date = date;
        this.time = time;
        this.todo = todo;
        this.hasDone = hasDone;
        this.expression = expression;
    }

    public CheckInRecord() {
    }

    @Override
    public String toString() {
        return "CheckInRecord{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", todo=" + todo +
                ", hasDone=" + hasDone +
                ", expression=" + expression +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getHasDone() {
        return hasDone;
    }

    public void setHasDone(int hasDone) {
        this.hasDone = hasDone;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }
}
