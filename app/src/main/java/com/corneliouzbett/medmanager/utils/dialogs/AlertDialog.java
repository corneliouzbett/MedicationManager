package com.corneliouzbett.medmanager.utils.dialogs;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by CorneliouzBett on 07/04/2018.
 */

public class AlertDialog {

    public static void create(String message, String title, Context context){
        android.app.AlertDialog.Builder alertDialogbuilder = new android.app.AlertDialog.Builder(context);

        alertDialogbuilder.setTitle(title);
        alertDialogbuilder
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.app.AlertDialog alertDialog = alertDialogbuilder.create();
        alertDialog.show();
    }
}
