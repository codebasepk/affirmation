package com.byteshaft.affirmations.affirmationdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.byteshaft.affirmations.model.Affirmation;

import java.util.List;

// user Data Access Object
@Dao
public interface AffirmationDao {

    @Query("SELECT * FROM affirmation")
    List<Affirmation> getAllAffirmations();

    @Insert
    void insertAll(Affirmation... affirmations);
}
