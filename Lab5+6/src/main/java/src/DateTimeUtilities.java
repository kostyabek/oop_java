package src;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeUtilities {

    public static Calendar getCurrentDateAndTime() {
        Calendar calendar = new GregorianCalendar();
        return calendar;
    }

    public static String getDateAndTimeString(Calendar calendar) {
        String dateAndTime = String.format("%s.%s.%s %s:%s",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        return dateAndTime;
    }
    
    public static String getDateString(Calendar calendar) {
        String dateAndTime = String.format("%s.%s.%s",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
        return dateAndTime;
    }
    
    public static Calendar convertStringDateToCalendar(String dateTimeString) {
        String[] dateTimeParts = dateTimeString.split(" ");

        String date = dateTimeParts[0];
        int day = Integer.valueOf(date.substring(0, date.indexOf('.')));
        int month = Integer.valueOf(date.substring(date.indexOf('.') + 1, date.lastIndexOf('.'))) - 1;
        int year = Integer.valueOf(date.substring(date.lastIndexOf('.') + 1));

        String time = "";
        int hours = 0;
        int minutes = 0;
        if (dateTimeParts.length == 2) {
            time = dateTimeParts[1];
            hours = Integer.valueOf(time.substring(0, time.indexOf(':')));
            minutes = Integer.valueOf(time.substring(time.indexOf(':') + 1));
        }

        Calendar calendar = new GregorianCalendar(year, month, day, hours, minutes);
        return calendar;
    }
}
