package com.molo17.customizablecalendar.sample;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkDate() throws Exception {
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = LocalDate.now();

        assertEquals(localDate1.compareTo(localDate2), 0);

        DateTime dateTime1 = DateTime.now();
        DateTime dateTime2 = DateTime.now();

        assertEquals(dateTime1.withMillisOfDay(0).compareTo(dateTime2.withMillisOfDay(0)), 0);

        assertEquals(localDate1.withDayOfMonth(1).getDayOfMonth(), dateTime1.withDayOfMonth(1).withMillisOfDay(0).getDayOfMonth());

        assertEquals(localDate1.getMonthValue(), dateTime1.getMonthOfYear());

        assertEquals(localDate1.getDayOfWeek().getValue() + 1, dateTime1.getDayOfWeek() + 1);
    }

    @Test
    public void checkDate1() throws Exception {
        DateTime firstMonth = new DateTime().withMonthOfYear(1);
        DateTime lastMonth = new DateTime();

        int monthsBetweenCount = Months.monthsBetween(firstMonth, lastMonth).getMonths();

        LocalDate localDate1 = LocalDate.now().withMonth(1);
        LocalDate localDate2 = LocalDate.now();

        int m2 = Period.between(localDate1, localDate2).getMonths();

        assertEquals(monthsBetweenCount, m2);
    }
}