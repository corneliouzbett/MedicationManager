package com.corneliouzbett.medmanager.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class AlarmReminder extends BroadcastReceiver{

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Toast.makeText(arg0, "Reminder received!", Toast.LENGTH_LONG).show();

    }
}
