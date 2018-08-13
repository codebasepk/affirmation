package com.byteshaft.affirmations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCreateButton;
    private Button mListButton;
    private Button mDailyButton;
    private Button mOptionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
