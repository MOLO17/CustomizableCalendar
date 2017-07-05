package com.molo17.customizablecalendar.library.model;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by francescofurlan on 27/06/17.
 */

public class Calendar {
    private DateTime firstMonth;
    private DateTime firstSelectedDay;
    private DateTime lastSelectedDay;
    private DateTime currentMonth;
    private List<DateTime> months;
    private boolean multipleSelection;
    private int firstDayOfWeek;

    public Calendar(DateTime firstMonth, DateTime lastMonth) {
        this.firstMonth = firstMonth;
        this.firstDayOfWeek = java.util.Calendar.getInstance(Locale.getDefault()).getFirstDayOfWeek();

        int startMonth = firstMonth.getMonthOfYear() + 1;
        int monthsBetweenCount = Months.monthsBetween(firstMonth, lastMonth).getMonths();

        months = new ArrayList<>();

        months.add(firstMonth);
        currentMonth = firstMonth;

        DateTime monthToAdd = new DateTime(firstMonth.getYear(), startMonth, 1, 0, 0);
        for (int i = 0; i <= monthsBetweenCount; i++) {
            months.add(monthToAdd);
            monthToAdd = monthToAdd.plusMonths(1);
        }
    }

    public DateTime getFirstSelectedDay() {
        return firstSelectedDay;
    }

    public void setFirstSelectedDay(DateTime firstSelectedDay) {
        this.firstSelectedDay = firstSelectedDay;
    }

    public DateTime getLastSelectedDay() {
        return lastSelectedDay;
    }

    public void setLastSelectedDay(DateTime lastSelectedDay) {
        this.lastSelectedDay = lastSelectedDay;
    }

    public DateTime getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(DateTime currentMonth) {
        this.currentMonth = currentMonth;
    }

    public List<DateTime> getMonths() {
        return months;
    }

    public void setMonths(List<DateTime> months) {
        this.months = months;
    }

    public boolean isMultipleSelectionEnabled() {
        return multipleSelection;
    }

    public void setMultipleSelection(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public DateTime getFirstMonth() {
        return firstMonth;
    }
}
