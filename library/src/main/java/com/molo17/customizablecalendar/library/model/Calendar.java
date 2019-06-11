package com.molo17.customizablecalendar.library.model;


import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by francescofurlan on 27/06/17.
 */

public class Calendar {
    private LocalDate firstMonth;
    private LocalDate firstSelectedDay;
    private LocalDate lastSelectedDay;
    private LocalDate currentMonth;
    private List<LocalDate> months;
    private boolean multipleSelection;
    private boolean allowSelectionFeatureDays;
    private int firstDayOfWeek;

    public Calendar(LocalDate firstMonth, LocalDate lastMonth) {
        this.firstMonth = firstMonth;
        this.firstDayOfWeek = java.util.Calendar.getInstance(Locale.getDefault()).getFirstDayOfWeek();

        long monthsBetweenCount = Period.between(firstMonth, lastMonth).toTotalMonths();

        months = new ArrayList<>();

        currentMonth = firstMonth;

        LocalDate monthToAdd = LocalDate.of(firstMonth.getYear(), firstMonth.getMonth(), 1);
        for (int i = 0; i <= monthsBetweenCount; i++) {
            months.add(monthToAdd);
            monthToAdd = monthToAdd.plusMonths(1);
        }
    }

    public LocalDate getFirstSelectedDay() {
        return firstSelectedDay;
    }

    public void setFirstSelectedDay(LocalDate firstSelectedDay) {
        this.firstSelectedDay = firstSelectedDay;
    }

    public LocalDate getLastSelectedDay() {
        return lastSelectedDay;
    }

    public void setLastSelectedDay(LocalDate lastSelectedDay) {
        this.lastSelectedDay = lastSelectedDay;
    }

    public LocalDate getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(LocalDate currentMonth) {
        this.currentMonth = currentMonth;
    }

    public List<LocalDate> getMonths() {
        return months;
    }

    public void setMonths(List<LocalDate> months) {
        this.months = months;
    }

    public boolean isMultipleSelectionEnabled() {
        return multipleSelection;
    }

    public boolean isAllowSelectionFeatureDays() {
        return allowSelectionFeatureDays;
    }

    public void setMultipleSelection(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    public void setAllowSelectionFeatureDays(boolean allowSelectionFeatureDays) {
        this.allowSelectionFeatureDays = allowSelectionFeatureDays;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public LocalDate getFirstMonth() {
        return firstMonth;
    }
}
