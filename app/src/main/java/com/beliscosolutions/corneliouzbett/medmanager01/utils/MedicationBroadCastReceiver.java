package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.beliscosolutions.corneliouzbett.medmanager01.R;

/**
 * Created by CorneliouzBett on 05/04/2018.
 */

public class MedicationBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Notifications.createNotification(context,
                "MED-MANAGER",
                "It's Time !!! (::)<(:)> take " + bundle.getString("body"),
                "Quick recovery from med-manager team",
                R.mipmap.ic_launcher);


    }
}
