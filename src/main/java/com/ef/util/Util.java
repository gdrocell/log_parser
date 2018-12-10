package com.ef.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    
    /**
     * Reformats a date 
     * @param from - current format of date string
     * @param to - new format of date string
     * @param date - the original date string in the from format
     * @return the reformatted date.
     */
    public static String reformatDate(SimpleDateFormat from, SimpleDateFormat to, String date) {
        Date d = null;
        
        if(from == null || to == null || date == null) {
            throw new IllegalArgumentException("Can't invoke reformatDate with a null parameter.");
        }
        
        try {
            d = from.parse(date);
        }
        catch(ParseException e) {
            
        }
        
        return to.format(d);
    }
    
    /**
     * Reformats a date 
     * @param to - new format of date string
     * @param d - the date object to reformat
     * @return the reformatted date.
     */
    public static String reformatDate(SimpleDateFormat to, Date d) {

        if(to == null || d == null) {
            throw new IllegalArgumentException("Can't invoke reformatDate with a null parameter.");
        }

        
        return to.format(d);
    }
    
    /**
     * 
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date toDate(String format, String date) throws ParseException {
        if(format == null || date == null) {
            throw new IllegalArgumentException("Can't invoke toDate with a null parameter");
        }
        
        return new SimpleDateFormat(format).parse(date);  
    }
    
    /**
     * 
     * @param date
     * @return
     */
    public static Date addOneHour(Date date) {
        if(date == null) {
            throw new IllegalArgumentException("can't invoke addOneHour with a null parameter.");
        }
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1); 
        return cal.getTime();
    }
    
    public static Date addOneDay(Date date) {
        if(date == null) {
            throw new IllegalArgumentException("can't invoke addOneHour with a null parameter.");
        }
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date);
        cal.add(Calendar.DATE, 1); 
        return cal.getTime();
    }
}
