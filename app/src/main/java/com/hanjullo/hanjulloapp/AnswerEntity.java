package com.hanjullo.hanjulloapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerEntity {
    @PrimaryKey
    public int date;

    public AnswerEntity(int date) {
        this.date = date;
    }

    public void setDailyA(int dailyA) {
        this.dailyA = dailyA;
    }

    public void setDailyB(int dailyB) {
        this.dailyB = dailyB;
    }

    public void setDailyC(int dailyC) {
        this.dailyC = dailyC;
    }

    public void setDailyStepB(String dailyStepB) {
        this.dailyStepB = dailyStepB;
    }

    public void setDailyExtra(String dailyExtra) {
        this.dailyExtra = dailyExtra;
    }

    @ColumnInfo(name = "daily_A")
    public int dailyA;

    @ColumnInfo(name = "daily_B")
    public int dailyB;

    @ColumnInfo(name = "daily_C")
    public int dailyC;

    @ColumnInfo(name = "daily_stepB")
    public String dailyStepB;

    @ColumnInfo(name = "daily_extra")
    public String dailyExtra;

    @ColumnInfo(name = "inside_me")
    public String insideMe;

    @ColumnInfo(name = "behind_me")
    public String behindMe;

    @ColumnInfo(name = "front_me")
    public String frontMe;
}
