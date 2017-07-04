package com.francesco.furlan.customizablecalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.francesco.furlan.customizablecalendar.library.components.CustomizableCalendar;
import com.francesco.furlan.customizablecalendar.library.interactors.AUCalendar;
import com.francesco.furlan.customizablecalendar.library.model.Calendar;

import org.joda.time.DateTime;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.view_month)
    CustomizableCalendar customizableCalendar;

    private CompositeDisposable subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        subscriptions = new CompositeDisposable();
        updateData();
    }

    private void updateData() {
        // setting up first and last month that must be showed in the calendar
        DateTime firstMonth = new DateTime().withDayOfMonth(1);
        DateTime lastMonth = new DateTime().plusMonths(3).withDayOfMonth(1);

        // create the Calendar obj and setting it up with some configs like:
        // - first selected day
        // - last selected day
        // - multiple selection
        final Calendar calendar = new Calendar(firstMonth, lastMonth);
        calendar.setFirstSelectedDay(new DateTime().plusDays(4));
        calendar.setLastSelectedDay(new DateTime().plusDays(6));
        calendar.setMultipleSelection(true);

        // create a CalendarViewInteractor obj needed to interact with the CustomizableCalendar
        final CalendarViewInteractor calendarViewInteractor = new CalendarViewInteractor(getBaseContext());

        // create the AUCalendar obj and observes changes on it
        AUCalendar auCalendar = AUCalendar.getInstance(calendar);
        calendarViewInteractor.updateCalendar(calendar);
        subscriptions.add(
                auCalendar.observeChangesOnCalendar().subscribe(new Consumer<AUCalendar.ChangeSet>() {
                    @Override
                    public void accept(AUCalendar.ChangeSet changeSet) throws Exception {
                        calendarViewInteractor.updateCalendar(calendar);
                    }
                })
        );

        // inject (set) the calendarViewInteractor to the CustomizableCalendar
        customizableCalendar.injectViewInteractor(calendarViewInteractor);
    }
}