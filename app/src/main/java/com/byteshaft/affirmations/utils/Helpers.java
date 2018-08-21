package com.byteshaft.affirmations.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.byteshaft.affirmations.services.AlarmReceiver;

public class Helpers {
    public static void start(Context context) {
        PendingIntent pendingIntent;
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

//
//        Calendar time = Calendar.getInstance();
//        time.set(Calendar.HOUR_OF_DAY, 21);
//        time.set(Calendar.MINUTE, 48);
//        time.set(Calendar.SECOND, 0);

        int hour12 = 43200000;
        int hour24 = 86400000 ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + hour12, pendingIntent);
        } else {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + hour12, pendingIntent);
        }
        Log.i("TAG", " alarm is set");
    }

    public static void cancel(Context context) {
        PendingIntent pendingIntent;
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
//        Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public static boolean isAlarmSet(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);//the same as up
        boolean isWorking = (PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
//        Log.d("TAG", "alarm is " + (isWorking ? "" : "not") + " working...");
        return isWorking;
    }
}
