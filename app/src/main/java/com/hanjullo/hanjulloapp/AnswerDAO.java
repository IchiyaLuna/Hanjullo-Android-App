package com.hanjullo.hanjulloapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AnswerDAO {
    @Query("SELECT * FROM AnswerEntity WHERE date = :dateInput LIMIT 1")
    AnswerEntity getAnswerByDate(int dateInput);

    @Insert
    void insertAnswer(AnswerEntity answer);

    @Query("UPDATE AnswerEntity SET inside_me = :insideMe WHERE date = :dateInput")
    void insideMeUpdate(int dateInput, String insideMe);

    @Query("UPDATE AnswerEntity SET behind_me = :behindMe WHERE date = :dateInput")
    void behindMeUpdate(int dateInput, String behindMe);

    @Query("UPDATE AnswerEntity SET front_me = :frontMe WHERE date = :dateInput")
    void frontMeUpdate(int dateInput, String frontMe);

    @Query("SELECT EXISTS(SELECT * FROM AnswerEntity WHERE date = :dateInput) AS SUCCESS")
    int isExist(int dateInput);
}
