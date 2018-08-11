package com.byteshaft.affirmations;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.byteshaft.affirmations.adapter.AffirmationAdapter;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;

import java.util.List;

public class AffirmationsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AffirmationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations_list);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
        List<AffirmationEntity> users = database.affirmationDao().getAllUsers();
        adapter = new AffirmationAdapter(users);
        recyclerView.setAdapter(adapter);
    }
}
