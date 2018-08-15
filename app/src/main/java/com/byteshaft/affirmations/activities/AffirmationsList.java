package com.byteshaft.affirmations.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.affirmations.R;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;
import com.byteshaft.affirmations.utils.AppGlobals;

import java.util.List;

public class AffirmationsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AffirmationAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations_list);
        setTitle("List");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AffirmationsList.this, CreateAffirmation.class));
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = Room.databaseBuilder(AppGlobals.getContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<Affirmation> affirmationList = db.affirmationDao().getAllAffirmations();
        adapter = new AffirmationAdapter(affirmationList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public class AffirmationAdapter extends RecyclerView.Adapter<AffirmationAdapter.ViewHolder> {

        private List<Affirmation> arrayList;


        private AffirmationAdapter(List<Affirmation> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public AffirmationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delegate_affirmation, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AffirmationAdapter.ViewHolder holder, final int position) {
            holder.affirmationText.setText(arrayList.get(position).getAffirmation());
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Affirmation affirmation = arrayList.get(position);
                    db.affirmationDao().delete(affirmation);
                    System.out.println("Deleted");
                    arrayList.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AffirmationsList.this, "Item Removed", Toast.LENGTH_SHORT).show();
                }
            });
            holder.affirmationText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView affirmationText;
            ImageButton deleteButton;

            ViewHolder(View itemView) {
                super(itemView);
                affirmationText = itemView.findViewById(R.id.tv_affirmation);
                deleteButton = itemView.findViewById(R.id.button_delete_affirmation);
            }
        }
    }
}
