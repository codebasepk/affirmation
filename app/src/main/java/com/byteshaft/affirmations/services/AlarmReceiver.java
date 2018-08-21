package com.byteshaft.affirmations.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.byteshaft.affirmations.MainActivity;
import com.byteshaft.affirmations.R;
import com.byteshaft.affirmations.activities.DailyActivity;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;
import com.byteshaft.affirmations.utils.AppGlobals;
import com.byteshaft.affirmations.utils.Helpers;

import java.util.List;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    private AppDatabase db;
    private int myInt =-1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Helpers.cancel(context);
        db = Room.databaseBuilder(AppGlobals.getContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
        final List<Affirmation> affirmationList = db.affirmationDao().getAllAffirmations();
        Random random = new Random();
        if (!affirmationList.isEmpty()) {
            myInt = random.nextInt(affirmationList.size());
        }
        if (myInt >= 0) {
            if (AppGlobals.isEnabled()) {
                showNotification(context, affirmationList.get(myInt).getAffirmation());
            }
        }
        Helpers.start(context);
        System.out.println("ok again");
        AppGlobals.saveDataToSharedPreferences(AppGlobals.TODAYS_NUMBER, myInt);
    }

    private void showNotification(Context ctx ,String message) {
        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String CHANNEL_ID = "affirmation_channel";
            CharSequence name = "Affirmation";
            String Description = "The Affirmation channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, "affirmation_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Today's Affirmation")
                .setContentText(message);

        Intent resultIntent = new Intent(ctx, DailyActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(12, builder.build());
    }
}