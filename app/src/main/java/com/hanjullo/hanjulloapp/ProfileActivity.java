package com.hanjullo.hanjulloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hanjullo.hanjulloapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private boolean isTodayAnswerTrue = false;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        switchFragment(isTodayAnswerTrue);
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