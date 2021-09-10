package com.corneliouzbett.medmanager.utils;

import java.util.Calendar;

/**
 * Created by CorneliouzBett on 06/04/2018.
 */

public class Years {

    public static int getPreviousYear(){
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR,-1);

        return prevYear.get(Calendar.YEAR);
    }

    public static int getNextYear(){
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,+1);

        return nextYear.get(Calendar.YEAR);
    }
}
