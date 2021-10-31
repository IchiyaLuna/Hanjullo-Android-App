package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DailyQuestionChoiceActivity extends AppCompatActivity {

    private final int fragDailyHow = 1;
    private final int fragDailyWhat = 2;

    private int currentStep;
    private int currentSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_question);

        Button btnNext = (Button) findViewById(R.id.nextBtn);

        FragmentSwitch(fragDailyHow);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (currentStep) {
                    case 0:
                        getHow();
                        FragmentSwitch(fragDailyWhat);
                        break;
                    case 1:
                        break;
                }
            }
        });
    }

    private void FragmentSwitch(int fragCode) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.faid_in,R.anim.faid_out);

        switch (fragCode) {
            case 1:
                DailyHowFragment fragHow = new DailyHowFragment();

                transaction.replace(R.id.fragFrame, fragHow);

                transaction.commit();

                currentStep = 0;
                break;
            case 2:
                DailyWhatFragment fragWhat = new DailyWhatFragment();

                transaction.replace(R.id.fragFrame, fragWhat);

                transaction.commit();

                currentStep = 1;
                break;
            default:
                break;
        }
    }

    private void getHow() {
        DailyHowFragment fragHow = (DailyHowFragment) getSupportFragmentManager().findFragmentById(R.id.fragFrame);
        currentSelect = fragHow.currentBtn;

        TextView questionText = (TextView) findViewById(R.id.todayQuestionText);

        switch (currentSelect) {
            case 1:
                questionText.setText(R.string.question_what_best);
                break;
            case 2:
                questionText.setText(R.string.question_what_good);
                break;
            case 3:
                questionText.setText(R.string.question_what_nice);
                break;
            case 4:
                questionText.setText(R.string.question_what_not_bad);
                break;
            case 5:
                questionText.setText(R.string.question_what_bad);
                break;
        }
    }

    private void getWhat() {
        DailyWhatFragment fragWhat = (DailyWhatFragment) getSupportFragmentManager().findFragmentById(R.id.fragFrame);
    }
}