package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class GetMonthValue {

    public static int getMonthValue(Date date){
        int month = 0 ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            month = localDate.getMonthValue();
        }
        return month;
    }
}
