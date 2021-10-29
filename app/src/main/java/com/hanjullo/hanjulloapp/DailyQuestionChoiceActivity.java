package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class DailyQuestionChoiceActivity extends AppCompatActivity {

    private final int fragDailyHow = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_question);

        FragmentSwitch(fragDailyHow);
    }

    private void FragmentSwitch(int fragCode) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragCode) {
            case 1:
                DailyHowFragment fragHow = new DailyHowFragment();

                transaction.replace(R.id.fragFrame, fragHow);

                transaction.commit();
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}