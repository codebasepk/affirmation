package com.byteshaft.affirmations;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.byteshaft.affirmations.activities.AffirmationsList;
import com.byteshaft.affirmations.activities.CreateAffirmation;
import com.byteshaft.affirmations.activities.DailyActivity;
import com.byteshaft.affirmations.activities.Options;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;
import com.byteshaft.affirmations.utils.AppGlobals;
import com.byteshaft.affirmations.utils.Helpers;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCreateButton;
    private Button mListButton;
    private Button mDailyButton;
    private Button mOptionsButton;
    private AppDatabase db;
    private SharedPreferences saved_values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setTitle("Home");
        db = Room.databaseBuilder(AppGlobals.getContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
        final List<Affirmation> affirmationList = db.affirmationDao().getAllAffirmations();
        Log.i("TAG", "alarm " + Helpers.isAlarmSet(getApplicationContext()));
        if (!Helpers.isAlarmSet(getApplicationContext()) && affirmationList.size() > 0) {
            Helpers.start(getApplicationContext());
        } else {
            Helpers.cancel(getApplicationContext());
            Helpers.start(getApplicationContext());
        }
        saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
            if (!saved_values.getBoolean("protected", false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Huawei Mobile").setMessage("Your phone need special permission to run " +
                        "Alarms for daily random affirmations. please select Allow to use the app. \nTurn off switch for " + getString(R.string.app_name))
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.huawei.systemmanager",
                                        "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"));
                                startActivityForResult(intent, 1001);
                            }
                        }).create().show();
            }
        }
        Log.i("TAG", "alarm " + Helpers.isAlarmSet(getApplicationContext()));
        mCreateButton = findViewById(R.id.button_create);
        mListButton = findViewById(R.id.button_list);
        mDailyButton = findViewById(R.id.button_daily);
        mOptionsButton = findViewById(R.id.button_options);
        mCreateButton.setOnClickListener(this);
        mListButton.setOnClickListener(this);
        mDailyButton.setOnClickListener(this);
        mOptionsButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", " code " + requestCode);
        Log.i("TAG", " result code " + resultCode);
        if (requestCode == 1001) {
            saved_values.edit().putBoolean("protected", true).apply();
        }
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
