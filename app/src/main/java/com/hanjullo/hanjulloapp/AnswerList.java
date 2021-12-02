package com.hanjullo.hanjulloapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerList {
    @PrimaryKey
    public int date;

    @ColumnInfo(name = "daily_how")
    public String dailyHow;

    @ColumnInfo(name = "daily_why")
    public String dailyWhy;

    @ColumnInfo(name = "daily_what")
    public String dailyWhat;

    @ColumnInfo(name = "inside_me")
    public String insideMe;

    @ColumnInfo(name = "behind_me")
    public String behindMe;

    @ColumnInfo(name = "front_me")
    public String frontMe;
}
