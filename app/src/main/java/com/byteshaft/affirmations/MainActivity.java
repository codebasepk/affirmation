package com.byteshaft.affirmations;

import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.byteshaft.affirmations.activities.AffirmationsList;
import com.byteshaft.affirmations.activities.CreateAffirmation;
import com.byteshaft.affirmations.activities.DailyActivity;
import com.byteshaft.affirmations.activities.Options;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;
import com.byteshaft.affirmations.utils.AppGlobals;
import com.byteshaft.affirmations.utils.Helpers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
            } else {
                ifHuaweiAlert();
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


    private boolean isCallable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void huaweiProtectedApps() {
        try {
            String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial();
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ignored) {
        }
    }

    private String getUserSerial() {
        //noinspection ResourceType
        Object userManager = getSystemService("user");
        if (null == userManager) return "";

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return "";
    }
    private void ifHuaweiAlert() {
        final SharedPreferences settings = getSharedPreferences("ProtectedApps", MODE_PRIVATE);
        final String saveIfSkip = "skipProtectedAppsMessage";
        boolean skipMessage = settings.getBoolean(saveIfSkip, false);
        if (!skipMessage) {
            final SharedPreferences.Editor editor = settings.edit();
            Intent intent = new Intent();
            intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            if (isCallable(intent)) {
                final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(this);
                dontShowAgain.setText("Do not show again");
                dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean(saveIfSkip, isChecked);
                        editor.apply();
                    }
                });

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Huawei Protected Apps")
                        .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", getString(R.string.app_name)))
                        .setView(dontShowAgain)
                        .setPositiveButton("Protected Apps", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                huaweiProtectedApps();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            } else {
                editor.putBoolean(saveIfSkip, true);
                editor.apply();
            }
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
