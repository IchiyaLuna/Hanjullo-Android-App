package com.hanjullo.hanjulloapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AnswerDAO {
    @Query("SELECT * FROM AnswerList WHERE date = :dateInput LIMIT 1")
    AnswerList getAnswerByDate(int dateInput);

    @Insert
    void insertAnswer(AnswerList answer);
}
