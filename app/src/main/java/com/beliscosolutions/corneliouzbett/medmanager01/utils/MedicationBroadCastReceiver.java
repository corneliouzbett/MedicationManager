package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by CorneliouzBett on 05/04/2018.
 */

public class MedicationBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmReminder.setTheAlarm(context);
    }
}
