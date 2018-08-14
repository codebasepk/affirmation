package com.byteshaft.affirmations;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.byteshaft.affirmations.activities.AffirmationsList;
import com.byteshaft.affirmations.activities.CreateAffirmation;
import com.byteshaft.affirmations.activities.DailyActivity;
import com.byteshaft.affirmations.activities.Options;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCreateButton;
    private Button mListButton;
    private Button mDailyButton;
    private Button mOptionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setTitle("Home");
        mCreateButton = findViewById(R.id.button_create);
        mListButton = findViewById(R.id.button_list);
        mDailyButton = findViewById(R.id.button_daily);
        mOptionsButton = findViewById(R.id.button_options);
        mCreateButton.setOnClickListener(this);
        mListButton.setOnClickListener(this);
        mDailyButton.setOnClickListener(this);
        mOptionsButton.setOnClickListener(this);
    }

    public void SetAlarm()
    {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent _ )
            {
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( this, 0, new Intent("com.blah.blah.somemessage"), 0 );
        AlarmManager manager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000*5, pintent );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create:
                startActivity(new Intent(MainActivity.this, CreateAffirmation.class));
                break;
            case R.id.button_list:
                startActivity(new Intent(MainActivity.this, AffirmationsList.class));
                break;
            case R.id.button_daily:
                startActivity(new Intent(MainActivity.this, DailyActivity.class));
                break;
            case R.id.button_options:
                startActivity(new Intent(MainActivity.this, Options.class));
                break;
        }
    }
}
