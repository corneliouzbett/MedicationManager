package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;

/**
 * Created by CorneliouzBett on 05/04/2018.
 */

public class MedicationBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Reminder received!", Toast.LENGTH_LONG).show();
        Bundle  bundle = intent.getExtras();
        Notifications.createNotification(context,
                "MED-MANAGER",
                bundle.getString("body"),
                "testing my medication manager",
                R.mipmap.ic_launcher);

    }
}
