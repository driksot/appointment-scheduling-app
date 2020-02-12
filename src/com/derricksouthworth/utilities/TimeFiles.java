package com.derricksouthworth.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author derrick.southworth
 */

public class TimeFiles {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("kk:mm:ss");

    /**
     * Convert given String to Calendar
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Calendar stringToCalendar (String strDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.setTime(sdf.parse(strDate));
        return cal;
    }

    /**
     * Convert string time from local to UTC time
     * @param time
     * @return
     * @throws ParseException
     */
    public static String timeToUTC(String time) throws ParseException {
        Calendar calendar = stringToCalendar(time);
        ZonedDateTime toUTC = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.of("UTC"));
        return toUTC.format(DATE_TIME_FORMATTER);
    }

    /**
     * Convert string time from UTC to local time
     * @param time
     * @return
     * @throws ParseException
     */
    public static String timeToLocal(String time) throws ParseException {
        // Save system timezone to set default back to correct value later
        TimeZone localTimeZone = TimeZone.getDefault();

        // Set default timezone to UTC so that Calendar object will be instantiated in UTC timezone to match
        // string time timezone from database
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = stringToCalendar(time);

        // Reset default timezone to correct local timezone
        TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone.getID()));
        ZonedDateTime toLocal = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return toLocal.format(DATE_TIME_FORMATTER);
    }
}
