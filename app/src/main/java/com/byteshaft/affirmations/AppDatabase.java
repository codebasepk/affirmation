package com.byteshaft.affirmations;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AffirmationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AffirmationDao affirmationDao();
}
