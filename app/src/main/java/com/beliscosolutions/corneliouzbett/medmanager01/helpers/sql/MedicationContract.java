package com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql;

import android.provider.BaseColumns;

/**
 * Created by CorneliouzBett on 03/04/2018.
 */

public class MedicationContract {

    public static final class MedicationEntry implements BaseColumns{
        public static final String TABLE_NAME = "medication";
        public static final String COLUMN_MEDICATION_NAME = "medication_name";
        public static final String COLUMN_MEDICATION_DESCRIPTION = "medication_description";
        public static final String COLUMN_MEDICATION_INTERVAL = "medication_interval";
        public static final String COLUMN_MEDICATION_STARTDATE = "medication_start_date";
        public static final String COLUMN_MEDICATION_ENDDATE = "medication_end_date";
    }
}
