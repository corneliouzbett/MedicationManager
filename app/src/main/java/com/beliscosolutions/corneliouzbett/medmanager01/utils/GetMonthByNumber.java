package com.beliscosolutions.corneliouzbett.medmanager01.utils;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class GetMonthByNumber {

    public static String get(int month){
        String month_value = "";
        switch (month){
            case 1:
               month_value = "JANUARY";
               break;
            case 2:
                month_value = "FEBRUARY";
                break;
            case 3:
               month_value = "MARCH";
               break;
            case 4:
                month_value = "APRIL";
                break;
            case 5:
               month_value = "MAY";
               break;
            case 6:
                month_value = "JUNE";
                break;
            case 7:
               month_value = "JULY";
               break;
            case 8:
                month_value = "AUGUST";
                break;
            case 9:
               month_value = "SEPTEMBER";
               break;
            case 10:
                month_value = "OCTOBER";
                break;
            case 11:
               month_value = "NOVEMBER";
               break;
            case 12:
                month_value = "DECEMBER";
                break;
        }

        return month_value;
    }
}
