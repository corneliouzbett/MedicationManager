package com.corneliouzbett.medmanager.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.corneliouzbett.medmanager.R;

/**
 * Created by CorneliouzBett on 05/04/2018.
 */

public class MedicationBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Util.scheduleJob(context);
        Notifications.createNotification(context,
                "MED-MANAGER",
                "It's Time !!! (::)<(:)> take ",
                "Quick recovery from med-manager team",
                R.mipmap.ic_launcher);


    }
}
