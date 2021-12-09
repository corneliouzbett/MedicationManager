package com.corneliouzbett.medmanager.utils.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

/**
 * @author corneliouzbett  on 16/04/2018.
 */

public class medManagerJobService extends JobService {

    private MedicationJobExecutor medicationJobExecutor;

    @Override
    public boolean onStartJob(JobParameters params) {
        medicationJobExecutor = new MedicationJobExecutor() {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "fdfdsdsds" + s, Toast.LENGTH_LONG).show();
                jobFinished(params, false);
            }
        };
        medicationJobExecutor.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        medicationJobExecutor.cancel(true);
        return false;
    }
}
