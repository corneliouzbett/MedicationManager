package com.corneliouzbett.medmanager.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.corneliouzbett.medmanager.R;


/**
 * Created by CorneliouzBett on 05/04/2018.
 *
 */

public class NotifyService extends Service {

    @Override
    public void onCreate() {

        Notifications.createNotification(
                this,
                "Med-manager",
                "coming soon",
                "medication",
                R.drawable.ic_checked);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
