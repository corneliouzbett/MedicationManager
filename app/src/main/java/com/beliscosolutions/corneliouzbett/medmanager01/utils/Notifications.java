package com.beliscosolutions.corneliouzbett.medmanager01.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class Notifications {

    public static void createNotification(Context context, String title, String body, String subject, int drawable){

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setContentTitle(subject)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(drawable)
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

    }
}
