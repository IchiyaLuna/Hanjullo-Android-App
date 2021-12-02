package com.hanjullo.hanjulloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hanjullo.hanjulloapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private boolean isTodayAnswerTrue = false;

    private ActivityProfileBinding binding;
    private ImageButton calendarImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();

        switchFragment(isTodayAnswerTrue);
    }

    private void setBinding() {
        calendarImageButton = binding.calendarBtn;
    }

    private void setListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.calendarBtn:
                        intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        break;
                    default:
                        return;
                }

                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_open, R.anim.slide_right_close);
            }
        };

        calendarImageButton.setOnClickListener(listener);
    }

    public void startDailyQuestion() {
        Intent intent = new Intent(getApplicationContext(), DailyQuestionChoiceActivity.class);
        startActivity(intent);
    }

    private void switchFragment(boolean answer) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (answer) {
            ProfileTodayFragment fragToday = new ProfileTodayFragment();

            transaction.replace(R.id.answerTodayFrame, fragToday);
        } else {
            ProfileAnswerFragment fragAnswer = new ProfileAnswerFragment();

            transaction.replace(R.id.answerTodayFrame, fragAnswer);
        }

        transaction.commit();
    }
}