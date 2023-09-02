package com.zybooks.db_with_login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//****************************************
//
// BUILD the Notification
//
//****************************************

public class AlarmBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String text = bundle.getString("event");
        String description = bundle.getString("description");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyText")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(text)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());

    }


}

