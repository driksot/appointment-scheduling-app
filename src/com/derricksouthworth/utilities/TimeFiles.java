package com.derricksouthworth.utilities;

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

    // TODO Convert time to UTC

    // TODO Convert time to Local Time
}
