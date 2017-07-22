package com.parse.starter;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.content.Context.NOTIFICATION_SERVICE;

public class alarmReceiver extends BroadcastReceiver {


    String message = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        message =  intent.getStringExtra("reminders");
        Notification noti = null;
        noti = new Notification.Builder(context).setContentTitle(message).setContentText("Hello").setSmallIcon(R.mipmap.ic_launcher).build();
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, noti);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

    }
}

