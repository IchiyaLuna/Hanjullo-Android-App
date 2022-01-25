package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.room.Room;

import java.time.LocalDate;

public class AnswerTransactionThread implements Runnable {

    enum workMode {
        CHECK,
        INSERT
    }

    Context context;
    Handler handler;
    AnswerEntity answerEntity;
    workMode mode;

    public AnswerTransactionThread(Context context, Handler handler, workMode mode, AnswerEntity entity) {
        this.context = context;
        this.handler = handler;
        this.mode = mode;
        answerEntity = entity;
    }

    @Override
    public void run() {

        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();

        AnswerDB db = Room.databaseBuilder(context, AnswerDB.class, "AnswerDB").build();

        switch (mode) {
            case CHECK:
                LocalDate date = LocalDate.now();
                int today = date.getYear() * 10000 + date.getMonthValue() * 100 + date.getDayOfMonth();

                if (db.answerDAO().isExist(today) == 1) {
                    bundle.putBoolean("exist", true);
                }
                break;
            case INSERT:
                db.answerDAO().insertAnswer(answerEntity);
                break;
        }


        bundle.putBoolean("done", true);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
