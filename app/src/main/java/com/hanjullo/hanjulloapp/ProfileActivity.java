package com.hanjullo.hanjulloapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hanjullo.hanjulloapp.databinding.ActivityProfileBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ProfileActivity extends AppCompatActivity {

    private long backPressedTime;
    private boolean isTodayAnswerTrue = false;

    private ActivityProfileBinding binding;
    private TextView nameTextView;
    private ImageButton CalendarBtn;
    private ImageButton SettingBtn;
    private ImageButton InsideMeBtn;
    private ImageButton BehindMeBtn;
    private ImageButton FrontMeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();
        UserData userData = UserData.getInstance();

        if (!userData.isLoggedIn()) finishAffinity();

        TransactionHandler handler = new TransactionHandler(Looper.getMainLooper());
        Thread t = new Thread(new AnswerTransactionThread(getApplicationContext(), handler, AnswerTransactionThread.workMode.CHECK, null));
        t.start();

        nameTextView.setText(userData.getUserName());

        switchFragment(isTodayAnswerTrue);
    }

    private void setBinding() {
        CalendarBtn = binding.calendarBtn;
        SettingBtn = binding.settingBtn;
        nameTextView = binding.nameText;
        InsideMeBtn = binding.insideMeBtn;
        BehindMeBtn = binding.behindMeBtn;
        FrontMeBtn = binding.frontMeBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            Intent intent;

            int id = v.getId();

            if (id == R.id.calendarBtn) {
                intent = new Intent(ProfileActivity.this, CalendarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_open, R.anim.slide_right_close);
            } else if (id == R.id.settingBtn) {
                try {
                    UserData.getInstance().setAutoLogin(getApplicationContext(), false);
                    ExceptionToast.showExceptionToast(getApplicationContext(), "LOGIN", "자동 로그인 해제됨");
                } catch (GeneralSecurityException | IOException e) {
                    ExceptionToast.showExceptionToast(getApplicationContext(), "LOGIN", "자동 로그인 해제 실패");
                }
            } else if (id == R.id.insideMeBtn) {
                intent = new Intent(ProfileActivity.this, IBFQuestionActivity.class);
                intent.putExtra("IBF",1);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (id == R.id.behindMeBtn) {
                intent = new Intent(ProfileActivity.this, IBFQuestionActivity.class);
                intent.putExtra("IBF",2);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (id == R.id.frontMeBtn) {
                intent = new Intent(ProfileActivity.this, IBFQuestionActivity.class);
                intent.putExtra("IBF",3);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        };

        CalendarBtn.setOnClickListener(listener);
        SettingBtn.setOnClickListener(listener);
        InsideMeBtn.setOnClickListener(listener);
        BehindMeBtn.setOnClickListener(listener);
        FrontMeBtn.setOnClickListener(listener);
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

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finishAffinity();
        }
    }

    class TransactionHandler extends Handler {

        public TransactionHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.getData().getBoolean("done", false)) {
                isTodayAnswerTrue = msg.getData().getBoolean("exist", false);

                switchFragment(isTodayAnswerTrue);
            }
        }
    }
}