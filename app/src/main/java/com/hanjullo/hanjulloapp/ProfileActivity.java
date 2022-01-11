package com.hanjullo.hanjulloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hanjullo.hanjulloapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private boolean isTodayAnswerTrue = false;

    private ActivityProfileBinding binding;
    private TextView nameTextView;
    private ImageButton calendarImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();
        UserData userData = UserData.getInstance();
        nameTextView.setText(userData.getUserName());
        switchFragment(isTodayAnswerTrue);
    }

    private void setBinding() {
        calendarImageButton = binding.calendarBtn;
        nameTextView = binding.nameText;
    }

    private void setListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.calendarBtn) {
                    intent = new Intent(ProfileActivity.this, CalendarActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_open, R.anim.slide_right_close);
            }
        };

        calendarImageButton.setOnClickListener(listener);
    }

    private void loadData() {
        UserData userdata = UserData.getInstance();
        nameTextView.setText(userdata.getUserName());
    }

    public void startDailyQuestion() {
        Intent intent = new Intent(ProfileActivity.this, DailyAQuestionActivity.class);
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