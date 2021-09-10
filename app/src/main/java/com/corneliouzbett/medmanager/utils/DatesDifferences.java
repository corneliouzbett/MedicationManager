package com.corneliouzbett.medmanager.utils;

import java.util.Date;

/**
 * Created by CorneliouzBett on 07/04/2018.
 */

public class DatesDifferences {

    public static long getNumberOfDays(Date date1, Date date2){

        long diff = Math.abs(date1.getTime() - date2.getTime());
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }
}
