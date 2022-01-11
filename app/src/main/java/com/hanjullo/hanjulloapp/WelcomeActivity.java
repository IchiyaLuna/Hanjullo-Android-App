package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    TextView NameTextView;
    TextView MessageTextView;
    TextView IntroTitleTextView;
    TextView IntroContentTextView;

    Button NextBtn;

    int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        currentStep = 1;

        setBinding();
        setInitAnim();

        NameTextView.setText(UserData.getInstance().getUserName());
        NextBtn.setOnClickListener(v -> {
            switch (currentStep) {
                case 1:
                    startAnim();
                    currentStep = 2;
                    break;
                case 2:
                    Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    break;
            }
        });
    }

    private void setBinding() {
        NameTextView = binding.welcomeNameTextView;
        MessageTextView = binding.welcomeMessageTextView;
        IntroTitleTextView = binding.welcomeIntroTitleTextView;
        IntroContentTextView = binding.welcomeIntroContentTextView;
        NextBtn = binding.welcomeNextBtn;
    }

    private void setInitAnim() {
        IntroTitleTextView.setVisibility(View.GONE);
        IntroContentTextView.setVisibility(View.GONE);
    }

    private void startAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        IntroTitleTextView.setAlpha(0f);
        IntroContentTextView.setAlpha(0f);

        IntroTitleTextView.setVisibility(View.VISIBLE);
        IntroContentTextView.setVisibility(View.VISIBLE);

        IntroTitleTextView.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        IntroContentTextView.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        NameTextView.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        NameTextView.setVisibility(View.GONE);
                    }
                });

        MessageTextView.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        MessageTextView.setVisibility(View.GONE);
                    }
                });
    }
}