package com.hanjullo.hanjulloapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AnswerList.class}, version = 1)
public abstract class AnswerDB extends RoomDatabase {
    public abstract AnswerDAO answerDAO();

    private static volatile AnswerDB INSTANCE;

    public static AnswerDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnswerDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AnswerDB.class, "answer_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
