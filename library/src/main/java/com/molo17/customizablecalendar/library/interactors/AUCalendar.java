package com.molo17.customizablecalendar.library.interactors;

import com.molo17.customizablecalendar.library.model.Calendar;
import com.molo17.customizablecalendar.library.model.CalendarFields;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by francescofurlan on 30/06/17.
 */
public class AUCalendar {
    private static AUCalendar AUCalendarInstance;
    private Calendar calendar;
    private List<CalendarObjectChangeListener> onChangeListenerList = new ArrayList<>();
    private boolean selectStarted;

    public static AUCalendar getInstance() {
        if (AUCalendarInstance == null) {
            AUCalendarInstance = new AUCalendar();
        }
        return AUCalendarInstance;
    }

    public static AUCalendar getInstance(Calendar calendar) {
        getInstance();
        if (calendar != null) {
            AUCalendarInstance.setCalendar(calendar);
        }
        return AUCalendarInstance;
    }

    public LocalDate getFirstMonth() {
        return calendar.getFirstMonth();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void addChangeListener(CalendarObjectChangeListener objectChangeListener) {
        onChangeListenerList.add(objectChangeListener);
    }

    public void removeChangeListener(CalendarObjectChangeListener objectChangeListener) {
        onChangeListenerList.remove(objectChangeListener);
    }

    public void clearAllChangeListener() {
        onChangeListenerList.clear();
    }

    private void emitOnChange(ChangeSet changeSet) {
        for (CalendarObjectChangeListener onChangeListener : onChangeListenerList) {
            onChangeListener.onChange(changeSet);
        }
    }

    private void emitOnChange(String changedField) {
        CalendarChangeSet changeSet = new CalendarChangeSet();
        changeSet.addChangedField(changedField);
        emitOnChange(changeSet);
    }

    public LocalDate getFirstSelectedDay() {
        return calendar.getFirstSelectedDay();
    }

    public void setFirstSelectedDay(LocalDate firstSelectedDay) {
        if (calendar.getFirstSelectedDay() != firstSelectedDay) {
            calendar.setFirstSelectedDay(firstSelectedDay);
            emitOnChange(CalendarFields.FIRST_SELECTED_DAY);
        }
    }

    public LocalDate getLastSelectedDay() {
        return calendar.getLastSelectedDay();
    }

    public void setLastSelectedDay(LocalDate lastSelectedDay) {
        if (calendar.getLastSelectedDay() != lastSelectedDay) {
            calendar.setLastSelectedDay(lastSelectedDay);
            emitOnChange(CalendarFields.LAST_SELECTED_DAY);
        }
    }

    public LocalDate getCurrentMonth() {
        return calendar.getCurrentMonth();
    }

    public void setCurrentMonth(LocalDate currentMonth) {
        if (calendar.getCurrentMonth() != currentMonth) {
            calendar.setCurrentMonth(currentMonth);
            emitOnChange(CalendarFields.CURRENT_MONTH);
        }
    }

    public List<LocalDate> getMonths() {
        return calendar.getMonths();
    }

    public void setMonths(List<LocalDate> months) {
        if (calendar.getMonths() != months) {
            calendar.setMonths(months);
            emitOnChange(CalendarFields.MONTHS);
        }
    }

    public boolean isMultipleSelectionEnabled() {
        return calendar.isMultipleSelectionEnabled();
    }

    public boolean isAllowSelectionFeatureDays() {
        return calendar.isAllowSelectionFeatureDays();
    }

    public void setMultipleSelection(boolean multipleSelection) {
        if (calendar.isMultipleSelectionEnabled() != multipleSelection) {
            calendar.setMultipleSelection(multipleSelection);
            emitOnChange(CalendarFields.MULTIPLE_SELECTION);
        }
    }

    public void setAllowSelectionFeatureDays(boolean allowSelectionFeatureDays) {
        if (calendar.isAllowSelectionFeatureDays() != allowSelectionFeatureDays) {
            calendar.setAllowSelectionFeatureDays(allowSelectionFeatureDays);
            emitOnChange(CalendarFields.ALLOW_SELECTION_FEATURE_DAYS);
        }
    }

    public int getFirstDayOfWeek() {
        return calendar.getFirstDayOfWeek();
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        if (calendar.getFirstDayOfWeek() != firstDayOfWeek) {
            calendar.setFirstDayOfWeek(firstDayOfWeek);
            emitOnChange(CalendarFields.FIRST_DAY_OF_WEEK);
        }
    }

    public boolean isSelectStarted() {
        return selectStarted;
    }

    public void setSelectStarted(boolean selectStarted) {
        this.selectStarted = selectStarted;
    }

    public interface ChangeSet {
        List<String> getChangedFields();

        boolean isFieldChanged(String fieldName);

        String toString();
    }

    public interface CalendarObjectChangeListener {
        void onChange(ChangeSet changeSet);
    }

    private class CalendarChangeSet implements ChangeSet {
        private List<String> changedFields = new ArrayList<>();

        public void setChangedFiels(List<String> changedFields) {
            this.changedFields = changedFields;
        }

        public void addChangedField(String changedField) {
            this.changedFields.add(changedField);
        }

        @Override
        public List<String> getChangedFields() {
            return changedFields;
        }

        @Override
        public boolean isFieldChanged(String fieldName) {
            return changedFields.contains(fieldName);
        }

        @Override
        public String toString() {
            StringBuilder fieldChanges = new StringBuilder();
            for (String fieldChanged : changedFields) {
                fieldChanges.append(" ").append(fieldChanged);
            }
            return fieldChanges.toString();
        }
    }
}