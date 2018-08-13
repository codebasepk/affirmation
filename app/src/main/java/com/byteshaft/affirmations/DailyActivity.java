package com.byteshaft.affirmations;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;

import java.util.List;

public class DailyActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView mDaily_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        setTitle("Daily");
        mDaily_tv = findViewById(R.id.tv_daily);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = Room.databaseBuilder(AppGlobals.getContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
        final List<Affirmation> affirmationList = db.affirmationDao().getAllAffirmations();
        int number = affirmationList.size();

        if (affirmationList.size() > 0) {
            mDaily_tv.setText(affirmationList.get(0).getAffirmation());
        } else {
            mDaily_tv.setText("No Affirmation Added");
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
