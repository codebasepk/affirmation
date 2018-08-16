package com.byteshaft.affirmations.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.byteshaft.affirmations.R;
import com.byteshaft.affirmations.services.AlarmReceiver;
import com.byteshaft.affirmations.utils.AppGlobals;

public class Options extends AppCompatActivity {

    private static Options sInstance;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sInstance = this;
        setTitle("Options");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwitch = findViewById(R.id.notification_switch);
        if (AppGlobals.isEnabled()) {
            mSwitch.setChecked(true);
        } else {
            mSwitch.setChecked(false);
        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    startService(new Intent(Options.this, AlarmService.class));
                    System.out.println("On");
                } else {

//                    stopService(new Intent(Options.this, AlarmService.class));
                    System.out.println("off");
                }

                AppGlobals.saveState(isChecked);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
