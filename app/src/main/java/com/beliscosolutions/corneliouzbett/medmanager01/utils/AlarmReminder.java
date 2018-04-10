package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class AlarmReminder extends BroadcastReceiver{

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Toast.makeText(arg0, "Reminder received!", Toast.LENGTH_LONG).show();

    }
}
