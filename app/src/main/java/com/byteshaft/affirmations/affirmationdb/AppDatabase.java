package com.byteshaft.affirmations.affirmationdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.byteshaft.affirmations.AffirmationDao;
import com.byteshaft.affirmations.AffirmationEntity;

@Database(entities = {AffirmationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AffirmationDao affirmationDao();
}
