package com.corneliouzbett.medmanager.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.corneliouzbett.medmanager.views.MainActivity;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class Notifications {

    public static void createNotification(Context context, String title, String body, String subject, int drawable){

        Intent startMedsActivity = new Intent(context,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                startMedsActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setContentTitle(subject)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(drawable)
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

    }
}
