package com.molo17.customizablecalendar.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.molo17.customizablecalendar.library.components.CustomizableCalendar;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.model.Calendar;

import org.joda.time.DateTime;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateData();
    }

    private void updateData() {
        // setting up first and last month that must be showed in the calendar
        DateTime firstMonth = new DateTime().withMonthOfYear(1);
        DateTime lastMonth = new DateTime();

        // create the Calendar obj and setting it up with some configs like:
        // - first selected day
        // - last selected day
        // - multiple selection
        final Calendar calendar = new Calendar(firstMonth, lastMonth);
        calendar.setFirstSelectedDay(new DateTime());
        calendar.setLastSelectedDay(new DateTime());
        calendar.setMultipleSelection(true);

        CustomizableCalendar customizableCalendar = findViewById(R.id.view_month);

        // create a CalendarViewInteractor obj needed to interact with the CustomizableCalendar
        final CalendarViewInteractor calendarViewInteractor = new CalendarViewInteractor(getBaseContext());

        // create the AUCalendar obj and observes changes on it
        AUCalendar auCalendar = AUCalendar.getInstance(calendar);
        calendarViewInteractor.updateCalendar(calendar);
        auCalendar.addChangeListener(new AUCalendar.CalendarObjectChangeListener() {
            @Override
            public void onChange(AUCalendar.ChangeSet changeSet) {
                calendarViewInteractor.updateCalendar(calendar);
            }
        });
        // inject (set) the calendarViewInteractor to the CustomizableCalendar
        customizableCalendar.injectViewInteractor(calendarViewInteractor);
    }
}