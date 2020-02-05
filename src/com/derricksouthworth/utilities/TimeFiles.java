package com.derricksouthworth.utilities;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author derrick.southworth
 */

public class TimeFiles {
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

    public static Calendar timeStampToCalendar(Timestamp time) {
        if (time == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        return cal;
    }

    // TODO Convert time to UTC

    // TODO Convert time to Local Time
}
