package com.corneliouzbett.medmanager.utils.service;

import android.os.AsyncTask;

/**
 * @author corneliouzbett  on 17/04/2018.
 */

public class MedicationJobExecutor extends AsyncTask<Void,Void,String>{

    @Override
    protected String doInBackground(Void... voids) {
        return "running in the background";
    }
}
