package com.molo17.customizablecalendar.library.model;

import org.threeten.bp.LocalDate;

import java.util.Calendar;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CalendarItem {
    private long id;
    private LocalDate dateTime;
    private boolean selected;

//    public CalendarItem(Calendar calendar) {
//        this.dateTime = LocalDate.of(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH) + 1,
//                calendar.get(Calendar.DAY_OF_MONTH));
//        this.id = calendar.getTimeInMillis();
//    }

//    public CalendarItem(LocalDate dateTime) {
//        this.dateTime = dateTime;
//        this.id = dateTime.toEpochDay();
//    }

    public CalendarItem(int day, int month, int year) {
        this.dateTime = LocalDate.of(year, month, day);
        this.id = dateTime.toEpochDay();
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelectable() {
        return selected;
    }

    public void setSelectable(boolean selected) {
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int compareTo(LocalDate today) {
        return dateTime.compareTo(today);
    }

    public String getDayString() {
        return getDay() + "";
    }

    public int getDay() {
        return dateTime.getDayOfMonth();
    }

    public int getMonth() {
        return dateTime.getMonthValue();
    }

    public int getYear() {
        return dateTime.getYear();
    }
}
