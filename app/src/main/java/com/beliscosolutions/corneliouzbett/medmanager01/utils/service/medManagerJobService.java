package com.beliscosolutions.corneliouzbett.medmanager01.utils.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

import com.beliscosolutions.corneliouzbett.medmanager01.utils.MedicationBroadCastReceiver;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.Util;

/**
 * Created by CorneliouzBett on 16/04/2018.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class medManagerJobService extends JobService {

    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), MedicationBroadCastReceiver.class);
        getApplicationContext().startService(service);
        Util.scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
