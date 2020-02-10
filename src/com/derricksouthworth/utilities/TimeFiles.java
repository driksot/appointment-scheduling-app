package com.derricksouthworth.utilities;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author derrick.southworth
 */

public class TimeFiles {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

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

    // TODO Convert time to UTC
    public static String timeToUTC(String time) throws ParseException {
        Calendar calendar = stringToCalendar(time);
        ZonedDateTime toUTC = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.of("UTC"));
        return toUTC.format(DATE_TIME_FORMATTER);
    }

    // TODO Convert time to Local Time
    public static String timeToLocal(String time) throws ParseException {
        Calendar calendar = stringToCalendar(time);
        ZonedDateTime toLocal = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return toLocal.format(DATE_TIME_FORMATTER);
    }

//    // TODO Convert time to UTC
//    public static String timeToUTC(Timestamp ts) {
//        LocalDateTime ldt = ts.toLocalDateTime();
//        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
//        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
//        return utczdt.format(df);
//    }
//
//    // TODO Convert time to Local Time
//    public static String timeToLocal(Timestamp ts) {
//        LocalDateTime ldt = ts.toLocalDateTime();
//        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
//        return zdt.format(df);
//    }
}
