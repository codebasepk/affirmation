package com.byteshaft.affirmations.affirmationdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.byteshaft.affirmations.model.Affirmation;

import java.util.List;

// user Data Access Object
@Dao
public interface AffirmationDao {

    @Query("SELECT * FROM affirmation")
    List<Affirmation> getAllAffirmations();

    @Insert
    void insertAll(Affirmation... affirmations);


    @Update
    void update(Affirmation affirmation);


    @Delete
    void delete(Affirmation affirmation);


    @Delete
    void delete(Affirmation... affirmations);  // for array (to delete array)
}
