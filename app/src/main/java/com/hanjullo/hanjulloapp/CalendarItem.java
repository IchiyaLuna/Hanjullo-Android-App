package com.hanjullo.hanjulloapp;

import java.time.LocalDate;

public class CalendarItem {

    private LocalDate date;

    public CalendarItem(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDoW() {
        return date.getDayOfWeek().getValue() - 1;
    }

    public String getDateString() {
        String month = Integer.toString(date.getMonthValue());
        String day = Integer.toString(date.getDayOfMonth());
        return month + "/" + day;
    }

    public String getDateString(String separator) {
        String month = Integer.toString(date.getMonthValue());
        String day = Integer.toString(date.getDayOfMonth());
        return month + separator + day;
    }
}
