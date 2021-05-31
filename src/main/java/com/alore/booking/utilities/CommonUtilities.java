package com.alore.booking.utilities;

import java.text.ParseException;
import java.util.Date;

public class CommonUtilities {
    public static Date convertStringToDate(String dateStr) {
        Date date = null;
        try {
            date = CommonConstants.DATE_FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertStringToSqlDate(String dateStr) {
        String date = null;
        try {
            Date tempDate = CommonConstants.DATE_FORMATTER.parse(dateStr);
            date = CommonConstants.SQL_DATE_FORMATTER.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
