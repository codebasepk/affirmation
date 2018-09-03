package com.byteshaft.affirmations.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.byteshaft.affirmations.services.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

public class Helpers {

    public static void start(Context context) {
        PendingIntent pendingIntent;
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar time = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        Date date = new Date();
        time.setTime(date);
        cal_now.setTime(date);
        Log.i("TAG", "hours " + date.getHours());
        time.set(Calendar.HOUR_OF_DAY, 6);
        time.set(Calendar.MINUTE, 10);
        time.set(Calendar.SECOND, 10);
        if(time.before(cal_now)) {//if its in the past increment
            time.add(Calendar.DATE, 1);
//            time.set(Calendar.HOUR_OF_DAY, date.getHours());
            Log.i("TAG", " setting after alarm  " + time.getTimeInMillis());
        }
        Log.i("TAG", " time in milli seconds  " + (time.getTimeInMillis()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        } else {
            manager.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
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
