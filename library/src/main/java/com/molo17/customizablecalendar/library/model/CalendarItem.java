package com.molo17.customizablecalendar.library.model;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CalendarItem {
    private long id;
    private DateTime dateTime;
    private boolean selected;

    public CalendarItem(Calendar calendar) {
        this.dateTime = new DateTime(calendar);
        this.id = calendar.getTimeInMillis();
    }

    public CalendarItem(DateTime dateTime) {
        this.dateTime = dateTime;
        this.id = dateTime.getMillis();
    }

    public CalendarItem(int day, int month, int year) {
        this.dateTime = new DateTime(year, month, day, 0, 0);
        this.id = dateTime.getMillis();
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
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

    public int compareTo(DateTime today) {
        return dateTime.compareTo(today);
    }

    public String getDayString() {
        return getDay() + "";
    }

    public int getDay() {
        return dateTime.getDayOfMonth();
    }

    public int getMonth() {
        return dateTime.getMonthOfYear();
    }

    public int getYear() {
        return dateTime.getYear();
    }
}
