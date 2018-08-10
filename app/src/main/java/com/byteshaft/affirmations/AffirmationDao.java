package com.byteshaft.affirmations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

// user Data Access Object
@Dao
public interface AffirmationDao {

    @Query("SELECT * FROM affirmationentity")
    List<AffirmationEntity> getAllUsers();

    @Insert
    void insertAll(AffirmationEntity... affirmationEntities);
}
