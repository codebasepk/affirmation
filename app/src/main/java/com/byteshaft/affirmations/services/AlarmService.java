package com.byteshaft.affirmations.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
    AlarmReceiver alarm = new AlarmReceiver();

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.start();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alarm.cancel();
        System.out.println("started ....");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
