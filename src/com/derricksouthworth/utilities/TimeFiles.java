package com.derricksouthworth.utilities;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author derrick.southworth
 */

public class TimeFiles {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
//    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

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
     * convert LocalDateTime from POJO to UTC Timestamp to store in database
     * @param ldt
     * @return
     */
    public static Timestamp timeToUTC(LocalDateTime ldt) {
        ZonedDateTime utcTime = ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        return Timestamp.valueOf(String.valueOf(utcTime));
    }

    /**
     * convert UTC Timestamp from database to LocalDateTime for timezone change
     * and POJO creation
     * @param ts
     * @return
     */
    public static LocalDateTime timeToLocal(Timestamp ts) {
        LocalDateTime ldt = ts.toLocalDateTime();
        TimeZone localTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ZonedDateTime utczdt = ldt.atZone(ZoneId.systemDefault());
        TimeZone.setDefault(localTimeZone);
        ZonedDateTime localzdt = utczdt.withZoneSameInstant(ZoneId.systemDefault());
        return localzdt.toLocalDateTime();
    }
}
