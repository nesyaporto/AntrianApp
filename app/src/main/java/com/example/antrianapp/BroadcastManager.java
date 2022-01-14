package com.example.antrianapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BroadcastManager extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME = "Notifikasi Bimbingan";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private static final int ID_ONETIME = 100;
    private static final String CHANNEL_ID = "channel_notif_alarm";
    private static final CharSequence CHANNEL_NAME = "Alarm Channel";


    public BroadcastManager() {


    }

    @Override
    public void onReceive(Context context, Intent intent) {


        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);


        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_ONE_TIME;
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_ONETIME;


        showAlarmNotification(context, title, message, notifId);

    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_time)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            mBuilder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = mBuilder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }

    }

    public void setOneTimeAlarm(Context context, String type, String date, String time, String message) {
        if (isDateInvalid(date, "dd/MM/yyyy") || isDateInvalid(time, "HH:mm")) return;
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent in2 = new Intent(context, BroadcastManager.class);
        in2.putExtra(EXTRA_MESSAGE, message);
        in2.putExtra(EXTRA_TYPE, type);

        String dateArray[] = date.split("/");
        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));


        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pi2 = PendingIntent.getBroadcast(context, ID_ONETIME, in2, PendingIntent.FLAG_UPDATE_CURRENT);
        if (am != null) {
            //ini yang one time
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi2);

        }
        Toast.makeText(context, "Mengatur Notifikasi Bimbingan", Toast.LENGTH_SHORT).show();
    }


    public boolean isDateInvalid(String date, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        df.setLenient(false);
        try {
            df.parse(date);
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

}