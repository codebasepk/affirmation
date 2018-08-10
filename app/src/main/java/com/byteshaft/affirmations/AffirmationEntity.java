package com.byteshaft.affirmations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class AffirmationEntity {



    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "affirmation_id")
    private int id;

    @ColumnInfo (name = "affirmation")
    private String affirmation;

    public AffirmationEntity(String affirmation) {
        this.affirmation = affirmation;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getAffirmation() {
        return affirmation;
    }

    public void setAffirmation(String affirmation) {
        this.affirmation = affirmation;
    }
}
