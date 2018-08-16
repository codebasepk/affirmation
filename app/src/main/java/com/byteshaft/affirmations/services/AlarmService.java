package com.byteshaft.affirmations.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(getApplicationContext(), "Service started ", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
