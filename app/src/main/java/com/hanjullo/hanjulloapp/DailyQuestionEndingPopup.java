package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.time.LocalDate;

public class DailyQuestionEndingPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_question_ending_popup);

        Intent pullIntent = getIntent();
        Bundle receivedBundle = pullIntent.getBundleExtra("answersBundle");

        LocalDate date = LocalDate.now();
        int today = date.getYear() * 10000 + date.getMonthValue() * 100 + date.getDayOfMonth();
        AnswerEntity answerEntity = new AnswerEntity(today);

        answerEntity.setDailyA(receivedBundle.getInt("answerA"));
        answerEntity.setDailyB(receivedBundle.getInt("answerB"));
        answerEntity.setDailyC(receivedBundle.getInt("answerC"));
        answerEntity.setDailyStepB(receivedBundle.getString("answerStepB"));
        answerEntity.setDailyExtra(receivedBundle.getString("answerExtra"));

        LoadingHandler handler = new LoadingHandler(Looper.getMainLooper());

        AnswerTransactionThread thread = new AnswerTransactionThread(getApplicationContext(), handler, AnswerTransactionThread.workMode.INSERT, answerEntity);

        Thread t = new Thread(thread);
        t.start();
    }

    class LoadingHandler extends Handler {

        public LoadingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.getData().getBoolean("done")) {
                Intent intent = new Intent(DailyQuestionEndingPopup.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }
    }
}